import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class ServidorReservaVoo {
    private static final int PORTA = 27301;
    private static final int TAMANHO_POOL_THREADS = 50;
    private static final int MAX_RESERVAS_SIMULTANEAS = 10;
    
    private final ServerSocket socketServidor;
    private final ExecutorService servicoExecutor;
    private final PriorityBlockingQueue<RequisicaoReserva> filaRequisicoes;
    private final MapaAssentos mapaAssentos;
    private final Semaphore limitadorTaxa;
    private final AlocadorAssentos alocador;
    private volatile boolean executando = true;
    
    public ServidorReservaVoo(int porta) throws IOException {
        this.socketServidor = new ServerSocket(porta);
        this.servicoExecutor = Executors.newFixedThreadPool(TAMANHO_POOL_THREADS);
        this.filaRequisicoes = new PriorityBlockingQueue<>();
        
        this.mapaAssentos = new MapaAssentos(30, new char[]{'A', 'B', 'C', 'D', 'E', 'F'});
        
        this.limitadorTaxa = new Semaphore(MAX_RESERVAS_SIMULTANEAS, true);
        this.alocador = new AlocadorAssentos(filaRequisicoes, mapaAssentos, limitadorTaxa);
        
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║   Sistema de Reserva de Voo - Servidor        ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println("Porta: " + porta);
        System.out.println("Assentos: " + mapaAssentos.getTotalAssentos());
        System.out.println("Pool de threads: " + TAMANHO_POOL_THREADS);
        System.out.println("Máx. reservas simultâneas: " + MAX_RESERVAS_SIMULTANEAS);
        System.out.println();
    }
    
    public void iniciar() {
        servicoExecutor.submit(alocador);
        
        System.out.println("[Servidor] Aguardando conexões...\n");
        
        while (executando) {
            try {
                Socket socketCliente = socketServidor.accept();
                TratadorCliente tratador = new TratadorCliente(socketCliente, filaRequisicoes, mapaAssentos);
                servicoExecutor.submit(tratador);
                
            } catch (IOException e) {
                if (executando) {
                    System.err.println("[Servidor] Erro ao aceitar conexão: " + e.getMessage());
                }
            }
        }
    }
    
    public void encerrar() {
        System.out.println("\n[Servidor] Iniciando encerramento...");
        executando = false;
        
        try {
            socketServidor.close();
        } catch (IOException e) {
            System.err.println("Erro ao fechar ServerSocket: " + e.getMessage());
        }
        
        alocador.encerrar();
        servicoExecutor.shutdown();
        
        try {
            if (!servicoExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
                servicoExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            servicoExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        
        System.out.println("[Servidor] Encerrado");
    }
    
    public static void main(String[] args) {
        try {
            ServidorReservaVoo servidor = new ServidorReservaVoo(PORTA);
            
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                servidor.encerrar();
            }));
            
            servidor.iniciar();
            
        } catch (IOException e) {
            System.err.println("Erro ao iniciar servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}