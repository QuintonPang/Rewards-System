/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

/**
 *
 * @author munchun
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;
import java.util.Scanner;

public class RegistrationManager {
    private static final String filename = "user.txt";
    Scanner scanner = new Scanner(System.in);

    public void createAccountUser() {

        try {
            Path path = Paths.get(filename);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);

            System.out.println("\n Create User Account \n");
            while (true) {
                System.out.print("Enter your username: ");
                String username = scanner.nextLine();

                if (!isValidUsername(username)) {
                    System.out.println("Invalid username format should be (6-20 characters, no special characters)");
                    continue;
                }

                System.out.print("Enter your email: ");
                String email = scanner.nextLine();
                if (!isValidEmail(email)) {
                    System.out.println("Invalid email format should be (format: xxxxxx@yyy.zzz)");
                    continue;
                }

                System.out.print("Enter your phone number: ");
                String phone = scanner.nextLine();
                if (!isValidPhone(phone)) {
                    System.out.println("Invalid phone number format should be (digits only)");
                    continue;
                }

                System.out.print("Enter your password: ");
                String password = scanner.nextLine();
                if (!isValidPassword(password)) {
                    System.out.println("Invalid password format should be (6-20 characters)");
                    continue;
                }

                System.out.print("Reffered by (Member ID) (Empty to skip): ");
                String referrer = scanner.nextLine();
                if (referrer.length() > 0) {
                    boolean validMember = Main.memberIsExists(referrer);
                    if (!validMember && referrer.length() > 0) {
                        System.out.println("Member does not exist");
                        continue;
                    } else {
                        new Earning("Referral", 10, referrer);
                    }
                }

                // Check if the username, email, or phone already exist in the file
                if (isAlreadyRegistered(username, email, phone)) {
                    System.out.println("Username, email, or phone number already exists. Please try again.");
                    continue;
                }

                Random random = new Random();
                int randomNumber = random.nextInt(10000);
                String membershipNumber = "ABC" + String.format("%04d", randomNumber);
                System.out.println("Your membership number is: " + membershipNumber);
                System.out.println("RANNNNNNN");
                writer.write(username + " " + email + " " + phone + " " + password + " " + membershipNumber + " " + "0"
                        + " " + referrer);
                writer.newLine();

                writer.close();
                break;
            }

        } catch (IOException ex) {
            System.out.println("Error creating account: " + ex.getMessage());
        } finally {
            System.out.println("-------------------------------");
            System.out.println("Press any key to continue...");
        }
    }

    private boolean isAlreadyRegistered(String username, String email, String phone) throws IOException {
        Path path = Paths.get(filename);
        if (Files.exists(path)) {
            try (Scanner fileScanner = new Scanner(path)) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(" ");
                    if (parts.length >= 3
                            && (parts[0].equals(username) || parts[1].equals(email) || parts[2].equals(phone))) {
                        return true; // Username, email, or phone already exists in the file
                    }
                }
            }
        }
        return false; // Not already registered
    }

    private boolean isValidUsername(String username) {
        return username.matches("[a-zA-Z0-9]+") && username.length() >= 6 && username.length() <= 20;

    }

    private boolean isValidPhone(String phone) {
        return phone.matches("[0-9]+") && phone.length() == 10 || phone.length() == 11;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6 && password.length() <= 20;
    }

    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+");
    }

    public void createAccountStaff() {
        try {
            Path path = Paths.get("staff.txt");
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);

            System.out.println("\n Create Staff Account \n");

            // You can add validation logic similar to createAccountUser for staff details
            // For simplicity, I'm omitting some validation checks here

            System.out.print("Enter your authentication key: ");
            String key = scanner.nextLine();
            if (!key.equals("A123")) {
                System.out.println("Invalid authentication key");
                writer.close(); // Close the writer before returning
                return;
            }

            System.out.print("Enter your username: ");
            String username = scanner.nextLine();

            System.out.print("Enter your email: ");
            String email = scanner.nextLine();

            System.out.print("Enter your phone number: ");
            String phone = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            // Check if the username, email, or phone already exist in the file
            if (isAlreadyRegistered(username, email, phone)) {
                System.out.println("Username, email, or phone number already exists. Please try again.");
                writer.close(); // Close the writer before returning
                return;
            }

            // Generate staffID
            Random random = new Random();
            int randomNumber = random.nextInt(10000);
            String staffID = "STF" + String.format("%04d", randomNumber);
            System.out.println("Your staff ID is: " + staffID);

            // Write staff details to the staff file
            writer.write(username + " " + email + " " + phone + " " + password + " " + staffID);
            writer.newLine();
            writer.close();

            System.out.println("Staff account created successfully.");
            // If needed, you can add further logic here to redirect to the admin dashboard
            // AdminDashBoard adminDashBoard = new AdminDashBoard();
            // adminDashBoard.display();

        } catch (IOException ex) {
            System.out.println("Error creating account: " + ex.getMessage());
        } finally {
            System.out.println("-------------------------------");
            System.out.println("Press any key to continue...");
        }
    }

}