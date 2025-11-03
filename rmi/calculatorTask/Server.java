
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server implements Calculator
{
    public Server() throws RemoteException
    {
        super();
    }

    @Override
    public double add(double a, double b) throws RemoteException
    {
        return a + b;
    }
    @Override
    public double subtract(double a, double b) throws RemoteException
    {
        return  a - b;
    }
    @Override
    public double multiply(double a, double b) throws RemoteException
    {
        return a * b;
    }
    @Override
    public double divide(double a, double b) throws RemoteException, ArithmeticException
    {
        if (b == 0)
        {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a / b;
    }

    public static void main(String[] args)
    {
        try
        {
            LocateRegistry.createRegistry(1099);

            Server obj = new Server();

            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("Calculator", obj);

            System.out.println("Calculator Server is ready.");
        }
        catch (RemoteException e)
        {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
