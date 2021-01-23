package Client;

import org.apache.thrift.TException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class BankClientImplementation {
    private Client.thrift.BankAppService.Client client;

    private InputStream is;
    private Scanner scanner;

    public BankClientImplementation(Client.thrift.BankAppService.Client client) {
        scanner = new Scanner(System.in);
        this.client = client;
    }

    public void run_client() throws IOException, SQLException, TException {
        String message_to_server;
        String result_from_server;

        while (true){
            String option = "";

            // we have to identify the user before everything else
            message_to_server = HelperFunctions.identification_process();

            // Get message from server
            result_from_server = client.process_request(message_to_server);
            System.out.println(result_from_server + "\n");

            if (result_from_server.equals(Messages.IDENTIFICATION_SUCCESSION_MESSAGE)) {
                while (!result_from_server.equals("CLOSE")) {
                    // print menu and choose an option
                    HelperFunctions.printMenu();
                    option = scanner.nextLine();

                    switch (option) {
                        case "D":
                            message_to_server = HelperFunctions.deposit_process();
                            break;
                        case "W":
                            message_to_server = HelperFunctions.withdrawal_process();
                            break;
                        case "C":
                            message_to_server = HelperFunctions.change_PIN_process();
                            break;
                        case "S":
                            message_to_server = HelperFunctions.get_Balance_process();
                            break;
                        case "E":
                            System.exit(0);
                        default:
                            throw new IllegalStateException("Unexpected value: " + option);
                    }

                    // send the message to server and receive input
                    result_from_server = client.process_request(message_to_server);
                    System.out.println(result_from_server + "\n");
                }
            }
            else {
                System.out.println(result_from_server);
            }
        }
    }
}
