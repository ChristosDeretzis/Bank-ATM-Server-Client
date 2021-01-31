import com.squareup.okhttp.*;
import jdk.nashorn.internal.parser.JSONParser;
import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;

public class HelperFunctions {
    private static String username;
    private static int PIN;
    private static int new_PIN;
    private static double amount;

    private static Scanner scanner = new Scanner(System.in);
    public static final OkHttpClient client = new OkHttpClient();
    private static final String baseUrl = "http://localhost:8090/atm";

    public static void printMenu() {
        System.out.println("1. Deposit money (Press D)");
        System.out.println("2. Withdraw money (Press W)");
        System.out.println("3. Show current balance (Press S)");
        System.out.println("4. Change PIN (Press C)");
        System.out.println("5. Exit (Press E)");
        System.out.print("Enter your choice: ");
    }

    public static String identification_process() {
        System.out.println("Before doing anything, we have to identify you first");
        System.out.print("Enter your username: ");
        username = scanner.next();
        System.out.print("Enter your PIN: ");
        PIN = scanner.nextInt();

        String authentication_url = baseUrl + "/authenticate?username=" + username + "&pin=" + PIN;
        return authentication_url;
    }

    public static String deposit_process(int customer_id) {
        System.out.print("Enter the amount of money you want to deposit: ");
        amount = scanner.nextDouble();

        String deposit_url = baseUrl + "/" + customer_id + "/deposit?amount=" + amount;
        return deposit_url;
    }

    public static String withdrawal_process(int customer_id) {
        System.out.print("Enter the amount of money you want to withdraw: ");
        amount = scanner.nextDouble();

        String withdrawal_url = baseUrl + "/" + customer_id + "/withdraw?amount=" + amount;
        return withdrawal_url;
    }

    public static String get_Balance_process(int customer_id) {
        String balance_url = baseUrl + "/" + customer_id + "/show_balance";
        return balance_url;
    }

    public static String change_PIN_process(int customer_id) {
        System.out.print("Enter the the new PIN: ");
        new_PIN = scanner.nextInt();

        String changePIN_url = baseUrl + "/" + customer_id + "/change_PIN?new_pin=" + new_PIN;
        return changePIN_url;
    }

    public static int getCustomerId(String json_data) throws JSONException {
        JSONObject customerObject = new JSONObject(json_data);
        int id = customerObject.getInt("id");

        return id;
    };

    public static Reply doGetRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        String data = response.body().string();
        int statusCode = response.code();

        return new Reply(data, statusCode);
    }

    public static Reply doPostRequest(String url) throws IOException {
         MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "");

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        String data = response.body().string();
        int statusCode = response.code();

        return new Reply(data, statusCode);
    }

    public static Reply doPutRequest(String url) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "");

        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        Response response = client.newCall(request).execute();

        String data = response.body().string();
        int statusCode = response.code();

        return new Reply(data, statusCode);
    }

}
