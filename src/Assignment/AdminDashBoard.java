/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.awt.Desktop;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author munchun
 */
public class AdminDashBoard{

    private static final String filename = "user.txt";
    private String memberNo;
    Loyalty loyalty = new Loyalty();
    Policy policy = new Policy();
    UserAccount userAccount = new UserAccount();

//    public void display() {
//        System.out.println("\n Admin Dashboard \n");
//        System.out.println("0 : Exit");
//        System.out.println("1. Check Customer Details");
//        System.out.println("2. View all products");
//        System.out.println("3. Check Earning file");
//        System.out.println("4. Top redeemed Item from customer");
//        System.out.println("5. Least redeemed Item from Customer");
//        System.out.println("6. User Activity Checking");
//        System.out.println("7. Update TierMultiplier");
//        System.out.println("8. Update Expiration Duration");
//        System.out.print("  Enter your choice: ");
//        Scanner scanner = new Scanner(System.in);
//        int choice = scanner.nextInt();
//
//        switch (choice) {
//            case 0:
//                System.out.println("Exiting...");
//                userAccount.displayMenu();
//                break;
//            case 1:
//                checkCustomerDetails();
//                break;
//            case 2:
//                // Call viewAllProducts() method
//                viewAllProducts();
//                display();
//                break;
//            case 3:
//                checkEarningFile();
//                display();
//                break;
//            case 4:
//                displayTopRedeemedItem();
//                display();
//                break;
//            case 5:
//                displayLowRedeemedItem();
//                display();
//                break;
//            case 6:
//                ActivityTracking();
//                display();
//                break;
//            case 7:
//                loyalty.printTierMultipliers();
//                scanner.nextLine();
//                System.out.print("Enter the tier you want to modify: ");
//                String tierChoice = scanner.nextLine();
//                ;
//                System.out.print("Enter the new multiplier: ");
//                double multiplier = scanner.nextDouble();
//
//                switch (tierChoice) {
//                    case "1":
//                        loyalty.updateMultiplier("Bronze", multiplier);
//                        break;
//                    case "2":
//                        loyalty.updateMultiplier("Silver", multiplier);
//                        break;
//                    case "3":
//                        loyalty.updateMultiplier("Gold", multiplier);
//                        break;
//                    case "4":
//                        loyalty.updateMultiplier("Platinium", multiplier);
//                        break;
//                    default:
//                        System.out.println("Error Selection");
//                        break;
//                }
//                display();
//                break;
//            case 8:
//                String expiryMonths;
//                boolean validate = false;
//                while (!validate) {
//                    scanner.nextLine();
//                    System.out.println("Update the expiration durations in months (01-12)");
//                    expiryMonths = scanner.nextLine();
//                    if (policy.validateMonth(expiryMonths)) {
//                        policy.setExpiryMonths(Integer.parseInt(expiryMonths));
//                        System.out.println("New expiration durations : " + policy.getExpiryMonths());
//                        validate = true;
//
//                    } else {
//                        System.err.println("Invalid");
//                    }
//
//                }
//                display();
//                break;
//            default:
//                System.out.println("Invalid choice!");
//        }
//
//    }

    public void checkCustomerDetails() {
        System.out.println("View Customer Details");
        try (InputStream input = Files.newInputStream(Paths.get(filename));
                BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter membership number: ");
            memberNo = scanner.nextLine();

            String line;

            while ((line = reader.readLine()) != null) {
                String[] user = line.split(" ");
                if (user.length >= 5 && user[4].equals(memberNo)) {
                    System.out.println("Username: " + user[0]);
                    System.out.println("Email: " + user[1]);
                    System.out.println("Phone: " + user[2]);
                    System.out.println("Membership Number: " + user[4]);

                    // Extract current value from earning.csv
                    String currentValue = getCurrentValueFromCSV();
                    if (currentValue != null) {
                        System.out.println("Current Value: " + currentValue);
                    } else {
                        System.out.println("Current value not found.");
                    }

                    break;
                }
            }
            System.out.println("Customer details not found for membership number: " + memberNo);
        } catch (Exception e) {
            System.out.println("Error viewing customer details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getCurrentValueFromCSV() {
        try (BufferedReader csvReader = new BufferedReader(new FileReader("earning.csv"))) {
            String row;
            // Skip header row
            csvReader.readLine();
            // Read the next row containing the current value
            if ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                // Assuming the current value is the third column
                return data[2].trim();
            }
        } catch (IOException e) {
            System.out.println("Error reading earning.csv: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void open(File document) throws IOException {

        Desktop dt = Desktop.getDesktop();
        dt.open(document);
    }

    public void checkEarningFile() {
        try {
            open(new File("earning.csv"));
            System.out.println(" ");

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void viewAllProducts() {
        Map<String, Integer> productQuantities = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("redemptionHistory.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data.length > 2) {
                    String item = data[2].split(": ")[1];
                    int quantity = Integer.parseInt(data[3].split(": ")[1]);

                    productQuantities.put(item, productQuantities.getOrDefault(item, 0) + quantity);
                }
            }

            System.out.printf("%-25s %-20s %n", "Product", "Total Quantity");
            for (Map.Entry<String, Integer> entry : productQuantities.entrySet()) {
                System.out.printf("%-30s %-20d %n", entry.getKey(), entry.getValue());
            }

        } catch (IOException e) {
            System.out.println("Error reading redemptionHistory.txt: " + e.getMessage());
        }
    }

    public void displayTopRedeemedItem() {
        Map<String, Integer> redeemedItems = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("redemptionHistory.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length < 4) {
                    continue; // Skip this line because it doesn't contain the expected data
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
                    continue; // Skip this line because it doesn't contain the expected data
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

    public void ActivityTracking() {
        System.out.println("Activity Tracking");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter MemberNo: ");
        memberNo = scanner.nextLine();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("redemptionHistory.txt"));
            String line;
            System.out.printf("%-20s %-20s %-30s %-10s%n", "Date", "Time", "Redeemed Item", "Quantity");
            while ((line = reader.readLine()) != null) {
                if (line.contains("Member No: " + memberNo + ",")) {
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

    }
}
