package Calculator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class CalculatorReceiver
{
    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            System.err.println("Usage: java BasicUDPReceiver <host> <port>");
            System.exit(0);
        }

        try
        {
            java.net.DatagramSocket socket = new java.net.DatagramSocket();
            Inet4Address address = (Inet4Address) Inet4Address.getByName(args[0]);
            int port = Integer.parseInt(args[1]);

            while (true)
            {
                System.out.println("Enter operation in the format: <num1> <operation(+,-,*,/)> <num2>");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                
                byte[] sendingMessage = input.getBytes();
                DatagramPacket sendingPacket = new DatagramPacket(sendingMessage, sendingMessage.length, address, port);
                socket.send(sendingPacket);
                
                byte[] messageReceived = new byte[256];
                DatagramPacket packetReceived = new DatagramPacket(messageReceived, messageReceived.length);
                socket.receive(packetReceived);
                String resultStr = new String(packetReceived.getData(), 0, packetReceived.getLength()).trim();
                double result = Double.parseDouble(resultStr);
    
                JOptionPane.showMessageDialog(null, ("Resultado da operação: " + result), "Message received", 1);
            }
        }
        catch (IOException e)
        {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}