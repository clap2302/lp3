package BasicTCP;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Date;
import javax.swing.JOptionPane;

public class BasicTCPClient
{
    public static void main(String[] args)
    {
        try
        {
            // Connects to the server running on localhost at port 12345
            Socket client = new Socket("localhost", 12345);
            
            // Receives the date object sent by the server
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            Date currentDate = (Date) in.readObject();
            
            JOptionPane.showMessageDialog(null, "Date from server: " + currentDate.toString());
            
            // Closes the input stream and the socket
            in.close();
            System.err.println("Connection closed");
        }
        catch (HeadlessException | IOException | ClassNotFoundException e)
        {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}