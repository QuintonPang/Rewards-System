/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

/**
 *
 * @author munchun
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RegistrationManager {
    private static final String filename = "user.txt";
    Scanner scanner = new Scanner(System.in);
    String memberNo;
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

                System.out.print("Referred by (Member No) (Empty to skip): ");
                String referrer = scanner.nextLine();
                if (referrer.length() > 0) {
                    boolean validMember = memberIsExists(referrer);
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

                // Generate a random membership number
                Random random = new Random();
                int randomNumber = random.nextInt(10000);
                memberNo = "ABC" + String.format("%04d", randomNumber);
                System.out.println("Your membership number is: " + memberNo);

                // Write the user account details to the file
                writer.write(username + " " + email + " " + phone + " " + password + " " + memberNo + " " + referrer);
                writer.newLine();

                // Close the writer
                writer.close();

                // Sort the user accounts based on username
                sortUserAccountsByUsername(filename);

                break; // Exit the loop after successfully creating the account
            }

        } catch (IOException ex) {
            System.out.println("Error creating account: " + ex.getMessage());
        } finally {
            System.out.println("-------------------------------");
            System.out.println("Press any key to continue...");
        }
    }

    private void sortUserAccountsByUsername(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            Collections.sort(lines); // Sort the lines
            Files.write(Paths.get(filename), lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ex) {
            System.out.println("Error sorting user accounts: " + ex.getMessage());
        }
    }


    

    public void createAccountStaff() {
        try {
            Path path = Paths.get(filename);
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
            BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND);

            System.out.println("\n Create Staff Account \n");

            System.out.print("Enter your authentication key: ");
            String key = scanner.nextLine();
            if (!key.equals("A123")) {
                System.out.println("Invalid authentication key");
                writer.close(); // Close the writer before returning
                return;
            }

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
                AdminDashBoard adminDashBoard = new AdminDashBoard();
                adminDashBoard.display();

                break; // Break out of the while loop after successfully creating the staff account
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

    
        public static boolean memberIsExists(String memberNo) {
        boolean found = false;

        // validate member ID
        try (InputStream input = Files.newInputStream(Paths.get("user.txt")); BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            String membershipNumber = memberNo;
            String line;

            while ((line = reader.readLine()) != null) {
                String[] user = line.split(" ");
                if (user[4].equals(membershipNumber)) {
                    found = true;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: user.txt not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading user accounts: " + e.getMessage());
            e.printStackTrace();
        }

        return found;

    }

    public String getMemberNo() {
        return memberNo;
    }
        

}