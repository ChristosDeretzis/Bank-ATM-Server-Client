package client;

import java.io.IOException;
import java.net.Socket;

public class BankClientMain {

    public static final String HOST = "localhost";
    public static final int PORT = 1238;

    public static void main(String[] args) {
        try (Socket dataSocket = new Socket(HOST, PORT)) {
            BankClientProtocol protocol = new BankClientProtocol();

            // run the bank client method
            BankClient bankClient = new BankClient(protocol, dataSocket);
            bankClient.run_client();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
