import java.util.concurrent.SynchronousQueue;

public class RequisicaoReserva implements Comparable<RequisicaoReserva> {
    private final String idCliente;
    private final String nomePassageiro;
    private final Categoria categoria;
    private final String codigoAssento;
    private final SynchronousQueue<RespostaReserva> filaResposta;
    private final long marcaTempo;
    
    public RequisicaoReserva(String idCliente, String nomePassageiro, Categoria categoria, String codigoAssento) {
        this.idCliente = idCliente;
        this.nomePassageiro = nomePassageiro;
        this.categoria = categoria;
        this.codigoAssento = codigoAssento;
        this.filaResposta = new SynchronousQueue<>();
        this.marcaTempo = System.nanoTime();
    }
    
    @Override
    public int compareTo(RequisicaoReserva outra) {
        int comparacaoCategoria = Integer.compare(
            this.categoria.getPrioridade(), 
            outra.categoria.getPrioridade()
        );
        
        if (comparacaoCategoria != 0) {
            return comparacaoCategoria;
        }
        
        return Long.compare(this.marcaTempo, outra.marcaTempo);
    }
    
    public String getIdCliente() { 
        return idCliente; 
    }

    public String getNomePassageiro() { 
        return nomePassageiro; 
    }

    public Categoria getCategoria() { 
        return categoria; 
    }

    public String getCodigoAssento() { 
        return codigoAssento; 
    }
    
    public SynchronousQueue<RespostaReserva> getFilaResposta() { 
        return filaResposta; 
    }
}