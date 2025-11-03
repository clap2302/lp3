import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class TesteEstresse {
    private static final String HOST_SERVIDOR = "localhost";
    private static final int PORTA_SERVIDOR = 123;
    private static final int NUM_CLIENTES = 200;
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║    Teste de Estresse - Sistema de Reserva      ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println("Clientes simultâneos: " + NUM_CLIENTES);
        System.out.println();
        
        ExecutorService executor = Executors.newFixedThreadPool(NUM_CLIENTES);
        List<Future<ResultadoTeste>> futuros = new ArrayList<>();
        
        long tempoInicio = System.currentTimeMillis();
        
        for (int i = 0; i < NUM_CLIENTES; i++) {
            int numCliente = i + 1;
            futuros.add(executor.submit(() -> testarCliente(numCliente)));
        }

        int sucesso = 0, falha = 0;
        for (Future<ResultadoTeste> futuro : futuros) {
            try {
                ResultadoTeste resultado = futuro.get();
                if (resultado.sucesso) sucesso++;
                else falha++;
            } catch (ExecutionException e) {
                falha++;
                System.err.println("Erro: " + e.getCause().getMessage());
            }
        }
        
        long duracao = System.currentTimeMillis() - tempoInicio;
        
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
        
        // Relatório
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║   Resultados                                   ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println("Total de requisições: " + NUM_CLIENTES);
        System.out.println("Sucesso: " + sucesso);
        System.out.println("Falhas: " + falha);
        System.out.println("Tempo total: " + duracao + " ms");
        System.out.println("Média: " + (duracao / NUM_CLIENTES) + " ms/requisição");
    }
    
    private static ResultadoTeste testarCliente(int numCliente) {
        String[] categorias = {"PLATINUM", "GOLD", "SILVER", "BASIC"};
        Random rand = new Random();
        Clientes clientes = new Clientes();
        List<String> nomesClientes = clientes.getClientes(NUM_CLIENTES);
        String nome = "Usuario" + numCliente;
        String nomeCliente = nomesClientes.get(numCliente - 1);
        String categoria = categorias[rand.nextInt(categorias.length)];
        
        try (
            Socket socket = new Socket(HOST_SERVIDOR, PORTA_SERVIDOR);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter saida = new PrintWriter(socket.getOutputStream(), true)
        ) {
            entrada.readLine();
            entrada.readLine();
            
            String comando = String.format("RESERVE %s %s ANY", nomeCliente, categoria);
            saida.println(comando);
            
            String resposta = entrada.readLine();
            boolean sucesso = resposta.startsWith("OK");
            
            System.out.printf("[Cliente %d] %s → %s%n", numCliente, comando, resposta);
            
            saida.println("QUIT");
            
            return new ResultadoTeste(sucesso, resposta);
            
        } catch (IOException e) {
            System.err.printf("[Cliente %d] Erro: %s%n", numCliente, e.getMessage());
            return new ResultadoTeste(false, e.getMessage());
        }
    }
    
    static class ResultadoTeste {
        final boolean sucesso;
        final String mensagem;
        
        ResultadoTeste(boolean sucesso, String mensagem) {
            this.sucesso = sucesso;
            this.mensagem = mensagem;
        }
    }
}