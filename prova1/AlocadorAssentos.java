import java.util.*;
import java.util.concurrent.*;

public class AlocadorAssentos implements Runnable {
    private final PriorityBlockingQueue<RequisicaoReserva> filaRequisicoes;
    private final MapaAssentos mapaAssentos;
    private final Semaphore limitadorTaxa;
    private volatile boolean executando = true;
    
    public AlocadorAssentos(PriorityBlockingQueue<RequisicaoReserva> filaRequisicoes, MapaAssentos mapaAssentos, Semaphore limitadorTaxa) {
        this.filaRequisicoes = filaRequisicoes;
        this.mapaAssentos = mapaAssentos;
        this.limitadorTaxa = limitadorTaxa;
    }
    
    @Override
    public void run() {
        System.out.println("[AlocadorAssentos] Iniciado");
        
        while (executando) {
            try {
                RequisicaoReserva requisicao = filaRequisicoes.poll(1, TimeUnit.SECONDS);
                if (requisicao == null){
                     continue;
                }
                processarRequisicao(requisicao);
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("[AlocadorAssentos] Encerrado");
    }
    
    private void processarRequisicao(RequisicaoReserva requisicao) {
        
        try
        {
            if (!limitadorTaxa.tryAcquire(10, TimeUnit.SECONDS))
            {
                enviarResposta(requisicao, RespostaReserva.falha("TIMEOUT", "Sistema sobrecarregado"));
                return;
            }

            String codigoAssento = requisicao.getCodigoAssento();

            if (codigoAssento.equalsIgnoreCase("ANY"))
            {
                RespostaReserva resposta = alocarQualquerAssento(requisicao);
                enviarResposta(requisicao, resposta);
            }
            else
            {
                RespostaReserva resposta = alocarAssentoEspecifico(requisicao, codigoAssento);
                enviarResposta(requisicao, resposta);
            }
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            enviarResposta(requisicao, RespostaReserva.falha("ERRO_SERVIDOR", "Operação interrompida"));
        }
        finally
        {
            limitadorTaxa.release();
        }

        /** *
        REQUISITOS:
            1. Tentar adquirir permissão do Semaphore (limitadorTaxa) com timeout de 10 segundos
            2. Se timeout, enviar resposta de falha "TIMEOUT" com mensagem "Sistema sobrecarregado"
            3. Verificar se o código do assento é "ANY" (case-insensitive)
            4. Se "ANY": chamar alocarQualquerAssento()
            5. Se específico: chamar alocarAssentoEspecifico()
            6. Enviar a resposta usando enviarResposta()
            7. SEMPRE liberar o Semaphore no bloco finally (mesmo em caso de exceção)
            8. Tratar InterruptedException:
                - Restaurar flag de interrupção: Thread.currentThread().interrupt()
                 Enviar resposta de falha "ERRO_SERVIDOR" com mensagem "Operação interrompida"
        DICAS:
            - Use try-catch-finally para garantir liberação do semáforo
            - tryAcquire() retorna boolean indicando sucesso/falha
            - O finally SEMPRE executa, mesmo com return ou exception
            - Use equalsIgnoreCase() para comparação case-insensitive
        /** */
    }
    
    private RespostaReserva alocarQualquerAssento(RequisicaoReserva requisicao) throws InterruptedException {

        for (String codigoAssento : mapaAssentos.getTodosCodigosAssentos())
        {
            Assento assento = mapaAssentos.getAssento(codigoAssento);

            if (assento.estaOcupado()) continue;
            
            if (assento.getTrava().tryLock(5, TimeUnit.SECONDS))
            {
                try
                {
                    if (assento.estaOcupado()) continue;

                    assento.getFlagOcupado().compareAndSet(false, true);

                    String codigoReserva = gerarCodigoReserva();
                    Categoria categoria = requisicao.getCategoria();

                    assento.ocupar(codigoAssento, categoria, codigoReserva);
                    mapaAssentos.decrementarAssentosLivres();
                    registrarReserva(requisicao, codigoAssento, codigoReserva);
                    return RespostaReserva.sucesso(codigoAssento, codigoReserva);
                }
                finally
                {
                    assento.getTrava().unlock();
                }
            }
            
        }
        return RespostaReserva.falha("VOO_LOTADO", "Voo lotado");
        /** *
        REQUISITOS:
            1. Iterar por todos os assentos disponíveis usando mapaAssentos.getTodosCodigosAssentos()
            2. Para cada assento, verificar se está ocupado (pular se estiver)
            3. Tentar adquirir o lock do assento com timeout de 5 segundos usando tryLock()
            4. Fazer verificação dupla (double-check) se o assento ainda está livre
            5. Usar compareAndSet() do AtomicBoolean para garantir atomicidade
            6. Se conseguir alocar:
                - Gerar código de reserva usando gerarCodigoReserva()
                - Ocupar o assento com assento.ocupar()
                - Decrementar contador de assentos livres
                - Registrar a reserva no log
                - Retornar RespostaReserva.sucesso()
            7. SEMPRE liberar o lock no bloco finally
            8. Se nenhum assento disponível, retornar RespostaReserva.falha("VOO_LOTADO", "Voo lotado")
            9. Tratar InterruptedException adequadamente
        DICAS:
            - Use try-finally para garantir que o lock seja liberado
            - A verificação dupla evita race conditions
            - compareAndSet garante que apenas uma thread aloque o assento
            - Lembre-se de decrementar o contador atômico de assentos livres
        /** */
    }
    
    private RespostaReserva alocarAssentoEspecifico(RequisicaoReserva requisicao, String codigoAssento) {
        
        Assento assento = mapaAssentos.getAssento(codigoAssento);
        
        if (assento == null) {
            return RespostaReserva.falha("ASSENTO_INVALIDO", "Assento " + codigoAssento + " não existe");
        }
        
        try {
            if (!assento.getTrava().tryLock(5, TimeUnit.SECONDS)) {
                return RespostaReserva.falha("TIMEOUT", "Timeout ao acessar assento");
            }
            
            try {
                if (assento.estaOcupado()) {
                    return RespostaReserva.falha("ASSENTO_OCUPADO", "Assento " + codigoAssento + " já está reservado");
                }
                
                if (assento.getFlagOcupado().compareAndSet(false, true)) {
                    String codigoReserva = gerarCodigoReserva();
                    assento.ocupar(requisicao.getNomePassageiro(), requisicao.getCategoria(), codigoReserva);
                    mapaAssentos.decrementarAssentosLivres();
                    registrarReserva(requisicao, codigoAssento, codigoReserva);
                    return RespostaReserva.sucesso(codigoAssento, codigoReserva);
                } else {
                    return RespostaReserva.falha("ASSENTO_OCUPADO", "Assento " + codigoAssento + " foi reservado por outro cliente");
                }
            } finally {
                assento.getTrava().unlock();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return RespostaReserva.falha("TIMEOUT", "Operação interrompida");
        }
    }
    
    private void enviarResposta(RequisicaoReserva requisicao, RespostaReserva resposta) {
        try {
            requisicao.getFilaResposta().put(resposta);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[AlocadorAssentos] Erro ao enviar resposta: " + e.getMessage());
        }
    }
    
    private String gerarCodigoReserva() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
    
    private void registrarReserva(RequisicaoReserva requisicao, String codigoAssento, String codigoReserva) {
        System.out.printf("[RESERVA] %s %s → %s (%s)%n", requisicao.getNomePassageiro(), requisicao.getCategoria(), codigoAssento, codigoReserva);
    }
    
    public void encerrar() {
        executando = false;
    }
}