package server;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class MultiThreadedBankServer extends Thread {

    private Socket socket;
    private BankServerProtocol bankServerProtocol;

    private InputStream is;
    private BufferedReader in;
    private OutputStream os;
    private PrintWriter out;

    public MultiThreadedBankServer(Socket socket, BankServerProtocol bankServerProtocol) {
        this.socket = socket;
        this.bankServerProtocol = bankServerProtocol;

        try {
            // initialize the connection with the client
            is = socket.getInputStream();
            in = new BufferedReader(new InputStreamReader(is));
            os = socket.getOutputStream();
            out = new PrintWriter(os, true);

            // connnect to the database
            bankServerProtocol.initialize_DB_Connection();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        String input_message, output_message;

        try {
            while ((input_message = in.readLine()) != null){
                output_message = bankServerProtocol.process_request(input_message);
                out.println(output_message);
            }
            socket.close();

        } catch (IOException e){
            System.out.println("IOException: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }
}
