package org.example;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Ledger {
    ArrayList<Transaction> transactions = new ArrayList<>();

    public ArrayList<Transaction> customSearch(String startDateInput, String endDateInput,
                                               String vendorInput, String descriptionInput, String amountInput) {
        ArrayList<Transaction> result = new ArrayList<>();

        LocalDate startDate = null;
        LocalDate endDate = null;
        Double amount = null;

        // Convert string inputs to usable types
        if (!startDateInput.isEmpty()) {
            startDate = LocalDate.parse(startDateInput);
        }

        if (!endDateInput.isEmpty()) {
            endDate = LocalDate.parse(endDateInput);
        }

        if (!amountInput.isEmpty()) {
            try {
                amount = Double.parseDouble(amountInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Skipping amount filter.");
            }
        }

        for (Transaction t : transactions) {
            LocalDate tDate = t.getLocalDate();

            if (startDate != null && tDate.isBefore(startDate)) continue;
            if (endDate != null && tDate.isAfter(endDate)) continue;
            if (!vendorInput.isEmpty() && !t.getVendor().toLowerCase().contains(vendorInput.toLowerCase())) continue;
            if (!descriptionInput.isEmpty() && !t.getDescription().toLowerCase().contains(descriptionInput.toLowerCase())) continue;
            if (amount != null && t.getAmount() != amount) continue;

            result.add(t);
        }

        return result;
    }


    public ArrayList<Transaction> getMonthToDate() {
        ArrayList<Transaction> result = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (Transaction t : transactions) {
            LocalDate tDate = t.getLocalDate();
            if (tDate.getYear() == now.getYear() && tDate.getMonth() == now.getMonth()) {
                result.add(t);
            }
        }

        return result;
    }

    public ArrayList<Transaction> getPreviousMonth() {
        ArrayList<Transaction> result = new ArrayList<>();
        LocalDate now = LocalDate.now();
        LocalDate prevMonth = now.minusMonths(1);
        for (Transaction t : transactions) {
            LocalDate tDate = t.getLocalDate();
            if (tDate.getYear() == prevMonth.getYear() && tDate.getMonth() == prevMonth.getMonth()) {
                result.add(t);
            }
        }

        return result;
    }

    public ArrayList<Transaction> getYearToDate() {
        ArrayList<Transaction> result = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (Transaction t : transactions) {
            LocalDate tDate = t.getLocalDate();
            if (tDate.getYear() == now.getYear()) {
                result.add(t);
            }
        }

        return result;
    }

    public ArrayList<Transaction> getPreviousYear() {
        ArrayList<Transaction> result = new ArrayList<>();
        LocalDate now = LocalDate.now();
        int lastYear = now.getYear()-1;
        for (Transaction t : transactions) {
            if (t.getLocalDate().getYear() == lastYear) {
                result.add(t);
            }
        }

        return result;
    }

    public ArrayList<Transaction> searchByVendor(String name) {
        ArrayList<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getVendor().equalsIgnoreCase(name)) {
                result.add(t);
            }
        }

        return result;
    }

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
