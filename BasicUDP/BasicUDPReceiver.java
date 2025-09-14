package BasicUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.swing.JOptionPane;

public class BasicUDPReceiver
{
    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.err.println("Usage: java BasicUDPReceiver <port>");
            System.exit(0);
        }

        try
        {
            int port = Integer.parseInt(args[0]);

            DatagramSocket socket = new DatagramSocket(port);
            byte[] message = new byte[256];

            DatagramPacket packet = new DatagramPacket(message, message.length);
            socket.receive(packet);
            JOptionPane.showMessageDialog(null, new String(packet.getData()).trim(), "Message received", 1);
            socket.close();
        }
        catch (IOException e)
        {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}