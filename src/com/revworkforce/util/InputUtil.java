package com.revworkforce.util;

import java.util.Scanner;
import java.sql.Date;

public class InputUtil {

    // Safe Integer input
    public static int getInt(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(sc.nextLine().trim());
                return value;
            } catch (Exception e) {
                System.out.println("Invalid number. Please enter a valid integer.");
            }
        }
    }

    // Safe String input (not empty)
    public static String getString(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            String value = sc.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty.");
        }
    }

    // Safe Date input (yyyy-mm-dd)
    public static Date getDate(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                return Date.valueOf(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println(" Invalid date format. Use yyyy-mm-dd");
            }
        }
    }

    // Safe rating (1–5)
    public static int getRating(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                int rating = Integer.parseInt(sc.nextLine().trim());
                if (rating >= 1 && rating <= 5) {
                    return rating;
                }
                System.out.println(" Rating must be between 1 and 5.");
            } catch (Exception e) {
                System.out.println("Invalid number.");
            }
        }
    }

    // Safe percentage (0–100)
    public static int getPercentage(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                int p = Integer.parseInt(sc.nextLine().trim());
                if (p >= 0 && p <= 100) {
                    return p;
                }
                System.out.println("Percentage must be 0–100.");
            } catch (Exception e) {
                System.out.println("Invalid number.");
            }
        }
    }
}
