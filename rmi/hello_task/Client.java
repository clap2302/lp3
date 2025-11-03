import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client
{

    private Client(){}

    public static void main(String args[])
    {
        try
        {
            Registry registry = LocateRegistry.getRegistry("localhost");

            Hello stub = (Hello) registry.lookup("Hello"); // stub implements Hello interface

            String response = stub.sayHello(); // calls implementation of sayHello() on server

            System.out.println("Response: " + response);

        } catch (NotBoundException | RemoteException e)
        {
            System.out.println("Client exception: " + e.toString());
        }
    }
}