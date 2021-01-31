import org.json.JSONException;

import java.io.IOException;
import java.util.Scanner;

public class AtmInterface {
    private Scanner scanner = new Scanner(System.in);

    public AtmInterface() {

    }

    public void run_client() throws IOException, JSONException {
        String message_to_server;
        Reply result_from_server;

        while (true){
            String option = "";

            // we have to identify the user before everything else
            message_to_server = HelperFunctions.identification_process();

            // Get message from server
            result_from_server = HelperFunctions.doGetRequest(message_to_server);

            if (result_from_server.getStatusCode() == 200) {
                String json_data = result_from_server.getData();
                int customer_id = HelperFunctions.getCustomerId(json_data);
                while (!result_from_server.equals("CLOSE")) {


                    // print menu and choose an option
                    HelperFunctions.printMenu();
                    option = scanner.next();

                    switch (option) {
                        case "D":
                            message_to_server = HelperFunctions.deposit_process(customer_id);
                            result_from_server = HelperFunctions.doPostRequest(message_to_server);
                            break;
                        case "W":
                            message_to_server = HelperFunctions.withdrawal_process(customer_id);
                            result_from_server = HelperFunctions.doPostRequest(message_to_server);
                            break;
                        case "C":
                            message_to_server = HelperFunctions.change_PIN_process(customer_id);
                            result_from_server = HelperFunctions.doPutRequest(message_to_server);
                            break;
                        case "S":
                            message_to_server = HelperFunctions.get_Balance_process(customer_id);
                            result_from_server = HelperFunctions.doGetRequest(message_to_server);
                            break;
                        case "E":
                            System.exit(0);
                        default:
                            throw new IllegalStateException("Unexpected value: " + option);
                    }

                    // send the message to server and receive input
                    System.out.println(result_from_server.getData() + "\n");
                }
            }
            else {
                System.out.println(result_from_server.getData());
            }
        }
    }
}
