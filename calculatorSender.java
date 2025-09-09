import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;

public class calculatorSender
{
    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            System.err.println("Usage: java calculatorSender.java <machine_name> <port>");
            System.exit(0);
        }

        try
        {
            Inet4Address address = (Inet4Address) Inet4Address.getByName(args[0]);
            int port = Integer.parseInt(args[1]);
            DatagramSocket socket = new DatagramSocket(port);

            byte[] message = new byte[256];
            DatagramPacket packetReceived = new DatagramPacket(message, message.length);
            socket.receive(packetReceived);


            String messageString = new String(packetReceived.getData()).trim();
            String[] parts = messageString.split(" ");
            double result = 0;

            if (parts[2].equals("+"))
            {
                result = Double.parseDouble(parts[0]) + Double.parseDouble(parts[1]);
            }
            else if (parts[2].equals("-"))
            {
                result = Double.parseDouble(parts[0]) - Double.parseDouble(parts[1]);
            }
            else if (parts[2].equals("*"))
            {
                result = Double.parseDouble(parts[0]) * Double.parseDouble(parts[1]);
            }
            else if (parts[2].equals("/"))
            {
                result = Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]);
            }
            else
            {
                System.err.println("Invalid operation: " + parts[2]);
                System.exit(1);
            }

            DatagramPacket sendingPacket = new DatagramPacket(
                                            Double.toString(result).getBytes(),
                                            Double.toString(result).getBytes().length,
                                            packetReceived.getAddress(),
                                            packetReceived.getPort()
                                        );
            
            socket.send(sendingPacket);

            System.out.println("Message sent to " + args[0] + " on port " + port);
            socket.close();
        }
        catch (IOException e)
        {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
