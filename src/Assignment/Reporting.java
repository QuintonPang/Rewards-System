package Assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    
    System.out.println("-------------------------");
    System.out.println("Top 3 items redeemed:");
    System.out.println("-------------------------");
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
            System.out.print("Enter memberNo: ");
            String memberNo = scanner.nextLine();
            Path path = Paths.get("earning.csv");
            try (Stream<String> lines = Files.lines(path)) {
                List<String[]> data = lines
                    .map(line -> line.split(","))
                    .filter(arr -> arr[0].equals(memberNo))
                    .collect(Collectors.toList());

                int totalCurrentValue = data.stream()
                    .mapToInt(arr -> Integer.parseInt(arr[2]))
                    .sum();

                int totalOriginalValue = data.stream()
                    .mapToInt(arr -> Integer.parseInt(arr[3]))
                    .sum();

                    String expiryDate = data.stream()
                        .map(arr -> arr[4])
                        .reduce((first, second) -> second)
                        .orElse("N/A");

                System.out.println("\n"+"memberNo: " + memberNo);
                System.out.println("Current value: " + totalCurrentValue);
                System.out.println("Original value: " + totalOriginalValue);
                System.out.println("Points redeemed: " + (totalOriginalValue - totalCurrentValue));


            System.out.println("Expiry Date: " + expiryDate);
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
                e.printStackTrace();
            }
            break;

            case "2":
            System.out.println("Activity Tracking");

            System.out.print("Enter MemberNo: ");
            memberNo = scanner.nextLine();
            try {
                BufferedReader reader = new BufferedReader(new FileReader("redemptionHistory.txt"));
                String line;
                System.out.printf("%-20s %-20s %-30s %-10s%n", "Date", "Time", "Redeemed Item", "Quantity");
                while ((line = reader.readLine()) != null) {
                    if (line.contains("Member No: " + memberNo)) {
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

