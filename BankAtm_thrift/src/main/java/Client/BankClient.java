package Client;

import Client.thrift.BankAppService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.io.IOException;
import java.sql.SQLException;

public class BankClient {
    public static void main(String [] args) {


        try {
            TTransport transport;

            transport = new TSocket("localhost", 9090);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            BankAppService.Client client = new BankAppService.Client(protocol);
            BankClientImplementation clientImplementation = new BankClientImplementation(client);
            clientImplementation.run_client();

            transport.close();
        } catch (TException x) {
            x.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
