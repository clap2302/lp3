import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;

public class ClienteReserva {
    private static final String HOST_SERVIDOR = "localhost";
    private static final int PORTA_SERVIDOR = 27301;
    
    public static void main(String[] args) {
        try (
            Socket socket = new Socket(HOST_SERVIDOR, PORTA_SERVIDOR);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("╔════════════════════════════════════════════════╗");
            System.out.println("║   Sistema de Reserva de Voo - Cliente          ║");
            System.out.println("╚════════════════════════════════════════════════╝");
            System.out.println("Conectado ao servidor " + HOST_SERVIDOR + ":" + PORTA_SERVIDOR);
            System.out.println();
            
            ExecutorService executorLeitura = Executors.newSingleThreadExecutor();
            executorLeitura.submit(() -> {
                try {
                    String resposta;
                    while ((resposta = entrada.readLine()) != null) {
                        System.out.println("← " + resposta);
                    }
                } catch (IOException e) {
                    System.err.println("Conexão encerrada");
                }
            });
            
            System.out.println("Digite comandos (RESERVE, STATUS, MAP, QUIT):");
            while (scanner.hasNextLine()) {
                String comando = scanner.nextLine().trim();
                if (comando.isEmpty()) continue;
                
                System.out.println("→ " + comando);
                saida.println(comando);
                
                if (comando.toUpperCase().startsWith("QUIT") || 
                    comando.toUpperCase().startsWith("SAIR")) {
                    break;
                }
                
                Thread.sleep(100);
            }
            
            executorLeitura.shutdownNow();
            
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}