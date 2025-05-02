package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Ledger ledger = new Ledger();
        ledger.loadFromFile("transactions.csv");

        // after every new deposit or payment, call: ledger.saveToFile("transactions.csv)

        String response = HomeScreen();
        boolean running = true;

        while (running) {
            switch (response) {
                case "d":
                    System.out.println("Enter a description: ");
                    String descD = sc.nextLine();

                    System.out.println("Enter your vendor: ");
                    String venD = sc.nextLine();

                    System.out.println("Enter amount: ");
                    double amoD = Double.parseDouble(sc.nextLine());

                    Transaction deposit = new Transaction(
                            LocalDate.now().toString(),
                            LocalTime.now().toString(),
                            descD, venD, amoD
                    );

                    ledger.addTransaction(deposit);
                    ledger.saveToFile("transaction.csv");
                    System.out.println("Recorded deposit.");
                    break;

                case "p":
                    System.out.println("Enter a description: ");
                    String descP = sc.nextLine();

                    System.out.println("Enter your vendor: ");
                    String venP = sc.nextLine();

                    System.out.println("Enter amount: ");
                    double amoP = Double.parseDouble(sc.nextLine());
                    Transaction payment = new Transaction(
                            LocalDate.now().toString(),
                            LocalTime.now().toString(),
                            descP, venP, amoP
                    );

                    ledger.addTransaction(payment);
                    ledger.saveToFile("transaction.csv");
                    System.out.println("Recorded payment.");
                    break;

                case "l":
                    String r = ShowLedger();
                    break;

                case "x":
                    running = false;
                    break;

                default:
                    System.out.println("Please select a valid option!");
            }
        }
    }

    public static String HomeScreen() {
        Scanner sc = new Scanner(System.in);
        System.out.println("---------------------------------------------------");
        System.out.println("""
                Please select the following options:
                D) Add deposit.\s
                P) Make Payment (Debit).\s
                L) Ledger.\s
                X) Exit.""");
        System.out.println("---------------------------------------------------");

        return sc.nextLine().toLowerCase();
    }

    public static String ShowLedger() {
        Scanner sc = new Scanner(System.in);
        System.out.println("---------------------------------------------------");
        System.out.println("""
                Please select the following options:
                A) Add deposit.\s
                D) Make Payment (Debit).\s
                P) Ledger.\s
                R) Exit.\s
                H) Home""");
        System.out.println("---------------------------------------------------");

        return sc.nextLine().toLowerCase();
    }
}