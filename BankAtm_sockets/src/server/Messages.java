package server;

public class Messages {

    // Succession messages
    public static final String IDENTIFICATION_SUCCESSION_MESSAGE = "Your identification was successful";
    public static final String WITHDRAWAL_SUCCESSION_MESSAGE = "The withdrawal was successful";
    public static final String DEPOSIT_SUCCESSION_MESSAGE = "The deposit was successful";
    public static final String PIN_CHANCE_SUCCESSION_MESSAGE = "Your pin just changed successfully";

    // Error Messages
    public static final String DEPOSIT_NOT_VALID_ERROR = "The deposit was not successful";
    public static final String WITHDRAWAL_NOT_VALID_ERROR = "The withdrawal was not successful";
    public static final String PIN_CHANGE_FAILURE_ERROR = "The pin was not updated. There was an error. Try again";
    public static final String AMOUNT_NOT_VALID_ERROR = "The amount is not valid. Please enter a valid amount";
    public static final String BALANCE_NOT_ENOUGH_ERROR = "The amount you have inside the bank is not enough";
    public static final String WITHDRAWAL_DAILY_LIMIT_REACHED = "I'm sorry, but you reached the withdrawal daily limit";
    public static final String ACTION_NOT_FOUND_ERROR = "The action you entered is not valid. Please try again";
    public static final String WRONG_CREDENTIALS_ERROR = "The customer is not in the bank database, try again with different elements";
    public static final String INVALID_PIN_ERROR = "The pin is invalid. Please enter a valid PIN";
}
