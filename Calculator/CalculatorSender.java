package Calculator;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class CalculatorSender
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
            int port = Integer.parseInt(args[1]);
            DatagramSocket socket = new DatagramSocket(port);
            Calculator calculator = new Calculator();
            
            while (true)
            {
                byte[] message = new byte[256];
                DatagramPacket packetReceived = new DatagramPacket(message, message.length);
                socket.receive(packetReceived);
    
                String messageString = new String(packetReceived.getData()).trim();
                String[] parts = messageString.split(" ");
                double num1 = Double.parseDouble(parts[0]);
                double num2 = Double.parseDouble(parts[2]);
                double result = 0;
    
                switch (parts[1])
                {
                    case "+" -> result = calculator.add(num1, num2);

                    case "-" -> result = calculator.subtract(num1, num2);

                    case "*" -> result = calculator.multiply(num1, num2);

                    case "/" -> result = calculator.divide(num1, num2);

                    default -> {
                        System.err.println("Invalid operation: " + parts[1]);
                        System.exit(1);
                    }
                }
    
                DatagramPacket sendingPacket = new DatagramPacket(
                                                Double.toString(result).getBytes(),
                                                Double.toString(result).getBytes().length,
                                                packetReceived.getAddress(),
                                                packetReceived.getPort()
                                            );
                
                socket.send(sendingPacket);
    
                System.out.println("Message sent to " + args[0] + " on port " + port);
            }
        }
        catch (IOException e)
        {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
