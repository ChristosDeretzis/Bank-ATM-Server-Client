package client;

import java.util.Scanner;

public class BankClientProtocol {
    private String username;
    private int PIN;
    private int new_PIN;
    private double amount;

    private Scanner scanner = new Scanner(System.in);

    public void printMenu() {
        System.out.println("1. Deposit money (Press D)");
        System.out.println("2. Withdraw money (Press W)");
        System.out.println("3. Show current balance (Press S)");
        System.out.println("4. Change PIN (Press C)");
        System.out.println("5. Exit (Press E)");
        System.out.print("Enter your choice: ");
    }

    public String identification_process() {
        System.out.println("Before doing anything, we have to identify you first");
        System.out.print("Enter your username: ");
        username = scanner.nextLine();
        System.out.print("Enter your PIN: ");
        PIN = scanner.nextInt();

        return username + " " + Integer.toString(PIN) + " Identification";
    }

    public String deposit_process() {
        System.out.print("Enter the amount of money you want to deposit: ");
        amount = scanner.nextDouble();

        return username + " " + Integer.toString(PIN) + " Deposit " + Double.toString(amount);
    }

    public String withdrawal_process() {
        System.out.print("Enter the amount of money you want to withdraw: ");
        amount = scanner.nextDouble();

        return username + " " + Integer.toString(PIN) + " Withdraw " + Double.toString(amount);
    }

    public String get_Balance_process() {
        return username + " " + Integer.toString(PIN) + " Get_Balance";
    }

    public String change_PIN_process() {
        System.out.print("Enter the the new PIN: ");
        new_PIN = scanner.nextInt();

        return username + " " + Integer.toString(PIN) + " Change_Pin " + Integer.toString(new_PIN);
    }
}
