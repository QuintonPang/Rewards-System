/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author munchun
 */


 public class AdminDashBoard {
    private static final String filename = "user.txt";
    private String memberNo;
    public static void main(String[] args) {
        AdminDashBoard adminDashBoard = new AdminDashBoard();
        adminDashBoard.display();
    }
    public void display() {
        System.out.println("\n Admin Dashboard \n");
        System.out.println("1. Check Customer Details");// ??? inside reporting.java have similar function
        System.out.println("2. View all products");//only settle this
        System.out.println("3.feeback"); // ??? trying
        System.out.print("Enter your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1:
            checkCustomerDetails();
            break;
            case 2:
            // Call viewAllProducts() method
            viewAllProducts() ;
            break;
            case 3:
            break;
            case 4:
            break;
            case 5:
            System.out.println("Logging out...");
            // Call logout() method
            break;
            default:
            System.out.println("Invalid choice!");
        }

        
    }

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
    
    private String getCurrentValueFromCSV() {
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



    public void viewAllProducts(){
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

}