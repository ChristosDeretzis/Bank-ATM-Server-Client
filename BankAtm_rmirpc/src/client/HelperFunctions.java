package client;

import java.util.Scanner;

public class HelperFunctions {
    private static String username;
    private static int PIN;
    private static int new_PIN;
    private static double amount;

    private static Scanner scanner = new Scanner(System.in);

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
        username = scanner.nextLine();
        System.out.print("Enter your PIN: ");
        PIN = scanner.nextInt();

        return username + " " + Integer.toString(PIN) + " Identification";
    }

    public static String deposit_process() {
        System.out.print("Enter the amount of money you want to deposit: ");
        amount = scanner.nextDouble();

        return username + " " + Integer.toString(PIN) + " Deposit " + Double.toString(amount);
    }

    public static String withdrawal_process() {
        System.out.print("Enter the amount of money you want to withdraw: ");
        amount = scanner.nextDouble();

        return username + " " + Integer.toString(PIN) + " Withdraw " + Double.toString(amount);
    }

    public static String get_Balance_process() {
        return username + " " + Integer.toString(PIN) + " Get_Balance";
    }

    public static String change_PIN_process() {
        System.out.print("Enter the the new PIN: ");
        new_PIN = scanner.nextInt();

        return username + " " + Integer.toString(PIN) + " Change_Pin " + Integer.toString(new_PIN);
    }
}
