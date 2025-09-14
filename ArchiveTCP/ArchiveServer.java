package ArchiveTCP;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ArchiveServer
{
    public static void main(String[] args)
    {
        try
        {
            // Creates the TCP (by default) server socket
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server is listening on port 12345");

            while(true)
            {
                // Accepts and waits for a new client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                out.flush(); // Flush to ensure the stream is ready and there's no leftover data

                byte[] fileBytes = Files.readAllBytes(Paths.get("textinho.txt"));
                
                out.writeObject(fileBytes); // Keeps the date object in the output stream buffer

                out.close(); // Close the output stream and send the buffered data
                clientSocket.close(); // Close the client socket
            }
        }
        catch (IOException e)
        {
            System.err.println("IOException: " + e.getMessage());
        }
    }
}