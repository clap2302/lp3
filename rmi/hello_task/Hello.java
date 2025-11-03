import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote
{
    // Remote method that can be called by the client
    String sayHello() throws RemoteException;
}
