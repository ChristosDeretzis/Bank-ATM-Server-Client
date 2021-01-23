package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BankServerMain {
    public static final int PORT = 1238;

    public static void main(String[] args) {
        BankServerProtocol protocol = new BankServerProtocol();

        boolean listening = true;

        try (ServerSocket socket = new ServerSocket(PORT)) {
            System.out.println("Server is listening to port: " + socket);
            while (listening){
                Socket dataSocket = socket.accept();
                System.out.println("Recieved request from: " + dataSocket.getInetAddress());

                MultiThreadedBankServer multiThreadedBankServer = new MultiThreadedBankServer(dataSocket, protocol);
                multiThreadedBankServer.start();
            }

            socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }


    }
}
