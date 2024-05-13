/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class UserAccount extends Account implements AccountOperations {

    private static final String filename = "user.txt";
    Scanner scanner = new Scanner(System.in);
    private String memberNo;
    Loyalty loyalty = new Loyalty();

    public UserAccount() {
        super(null, null, null, null, null, null); // Set initial values to null
        scanner = new Scanner(System.in);
    }

    @Override
    public void printStartMenu() {
        System.out.println("------------------------------");
        System.out.println("|         Start Menu         |");
        System.out.println("------------------------------");
        System.out.println("| 1. Create User Account     |");
        System.out.println("| 2. Login                   |");
        System.out.println("| 3. Forgot Password         |");
        System.out.println("| 4. Exit                    |");
        System.out.println("------------------------------");
        System.out.print("Enter your choice: ");

//        String choice = scanner.nextLine();
//        switch (choice) {
//            case "1":
//                displayRegisterUser();
//                break;
//            case "2":
//                displayLoginMenu();
//                break;
//            case "3": {
//                try {
//                    forgot();
//                } catch (IOException ex) {
//                    Logger.getLogger(UserAccount.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            break;
//
//            case "4":
//                //System.exit(0);
//                //  System.out.println("ran");
//                break;
//            default:
//                System.out.println("Invalid choice.");
//                displayMenu();
//        }
    }

    //menu for user and admin
//    public void displayRegisterUser() {
//        System.out.println("------------------------------");
//        System.out.println("|         Main Menu          |");
//        System.out.println("------------------------------");
//        System.out.println("| 1. Customer Register       |");
//        System.out.println("| 2. Admin Register          |");
//        System.out.println("| 3. Exit                    |");
//        System.out.println("------------------------------");
//        System.out.print("Enter your choice: ");
//
//        String choice = scanner.nextLine();
//        switch (choice) {
//            case "1":
//                createAccount();
//                break;
//            case "2":
//                RegistrationManager registrationManager = new RegistrationManager();
//                registrationManager.createAccountStaff();
//                break;
//            case "3":
//                displayMenu();
//                break;
//            default:
//                System.out.println("Invalid choice.");
//                displayRegisterUser();
//        }
//    }
//    public void displayLoginMenu() {
//        System.out.println("------------------------------");
//        System.out.println("|         Main Menu          |");
//        System.out.println("------------------------------");
//        System.out.println("| 1. Customer Login          |");
//        System.out.println("| 2. Admin Login             |");
//        System.out.println("| 3. Exit                    |");
//        System.out.println("------------------------------");
//        System.out.print("Enter your choice: ");
//
//        String choice = scanner.nextLine();
//        switch (choice) {
//            case "1":
//                login();
//                break;
//            case "2":
//                LoginManager loginManager = new LoginManager();
//                loginManager.loginStaff();
//                memberNo = loginManager.getMemberNo();
//                break;
//            case "3":
//                displayMenu();
//                break;
//            default:
//                System.out.println("Invalid choice.");
//                displayLoginMenu();
//        }
//    }
    @Override
    public void createAccount() {
        RegistrationManager registrationManager = new RegistrationManager();
        registrationManager.createAccountUser();
        memberNo = registrationManager.getMemberNo();
    }

    @Override
    public void showReferees() {

        try (InputStream input = Files.newInputStream(Paths.get(filename)); BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

            String line;
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.println("|             My Referees               |");
            System.out.println("-----------------------------------------");
            int total = 0;
            while ((line = reader.readLine()) != null) {
                String[] user = line.split(" ");
                if (user.length >= 6 && user[5].equals(memberNo)) {

                    total += 1;
                    System.out.println("| Username\t\t: " + user[0] + "\t|");
                    System.out.println("| Membership Number\t: " + user[4] +"\t|");

                    System.out.println("-----------------------------------------");

                }
            }

            System.out.println("Total: " + total);

            System.out.println("Press any key to continue");

            System.in.read(); // PAUSE THE PROGRAM UNTIL USER PRESSE

        } catch (FileNotFoundException e) {
            System.out.println("\u001B[31mError: user.txt not found!\u001B[0m");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading user accounts: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public boolean login() {
        LoginManager loginManager = new LoginManager();
        if (loginManager.login()) {
            memberNo = loginManager.getMemberNo();
            return true;
        } else {
            return false;
        }

    }

    public void forgot() throws IOException {

        System.out.println("\n Forgot Password \n");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        List<String> lines = Files.readAllLines(Paths.get(filename));
        boolean updated = false;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            String[] user = line.split(" ");
            if (user.length >= 5 && user[0].equals(username) && user[1].equals(email)) {
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                System.out.print("Confirm new password: ");
                String confirmPassword = scanner.nextLine();

                if (newPassword.equals(confirmPassword)) {
                    user[3] = newPassword;
                    lines.set(i, String.join(" ", user));
                    updated = true;
                    break;
                } else {
                    System.out.println("\u001B[31m Passwords do not match! \u001B[0m");
                }
            }
        }

        if (updated) {
            Files.write(Paths.get(filename), lines, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Password reset successful.");
        } else {
            System.out.println("\u001B[31m Username or email not found! \u001B[0m");

        }

    }

    public void viewProfile() throws FileNotFoundException {

        // Assuming getMemberId() returns the correct membership number
        String membershipNumber = getMemberNo();
        List<Earning> earnings = CSVWrite.getAllEarnings();

        List<Earning> filteredList = new ArrayList<>();
        List<Earning> otherMembers = new ArrayList<>();

        for (Earning earn : earnings) {

            if (earn.getMemberNo().equals(memberNo)) {

                filteredList.add(earn);
            } else {
                otherMembers.add(earn);
            }
        }
        Collections.sort(filteredList, new EarningComparator());
        int totalPoints = 0;
        for (Earning e : filteredList) { // for each Player p in list         
            totalPoints += e.getValue();
        }
        System.out.println("-----------------------------------------------------------------");
        System.out.println("|                         View Profile                          |");
        System.out.println("-----------------------------------------------------------------");

        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] user = line.split(" ");
                if (user.length >= 5 && user[4].equals(membershipNumber)) {
                    System.out.println("| Username\t\t: " + user[0] + "\t\t\t\t|");
                    System.out.println("| Email\t\t\t: " + user[1] + "\t\t\t|");
                    System.out.println("| Phone\t\t\t: " + user[2] + "\t\t\t\t|");
                    System.out.println("| Membership Number\t: " + user[4] + "\t\t\t\t|");
                    System.out.println("| Current Point\t\t: " + totalPoints + "\t\t\t\t\t|");
                    System.out.println("| " + loyalty.toString(memberNo));
                    System.out.println("-----------------------------------------------------------------");

                    // Prompt the user outside of the loop
                    boolean modifyDetails = promptModifyDetails();
                    if (modifyDetails) {
                        updateAccount(membershipNumber);
                    } else {
                        return;
                    }

                    return; // Return if the profile is found
                }
            }

            System.out.println("User not found.");

        } catch (FileNotFoundException e) {
            System.out.println("Error: user.txt not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading user accounts: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean promptModifyDetails() {
        System.out.print("Do you want to modify your account details? (Y/N): ");
        String choice = scanner.nextLine();
        return choice.equalsIgnoreCase("Y");
    }

    public void updateAccount(String membershipNumber) {
        System.out.println("Which details do you want to modify?");
        System.out.println("1. Username");
        System.out.println("2. Email");
        System.out.println("3. Phone");
        System.out.println("4. Password");
        System.out.println("5. Go back to main menu");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                updateUsername(membershipNumber); // Pass membershipNumber to updateUsername
                break;
            case "2":
                updateEmail(membershipNumber);
                break;
            case "3":
                updatePhone(membershipNumber);
                break;
            case "4":
                updatePassword(membershipNumber);
                break;
            case "5":

                break;
            default:
                System.out.println("Invalid choice!\n");
                updateAccount(memberNo); // Pass membershipNumber to recursive call
        }
    }

    private boolean isValidUsername(String username) {
        return username.length() >= 6 && username.length() <= 20 && !username.matches("[^a-zA-Z0-9]");
    }

    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+");
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("[0-9]+") && phone.length() == 10 || phone.length() == 11;
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6 && password.length() <= 20;
    }

    public void updateUsername(String membershipNumber) {
        try {
            Path path = Paths.get(filename);
            List<String> lines = Files.readAllLines(path);
            boolean updated = false;

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] user = line.split(" ");
                if (user.length >= 5 && user[4].equals(membershipNumber)) {
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();

                    // Check if the new username is valid
                    if (!isValidUsername(newUsername)) {
                        System.out.println("\u001B[31m Invalid username format should be (6-20 characters && no special characters)! \u001B[0m");
                        return; // Exit the method if the username is invalid
                    }

                    // Update the username in the line
                    user[0] = newUsername;
                    lines.set(i, String.join(" ", user));
                    updated = true;
                    break; // Exit the loop after updating the username
                }
            }

            if (updated) {
                Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Username updated successfully.");
            } else {
                System.out.println("\u001B[31m Membership number not found! \u001B[0m");
            }
        } catch (IOException ex) {
            System.out.println("Error updating username: " + ex.getMessage());
        }
    }

    public void updateEmail(String membershipNumber) {
        try {
            Path path = Paths.get(filename);
            List<String> lines = Files.readAllLines(path);
            boolean updated = false;

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] user = line.split(" ");
                if (user.length >= 5 && user[4].equals(membershipNumber)) {
                    String newEmail = promptForValidEmail(); // Validate and get new email from user input
                    if (newEmail == null) {
                        System.out.println("\u001B[31m Invalid email format! Email not updated! \u001B[0m");
                        return; // Exit if the user input is invalid
                    }

                    user[1] = newEmail;
                    lines.set(i, String.join(" ", user));
                    updated = true;
                    break;
                }
            }

            if (updated) {
                Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Email updated successfully.");
            } else {
                System.out.println("\u001B[31m Membership number not found! \u001B[0m");
            }
        } catch (IOException ex) {
            System.out.println("Error updating email: " + ex.getMessage());
        }
    }

    private String promptForValidEmail() {
        while (true) {
            System.out.print("Enter new email: ");
            String newEmail = scanner.nextLine();
            if (isValidEmail(newEmail)) {
                return newEmail;
            }
            System.out.println("\u001B[31m Invalid email format! Please try again! \u001B[0m");
        }
    }

    public void updatePhone(String membershipNumber) {
        try {
            Path path = Paths.get(filename);
            List<String> lines = Files.readAllLines(path);
            boolean updated = false;

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] user = line.split(" ");
                if (user.length >= 5 && user[4].equals(membershipNumber)) {
                    System.out.print("Enter new phone number: ");
                    String newPhone = scanner.nextLine();

                    // Check if the new phone number is valid
                    if (!isValidPhone(newPhone)) {
                        System.out.println("\u001B[31m Invalid phone number format should be (digits only)! \u001B[0m");
                        return; // Exit the method if the phone number is invalid
                    }

                    // Update the phone number in the line
                    user[2] = newPhone;
                    lines.set(i, String.join(" ", user));
                    updated = true;
                    break; // Exit the loop after updating the phone number
                }
            }

            if (updated) {
                Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Phone number updated successfully.");
            } else {
                System.out.println("\u001B[31m Membership number not found \u001B[0m");
            }
        } catch (IOException ex) {
            System.out.println("Error updating phone number: " + ex.getMessage());
        }

    }

    public void updatePassword(String membershipNumber) {
        try {
            Path path = Paths.get(filename);
            List<String> lines = Files.readAllLines(path);
            boolean updated = false;

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] user = line.split(" ");
                if (user.length >= 5 && user[4].equals(membershipNumber)) {
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();

                    // Check if the new password is valid
                    if (!isValidPassword(newPassword)) {
                        System.out.println("\u001B[31mInvalid password format should be (6-20 characters)! \u001B[0m");
                        return; // Exit the method if the password is invalid
                    }

                    // Update the password in the line
                    user[3] = newPassword;
                    lines.set(i, String.join(" ", user));
                    updated = true;
                    break; // Exit the loop after updating the password
                }
            }

            if (updated) {
                Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("\u001B[31mMembership Number not found! \u001B[0m");
            }
        } catch (IOException ex) {
            System.out.println("Error updating password: " + ex.getMessage());
        }

    }

    @Override
    public String getMemberNo() {
        return memberNo;
    }

}
