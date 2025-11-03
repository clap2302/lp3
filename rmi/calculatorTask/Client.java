
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client
{
    public Client()
    {
        super();
    }

    public static void main(String[] args)
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry("localhost");

            Calculator stub = (Calculator) registry.lookup("Calculator");

            double a;
            double b;
            double result;
            char operation;
            Scanner scan = new Scanner(System.in);

            while (true)
            {
                System.out.println("Enter first number: ");
                a = scan.nextDouble();

                System.out.println("Enter second number: ");
                b = scan.nextDouble();

                System.out.println("Enter operation (+, -, *, /): ");
                operation = scan.next().charAt(0);


                switch(operation)
                {
                    case '+' -> result = stub.add(a, b);

                    case '-' -> result = stub.subtract(a, b);

                    case '*' -> result = stub.multiply(a, b);

                    case '/' -> result = stub.divide(a, b);

                    default ->
                    {
                        System.out.println("Invalid operation. Please try again.");
                        continue;
                    }
                }

                System.out.println("The result of the operation is: " + result);

            }
          
        } catch (RemoteException | NotBoundException e)
        {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
