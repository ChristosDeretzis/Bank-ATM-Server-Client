package server;

import java.sql.*;

public class BankServerImplementation {
    // Initialize all of the parameters for the database connection
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/bankatm?autoReconnect=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    // Enter your own username and password from
    // your database connection
    private static final String username = "root";
    private static final String password = "yah2!lo0";

    private static final double WITHDRAWAL_LIMIT = 600;

    // Additional variables
    private Connection connection = null;
    private ResultSet rs;

    public BankServerImplementation() {
        try {
            Class.forName(DRIVER_NAME);

            connection = DriverManager.getConnection(URL, username, password);
            System.out.println("Connection succeeded");
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public String process_request(String request) throws SQLException {
        String input_message = request;
        System.out.println("Received message from client: " + input_message);
        String[] parameters = input_message.split(" ");
        String result = perform_action(parameters);
        System.out.println(result);
        return result;
    }

    // This function is a helper function that accepts an array of elements
    // based on this basic form [username, pin, action] and sometimes has 4 elements
    // where the last element depends on the action the user chose
    private String perform_action(String[] arr) throws SQLException {
        String returning_message = "";

        String username = arr[0];
        int pin = Integer.parseInt(arr[1]);
        String action = arr[2];

        try {

            // the user needs to be authenticated first before he can move on
            // other transactions
            Customer customer = identify_customer(username, pin);
            System.out.println(customer.getFirstName() + " " + customer.getLastName());
            if(customer != null) {
                int customerID = customer.getID();

                if (action.equals("Withdraw")){
                    double amount = Double.parseDouble(arr[3]);
                    double current_balance = getBalance(customer.getID());

                    returning_message = withdraw(customerID, amount);
                    if (returning_message.equals(Messages.WITHDRAWAL_SUCCESSION_MESSAGE))
                        updateAmount(customerID, current_balance - amount);
                } else if(action.equals("Deposit")){
                    double amount = Double.parseDouble(arr[3]);
                    double current_balance = getBalance(customerID);

                    returning_message = deposit(customerID, amount);
                    if (returning_message.equals(Messages.DEPOSIT_SUCCESSION_MESSAGE))
                        updateAmount(customerID, current_balance + amount);
                } else if (action.equals("Change_Pin")){
                    int new_pin = Integer.parseInt(arr[3]);

                    returning_message = updatePin(customerID, new_pin);
                } else if (action.equals("Get_Balance")){
                    double balance = getBalance(customerID);

                    returning_message = "The amount is:" +  Double.toString(balance);
                } else if (action.equals("Identification")){
                    returning_message = Messages.IDENTIFICATION_SUCCESSION_MESSAGE;
                } else {
                    returning_message = Messages.ACTION_NOT_FOUND_ERROR;
                }
            } else {
                returning_message = Messages.WRONG_CREDENTIALS_ERROR;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returning_message;
    }

    // This fuction returns an instance of a customer object if the customer exists in the database,
    // else it returns null. It basically fetches the data of an instance from the Customer table, based on his
    // id and his pin.
    private Customer identify_customer(String user, int PIN) throws SQLException {
        Customer customer = new Customer();

        Statement statement = connection.createStatement();
        String query = "select username, first_name, last_name, pin, id from bankatm.Customer where username = '" + user + "'  and pin = '" +  PIN + "'";
        rs = statement.executeQuery(query);

        while (rs.next()){

            // move the database result to an instance of a
            // customer object
            customer.setUserName(rs.getString("username"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setPIN(rs.getInt("pin"));
            customer.setID(rs.getInt("id"));

            return customer;
        }
        return null;
    }

    // This function returns the current balance of the user based on his id
    private double getBalance(int customer_ID) throws SQLException{
        Statement statement = connection.createStatement();
        String query = "select balance from bankatm.Balance where cid = '" + customer_ID + "'";
        rs = statement.executeQuery(query);

        rs.next();
        double balance = rs.getDouble("balance");

        return balance;
    }

    // This function adds a row in the table of deposit if the user has chosen
    // to deposit money and returns a personalised message
    private String deposit(int customer_ID, double amount) throws SQLException{
        if (amount <= 0)
            return Messages.AMOUNT_NOT_VALID_ERROR;

        Statement statement = connection.createStatement();
        String query = "insert into bankatm.Deposit(amount, cid, last_updated) values ('" + amount + "', '" + customer_ID + "', now())";
        int rows = statement.executeUpdate(query);

        // successful insertion
        if (rows == 1)
            return Messages.DEPOSIT_SUCCESSION_MESSAGE;
        else
            return Messages.DEPOSIT_NOT_VALID_ERROR;
    }

    // This function adds a row in the table of withdraw if the user has chosen
    // to withdraw money and returns a personalised message
    private String withdraw(int customer_ID, double amount) throws SQLException{
        double balance = getBalance(customer_ID);
        boolean daily_amount_reached = check_withdrawal_daily_limit(customer_ID, amount);

        if(amount <= 0)
            return Messages.AMOUNT_NOT_VALID_ERROR;

        else if (balance < amount)
            return Messages.BALANCE_NOT_ENOUGH_ERROR;
        else if (daily_amount_reached)
            return Messages.WITHDRAWAL_DAILY_LIMIT_REACHED;
        else {

            Statement statement = connection.createStatement();
            String query = "insert into bankatm.Withdrawal(amount, cid, last_updated) values ('" + amount + "', '" + customer_ID + "', now())";
            int rows = statement.executeUpdate(query);

            if (rows == 1)
                return Messages.WITHDRAWAL_SUCCESSION_MESSAGE;
            else
                return Messages.WITHDRAWAL_NOT_VALID_ERROR;
        }

    }

    // this function updates the amount of the user after a withdrawal or a transaction
    // to the balance table
    private boolean updateAmount(int customer_ID, double amount) throws SQLException {
        Statement statement = connection.createStatement();
        if(amount <= 0)
            return false;

        String query = "update bankatm.Balance set balance = " + amount + ", last_updated = now() where cid = " +  customer_ID + "";
        int rows = statement.executeUpdate(query);

        if (rows == 1)
            return true;
        return false;
    }

    // this function updates the pin of a customer and returns a personalised message
    private String updatePin(int customerID, int new_pin) throws SQLException {
        if (new_pin < 1000 || new_pin >9999)
            return Messages.INVALID_PIN_ERROR;

        Statement statement = connection.createStatement();
        String query = "update bankatm.Customer set PIN = '" + new_pin + "' where id = '" + customerID + "'";
        int rows = statement.executeUpdate(query);

        // if there is a valid customer
        if (rows == 1)
            return Messages.PIN_CHANCE_SUCCESSION_MESSAGE;
        else
            return Messages.PIN_CHANGE_FAILURE_ERROR;
    }

    // this amount checks the sums of the today's daily withdrawals of the customer
    // if the sum is smaller than the daily withdrawal, return true
    // otherwise return false
    private boolean check_withdrawal_daily_limit(int customerID, double amount) throws SQLException{
        Statement statement = connection.createStatement();
        String query = "select sum(amount) as today_amount from bankatm.Withdrawal where cid = '" + customerID +
                "' and  last_updated >= CURRENT_DATE  and  last_updated < CURRENT_DATE + INTERVAL 1 DAY";

        rs = statement.executeQuery(query);
        rs.next();

        double balance = rs.getDouble("today_amount");
        if(balance + amount > WITHDRAWAL_LIMIT)
            return true;
        else
            return false;
    }
}
