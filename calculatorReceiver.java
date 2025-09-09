
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import javax.swing.JOptionPane;

public class calculatorReceiver
{
    public static void main(String[] args)
    {
        if (args.length != 5)
        {
            System.err.println("Usage: java BasicUDPReceiver <host> <port> <num1> <num2> <operation>");
            System.exit(0);
        }

        try
        {
            java.net.DatagramSocket socket = new java.net.DatagramSocket();

            Inet4Address address = (Inet4Address) Inet4Address.getByName(args[0]);
            int port = Integer.parseInt(args[1]);

            byte[] sendingMessage = (args[2] + ' ' + args[3] + ' ' + args[4]).getBytes();
            DatagramPacket sendingPacket = new DatagramPacket(sendingMessage, sendingMessage.length, address, port);
            socket.send(sendingPacket);
            
            byte[] messageReceived = new byte[256];
            DatagramPacket packetReceived = new DatagramPacket(messageReceived, messageReceived.length);
            socket.receive(packetReceived);
            String resultStr = new String(packetReceived.getData(), 0, packetReceived.getLength()).trim();
            double result = Double.parseDouble(resultStr);

            JOptionPane.showMessageDialog(null, ("Resultado da operação: " + result), "Message received", 1);
            socket.close();
        }
        catch (IOException e)
        {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}