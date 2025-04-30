package org.example;

import java.io.*;
import java.util.*;

public class Ledger {
    ArrayList<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void loadFromFile(String filename) {
        // .clear() prevents any copied data if I call this method again.
        transactions.clear();

        // Using scanner to read the file line by line.
        try (Scanner sc = new Scanner(new File(filename))) {
            while (sc.hasNextLine()) {
                String curr = sc.nextLine();
                // Splits each line into 5 parts by |
                String[] part = curr.split("\\|");
                if (part.length == 5) {
                    String date = part[0];
                    String time = part[1];
                    String description = part[2];
                    String vendor = part[3];
                    double amount = Double.parseDouble(part[4]);

                    Transaction t = new Transaction(date, time, description, vendor, amount);
                    transactions.add(t);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous transactions found.");
        }
    }

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Transaction t : transactions) {
                writer.printf("%s|%s|%s|%s|%.2f%n",
                        t.getDate(),
                        t.getTime(),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount()
                );
            }
        } catch (IOException e) {
            System.out.println("Error saving to file : " + e.getMessage());
        }
    }

    // All entries
    public ArrayList<Transaction> allEntries() {
        ArrayList<Transaction> all = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAmount() != 0) {
                all.add(t);
            }
        }

        return all;
    }
    // Positive entries
    public ArrayList<Transaction> getDeposits() {
        ArrayList<Transaction> deposits = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) {
                deposits.add(t);
            }
        }

        return deposits;
    }

    // Negative entries
    public ArrayList<Transaction> getPayments() {
        ArrayList<Transaction> payments = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAmount() < 0) {
                payments.add(t);
            }
        }

        return payments;
    }
}
