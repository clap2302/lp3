package BasicUDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;

public class BasicUDPSender
{
    public static void main(String[] args)
    {
        if (args.length != 3)
        {
            System.err.println("Usage: java BasicUDPSender <machine_name> <port> <message>");
            System.exit(0);
        }

        try
        {
            Inet4Address address = (Inet4Address) Inet4Address.getByName(args[0]);
            int port = Integer.parseInt(args[1]);
            byte[] message = args[2].getBytes();

            DatagramPacket packet = new DatagramPacket(message, message.length, address, port);
            java.net.DatagramSocket socket = new java.net.DatagramSocket();

            socket.send(packet);
            socket.receive(packet);

            System.out.println("Message sent to " + args[0] + " on port " + port);
            socket.close();
        }
        catch (IOException e)
        {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}