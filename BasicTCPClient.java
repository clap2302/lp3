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
            Socket client = new Socket("localhost", 12345);

            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            Date currentDate = (Date) in.readObject();
            
            JOptionPane.showMessageDialog(null, "Date from server: " + currentDate.toString());

            in.close();
            System.err.println("Connection closed");
        }
        catch (HeadlessException | IOException | ClassNotFoundException e)
        {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}