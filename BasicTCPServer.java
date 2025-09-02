import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class BasicTCPServer {
    public static void main(String[] args) throws IOException {
        try
        {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server is listening on port 12345");

            while(true)
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                out.flush();
                out.writeObject(new Date());
                out.close();
                clientSocket.close();
            }
        }
        catch (IOException e)
        {
            System.err.println("IOException: " + e.getMessage());
        }
    }
}