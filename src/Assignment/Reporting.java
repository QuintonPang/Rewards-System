package Assignment;

import Assignment.Product;
import Assignment.RedemptionItem;
import Assignment.Earning;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Reporting {



public void displayTopRedeemedItem() {
    Map<String, Integer> redeemedItems = new HashMap<>();

    try {
        BufferedReader reader = new BufferedReader(new FileReader("redemptionHistory.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(", ");
            if (parts.length < 4) {
                continue;  // Skip this line because it doesn't contain the expected data
            }
            String itemName = parts[2].replace("Redeemed Item: ", "");
            int quantity = Integer.parseInt(parts[3].replace("Quantity: ", ""));
            redeemedItems.put(itemName, redeemedItems.getOrDefault(itemName, 0) + quantity);
        }
        reader.close();
    } catch (IOException e) {
        System.out.println("An error occurred while reading the file.");
        e.printStackTrace();
    }

    List<Map.Entry<String, Integer>> list = new ArrayList<>(redeemedItems.entrySet());
    list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

    System.out.println("Top 3 items redeemed:");
    for (int i = 0; i < Math.min(list.size(), 3); i++) {
        System.out.println((i + 1) + ". " + list.get(i).getKey());
    }
}
public void displayLowRedeemedItem() {
    Map<String, Integer> redeemedItems = new HashMap<>();

    try {
        BufferedReader reader = new BufferedReader(new FileReader("redemptionHistory.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(", ");
            if (parts.length < 4) {
                continue;  // Skip this line because it doesn't contain the expected data
            }
            String itemName = parts[2].replace("Redeemed Item: ", "");
            int quantity = Integer.parseInt(parts[3].replace("Quantity: ", ""));
            redeemedItems.put(itemName, redeemedItems.getOrDefault(itemName, 0) + quantity);
        }
        reader.close();
    } catch (IOException e) {
        System.out.println("An error occurred while reading the file.");
        e.printStackTrace();
    }

    List<Map.Entry<String, Integer>> list = new ArrayList<>(redeemedItems.entrySet());
    list.sort(Map.Entry.comparingByValue());

    System.out.println("3 least redeemed items:");
    for (int i = 0; i < Math.min(list.size(), 3); i++) {
        System.out.println((i + 1) + ". " + list.get(i).getKey());
    }
}

public void userProfileStatus() {
    System.out.println("User Profile Status \n");
    System.out.println("1. Personal Info \n");
    System.out.println("2. Activity Tracking \n");

    Scanner scanner = new Scanner(System.in);
    String choice;

    do {
        System.out.print("Enter your choice: ");
        choice = scanner.nextLine();

        switch (choice) {
            case "1":
            System.out.print("Enter your MemberNo: ");
            String memberNo = scanner.nextLine();
            try {
                BufferedReader reader = new BufferedReader(new FileReader("earning.csv"));
                List<String> lines = new ArrayList<>();
                String line;
                while ((line = reader.readLine()) != null) {
                lines.add(line);
                }
                reader.close();

                boolean found = false;
                for (int i = lines.size() - 1; i >= 1; i--) {
                String[] parts = lines.get(i).split(",");
                if (parts[0].equals(memberNo)) {
                    System.out.println("MemberNo: " + parts[0]);
                    System.out.println("Available value: " + parts[2]);
                    System.out.println("Earning value: " + parts[3]);
                    System.out.println("Expiry Date: " + parts[5]);
                    found = true;
                    break;
                }
                }

                if (!found) {
                System.out.println("No member found with MemberNo: " + memberNo);
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
                e.printStackTrace();
            }
            break;

            case "2":
            System.out.println("Activity Tracking");

            System.out.print("Enter MemberNo: ");
            String memberId = scanner.nextLine();
            try {
                BufferedReader reader = new BufferedReader(new FileReader("redemptionHistory.txt"));
                String line;
                System.out.printf("%-20s %-20s %-30s %-10s%n", "Date", "Time", "Redeemed Item", "Quantity");
                while ((line = reader.readLine()) != null) {
                    if (line.contains("Member ID: " + memberId)) {
                        String[] parts = line.split(", ");
                        String dateTime = parts[0].replace("Date: ", "");
                        String[] dateTimeParts = dateTime.split(" ");
                        String date = dateTimeParts[0];
                        String time = dateTimeParts[1];
                        String itemName = parts[2].replace("Redeemed Item: ", "");
                        String quantity = parts[3].replace("Quantity: ", "");
                        System.out.printf("%-20s %-20s %-30s %-10s %n", date, time, itemName, quantity);
                    }
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
                e.printStackTrace();
            }
            break;

            default:
                System.out.println("Invalid choice. Please try again.");
                choice = null;  // Set choice to null to continue the loop
        }
    } while (choice == null);
}
}

