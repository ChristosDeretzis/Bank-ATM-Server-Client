package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BankClient {
    private BankClientProtocol protocol;
    private Socket dataSocket;

    private InputStream is;
    private BufferedReader in;
    private OutputStream os;
    private PrintWriter out;

    private BufferedReader input_reader;

    public BankClient(BankClientProtocol protocol, Socket socket) throws IOException {
        this.protocol = protocol;
        dataSocket = socket;

        try {
            is = dataSocket.getInputStream();
            in = new BufferedReader(new InputStreamReader(is));
            os = dataSocket.getOutputStream();
            out = new PrintWriter(os, true);
            input_reader = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    public void run_client() throws IOException {
        String message_to_server,result_from_server, option;
        option = "";

        while (true){

            // we have to identify the user before everything else
            message_to_server = protocol.identification_process();
            out.println(message_to_server);
            result_from_server = in.readLine();
            System.out.println(result_from_server + "\n");

            if (result_from_server.equals(Messages.IDENTIFICATION_SUCCESSION_MESSAGE)){
                while (!result_from_server.equals("CLOSE")){
                    // print menu and choose an option
                    protocol.printMenu();
                    option = input_reader.readLine();
                    System.out.println(option);

                    // personalized messages and inputs based on the action
                    switch (option){
                        case "D":
                            message_to_server = protocol.deposit_process();
                            break;
                        case "W":
                            message_to_server = protocol.withdrawal_process();
                            break;
                        case "C":
                            message_to_server = protocol.change_PIN_process();
                            break;
                        case "S":
                            message_to_server = protocol.get_Balance_process();
                            break;
                        case "E":
                            System.exit(0);
                            default:
                    }
                    System.out.println(message_to_server);

                    // send the message to server and receive input
                    out.println(message_to_server);
                    result_from_server = in.readLine();
                    System.out.println(result_from_server);
                }
            } else {
                System.out.println(result_from_server);
            }
        }
    }
}
