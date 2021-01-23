package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

// create the interface of the server
public interface BankServer extends Remote {
    String process_request(String input_message) throws SQLException, RemoteException;
}
