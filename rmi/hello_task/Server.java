import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements Hello
{
    public Server() throws Exception
    {
        super();
    }

    @Override
    public String sayHello() throws RemoteException
    {
        return "Hello, world"; // implements the remote method declared in Hello interface
    }

    public static void main(String args[])
    {
        try
        {
            LocateRegistry.createRegistry(1099); // creates RMI registry on default port 1099

            Server obj = new Server();

            Registry registry = LocateRegistry.getRegistry(); // gets the registry

            registry.rebind("Hello", obj); // registers the server object as a public name "Hello" to be accessed by clients

            System.out.println("Server ready");

        } catch (Exception e)
        {
            System.out.println("Server Exception: " + e.toString());
        }
    }
}