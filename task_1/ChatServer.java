import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer
{

    private static final int PORT = 12345;
    private static final List<PrintWriter> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args)
    {
        System.out.println("[Servidor] Ouvindo na porta  " + PORT + "...");
        try (ServerSocket server = new ServerSocket(PORT))
        {
            while (true)
            {
                Socket socket = server.accept();
                System.out.println("[Servidor] Conexão de: " + socket.getInetAddress().getHostAddress());
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e)
        {
            System.err.println("[Servidor] Erro: " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable
    {
        private final Socket socket;
        private PrintWriter out;

        ClientHandler(Socket socket)
        {
            this.socket = socket;
        }

        @Override public void run()
        {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));)
            {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                // 1) registrar cliente
                clients.add(out);

                out.println("Digite seu nome: ");
                String name;
                name = in.readLine();

                // 2) dar boas-vindas
                this.sendServidorMsg("Bem-vindo, " + name + "! Digite mensagens. Use 'exit' para sair.");
                broadcast(name + " entrou no chat!", out);

                // 3) laço principal de leitura
                String line;
                while ((line = in.readLine()) != null)
                {
                    if ("exit".equalsIgnoreCase(line.trim()))
                    {
                        this.sendServidorMsg("Você saiu do chat. Até logo!");
                        broadcast("[Servidor] " + name + " saiu no chat!", out);
                        break;
                    }
                    broadcast("[" + name + "] " + line, out);
                }
            } catch (IOException e)
            {
                // queda de cliente é comum; logar e seguir
                System.out.println("Cliente desconectou: " + e.getMessage());
            } finally
            {
                // 4) garantir limpeza
                try { socket.close(); clients.remove(out);} catch (IOException ignore) {}
            }
        }

        private void broadcast(String msg, PrintWriter sender)
        {
            for (PrintWriter pw : clients)
            {
                pw.println(msg);
            }
        }

        private void sendServidorMsg(String msg)
        {
            out.println("[Servidor] " + msg);
        }
    }
}
