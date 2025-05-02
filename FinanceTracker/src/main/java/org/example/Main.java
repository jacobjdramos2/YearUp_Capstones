package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Ledger ledger = new Ledger();
        ledger.loadFromFile("transactions.csv");

        // after every new deposit or payment, call: ledger.saveToFile("transactions.csv)
        boolean running = true;

        while (running) {
            String response = homeScreen();
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
                    ledger.saveToFile("transactions.csv");
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
                            descP, venP, -Math.abs(amoP)
                    );

                    ledger.addTransaction(payment);
                    ledger.saveToFile("transactions.csv");
                    System.out.println("Recorded payment.");
                    break;

                case "l":
                    showLedger(sc, ledger);
                    break;

                case "x":
                    running = false;
                    break;

                default:
                    System.out.println("Please select a valid option!");
            }
        }
    }

    public static String homeScreen() {
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

    public static void showReports(Scanner sc, Ledger ledger) {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("""
                    Reports:
                    1) Month to Date
                    2) Previous Month
                    3) Year To Date
                    4) Previous Year
                    5) Search by Vendor
                    6) Custom Search
                    0) Back to Ledger
                    """);

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Month To Date:");
                    for (Transaction t : ledger.getMonthToDate()) {
                        System.out.println(t);
                    }
                    break;
                case "2":
                    System.out.println("Previous Month:");
                    for (Transaction t : ledger.getPreviousMonth()) {
                        System.out.println(t);
                    }
                    break;
                case "3":
                    System.out.println("Year To Date:");
                    for (Transaction t : ledger.getYearToDate()) {
                        System.out.println(t);
                    }
                    break;
                case "4":
                    System.out.println("Previous Year:");
                    for (Transaction t : ledger.getPreviousYear()) {
                        System.out.println(t);
                    }
                    break;
                case "5":
                    System.out.print("Enter vendor name: ");
                    String vendor = sc.nextLine();
                    for (Transaction t : ledger.searchByVendor(vendor)) {
                        System.out.println(t);
                    }
                    break;
                case "6":
                    runCustomSearch(sc, ledger);
                    break;
                case "0":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }

    public static void showLedger(Scanner sc, Ledger ledger) {
        boolean isRunningLedgerMenu = true;

        while (isRunningLedgerMenu) {
            System.out.println("---------------------------------------------------");
            System.out.println("""
                    Please select the following options:
                    A) All transactions.\s
                    D) Deposits.\s
                    P) Payments\s
                    R) Reports\s
                    H) Home""");
            System.out.println("---------------------------------------------------");

            String response = sc.nextLine().toLowerCase();

            switch (response) {
                case "a":
                    System.out.println("All transactions:");
                    for (Transaction t : ledger.allEntries()) {
                        System.out.println(t);
                    }
                    break;
                case "d":
                    System.out.println("Deposits:");
                    for (Transaction t : ledger.getDeposits()) {
                        System.out.println(t);
                    }
                    break;
                case "p":
                    System.out.println("Payments:");
                    for (Transaction t : ledger.getPayments()) {
                        System.out.println(t);
                    }
                    break;
                case "r":
                    System.out.println("Reports:");
                    showReports(sc, ledger);
                case "h":
                    isRunningLedgerMenu = false;
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
    }

    public static void runCustomSearch(Scanner sc, Ledger ledger) {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Enter start date (yyyy-mm-dd) or press Enter to skip:");
            String startDateInput = sc.nextLine().trim();

            System.out.println("Enter end date (yyyy-mm-dd) or press Enter to skip:");
            String endDateInput = sc.nextLine().trim();

            System.out.println("Enter vendor name or press Enter to skip:");
            String vendorInput = sc.nextLine().trim();

            System.out.println("Enter description keyword or press Enter to skip:");
            String descriptionInput = sc.nextLine().trim();

            System.out.println("Enter exact amount or press Enter to skip:");
            String amountInput = sc.nextLine().trim();

            ArrayList<Transaction> result = ledger.customSearch(
                    startDateInput, endDateInput, vendorInput, descriptionInput, amountInput
            );

            System.out.println("Search results:");
            if (result.isEmpty()) {
                System.out.println("No matching transactions found.");
            } else {
                for (Transaction t : result) {
                    System.out.println(t);
                }
            }

            System.out.println("Would you like to run another custom search? (y/n)");
            String rerun = sc.nextLine().trim().toLowerCase();
            if (!rerun.equals("y")) {
                isRunning = false;
            }
        }
    }
}