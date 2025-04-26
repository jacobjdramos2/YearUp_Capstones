package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String response = HomeScreen();
        response = response.toLowerCase();
        boolean isValid = false;

        while (!isValid) {
            if (response.equals("d")) {
                isValid = true;
            } else if (response.equals("p")) {
                isValid = true;
            } else if (response.equals("l")) {
                String r = ShowLedger();
                isValid = true;
            } else if (response.equals("x")) {
                isValid = true;
            } else {
                System.out.println("Please select a valid option!");
                HomeScreen();
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

        return sc.nextLine();
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

        return sc.nextLine();
    }
}