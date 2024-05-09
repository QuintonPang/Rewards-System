package Assignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Policy {

    private Map<String, String> productEligibility;

    public Policy() {
        this.productEligibility = new HashMap<>();
        initializeProductEligibility();
    }

    private void initializeProductEligibility() {
        // get required tier in file
        productEligibility.put("Toothpaste", "Bronze"); // Example: Toothpaste requires Bronze membership
        productEligibility.put("Umbrella", "Gold");   // Example: Umbrella requires Gold membership
    }

    // getCustomerGrade from loyalty
    public boolean checkEligibilityCriteria(String productName, String customerMembershipTier) {
        // Retrieve the required membership tier for the specified product
        String requiredTier = productEligibility.get(productName);
        //return true , can redeem. return false, cannot redeem
        return customerMembershipTier.equalsIgnoreCase(requiredTier);
    }

    // Check expiry date
    public void checkExpiryDate() {
        String csvFile = "earning.csv";
        String line = "";
        String csvSplitBy = ",";
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                // The expiration date is at index 5 in the CSV file
                String expirationDateString = data[5];
                LocalDate expirationDate = LocalDate.parse(expirationDateString, formatter);

                if (expirationDate.isBefore(currentDate)) {
                    System.out.println("Points for entry " + data[0] + " have expired.");
                    // Perform action to remove the points (pending)
                } else {
                    System.out.println("Points for entry " + data[0] + " are still valid.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to update date in the earning file
    public void updateEarningFileDate() {
        // Input and output file paths
        String inputFile = "earning.csv";
        String outputFile = "updated_earning.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile)); BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by comma to extract date field
                String[] fields = line.split(",");
                if (fields.length >= 6) {
                    // Extract the earning date field
                    String earningDateString = fields[4];

                    // Parse earning date string to LocalDate
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate earningDate = LocalDate.parse(earningDateString, formatter);

                    // Add 3 months to the earning date
                    LocalDate updatedDate = earningDate.plusMonths(3);

                    // Format updated date back to string
                    String updatedDateString = formatter.format(updatedDate);

                    // Update the expiration date field (index 5) with the updated date
                    fields[5] = updatedDateString;

                    // Join the fields back to a single line
                    String updatedLine = String.join(",", fields);

                    // Write the updated line to the output file
                    writer.write(updatedLine);
                    writer.newLine();
                } else {
                    // Handle malformed lines
                    System.out.println("Malformed line: " + line);
                }
            }
            System.out.println("Date updated successfully.");
            

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rename the updated file to overwrite the original file
        try {
            java.io.File originalFile = new java.io.File(inputFile);
            java.io.File updatedFile = new java.io.File(outputFile);
            if (originalFile.exists()) {
                if (originalFile.delete()) {
                    if (!updatedFile.renameTo(originalFile)) {
                        System.out.println("Failed to rename the updated file.");
                    }
                } else {
                    System.out.println("Failed to delete the original file.");
                }
            } else {
                System.out.println("Original file does not exist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Policy policy = new Policy();
        //policy.updateEarningFileDate();
        //policy.checkExpiryDate();
        
        
        System.out.println(policy.checkEligibilityCriteria("Umbrella", "Gold"));
        
    }

}
