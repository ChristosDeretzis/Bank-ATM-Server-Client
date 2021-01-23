package client;

import server.BankServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BankClientMain {

    public static final String HOST = "localhost";
    public static final int PORT = Registry.REGISTRY_PORT;

    public static void main(String[] args) {
        try {
            // Locate rmi registry
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);

            // Look up for a specific name and get remote reference (stub)
            String rmiObjectName = "BankServer";
            BankServer ref = (BankServer) registry.lookup(rmiObjectName);

            // Call BankClient Method
            BankClient bankClient = new BankClient(ref);
            bankClient.run_client();
        } catch (RemoteException re) {
            System.out.println("Remote Exception");
            re.printStackTrace();
        } catch (Exception e) {
            System.out.println("Other Exception");
            e.printStackTrace();
        }


    }
}
