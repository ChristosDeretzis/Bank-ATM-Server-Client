package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class BankServerMain {

    public static final String HOST = "localhost";
    public static final int PORT = Registry.REGISTRY_PORT;

    public static void main(String[] args) throws Exception {
        // Should be first, especially if server is NOT localhost
        System.setProperty("java.rmi.server.hostname", HOST);

        // remote object creation
        BankServerImplementation bankServer = new BankServerImplementation();

        // create registry
        Registry registry = LocateRegistry.createRegistry(PORT);

        // Bind remote object to a name and publish in rmi registry
        String rmiObjectName = "BankServer";
        registry.rebind(rmiObjectName, bankServer);
        System.out.println("The bank Server is ready for use");

        // Server is running until we press a key
        System.out.println("Press <Enter> for exit.");
        System.in.read();

        // Free space and clear rmi registry
        UnicastRemoteObject.unexportObject(bankServer, true);
        registry.unbind(rmiObjectName);
        System.out.println("Remote object unbounded.");
    }
}
