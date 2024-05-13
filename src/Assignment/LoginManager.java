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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class LoginManager {

    private static final String filename = "user.txt";
    private Scanner scanner;
    MemberDashBoard userAccount = new MemberDashBoard();
    private String memberNo;
    private String staffNo;

    public LoginManager() {
        scanner = new Scanner(System.in);
    }

    public boolean login() {
        try {
            Path path = Paths.get(filename);
            InputStream input = Files.newInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            System.out.println("\n Login \n");
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            boolean loginSuccessful = false; // Flag to track login status
            try {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String[] user = line.split(" ");
                    if (user[0].equals(username) && user[3].equals(password)) {
                        memberNo = user[4];
                        // Additional logic if login is successful
                        System.out.println("Login successful.");
                        System.out.println("-------------------------------");
                        // Direct to the user menu
                        System.out.println("Press any key to continue...");
                        loginSuccessful = true; // Set the flag to true upon successful login
                        return loginSuccessful;
                        //continue; 
                    }
                }
                if (!loginSuccessful) { // Check login status before displaying invalid message
                    boolean invalidInput = true;
                    while (invalidInput) {
                        System.out.println("\nInvalid username or password.");
                        System.out.println("-------------------------------");
                        return false;
//                        // Ask user to re-login or exit to menu
//                        System.out.println("1. Re-login");
//                        System.out.println("2. Exit to menu");
//                        System.out.print("Enter your choice: ");
//                        String choiceStr = scanner.nextLine();
//                        if (choiceStr.matches("[12]")) {
//                            int choice = Integer.parseInt(choiceStr);
//                            switch (choice) {
//                                case 1:
//                                    // Re-login
//                                    login();
//                                    invalidInput = false; // Break out of the loop after successful re-login
//                                    break;
//                                case 2:
//                                    userAccount.displayLoginMenu();
//                                    invalidInput = false; // Break out of the loop after exiting to menu
//                                    break;
//                                default:
//                                    System.out.println("Invalid choice. Exiting to menu.");
//                                    userAccount.displayLoginMenu();
//                                    break;
//                            }
//                        } else {
//                            System.out.println("Invalid choice. Please enter 1 or 2.");
//                        }
                    }
                }
            } catch (IOException ex) {
                System.out.println("Error reading user accounts: " + ex.getMessage());
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException ex) {
                    System.out.println("Error closing reader or input stream: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            ex.printStackTrace();
            return false;
        }
        return false;
    }

//    public boolean loginStaff() {
//        try {
//            Path path = Paths.get("staff.txt");
//            InputStream input = Files.newInputStream(path);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//            System.out.println("\n Login Staff \n");
//            System.out.print("Enter your username: ");
//            String username = scanner.nextLine();
//            System.out.print("Enter your password: ");
//            String password = scanner.nextLine();
//            try {
//                String line = null;
//                boolean found = false;
//                while ((line = reader.readLine()) != null) {
//                    String[] user = line.split(" ");
//                    if (user[0].equals(username) && user[3].equals(password)) {
//                        found = true;
//                        staffNo = user[4];
//                        System.out.println("Login successful.");
//                        System.out.println("-------------------------------");
//                        return true;
//                        // Direct to the staff menu
////                        AdminDashBoard adminDashBoard = new AdminDashBoard();
////                        adminDashBoard.display();
//                    }
//                }
//                if (!found) {
//                    System.out.println("Invalid username or password.");
//                    System.out.println("-------------------------------");
//                    return false;
////                    userAccount.displayLoginMenu();
//
//                }
//
//            } catch (IOException ex) {
//                System.out.println("Error reading staff accounts: " + ex.getMessage());
//            } finally {
//                try {
//                    if (reader != null) {
//                        reader.close();
//                    }
//                    if (input != null) {
//                        input.close();
//                    }
//                } catch (IOException ex) {
//                    System.out.println("Error closing reader or input stream: " + ex.getMessage());
//                }
//            }
//            System.in.read(); // PAUSE THE PROGRAM UNTIL USER PRESSES A KEY
//            // Additional logic after login attempt
//        } catch (Exception ex) {
//            System.out.print(ex.getMessage());
//            ex.printStackTrace();
//        }
//        return false;
//    }
    public String getMemberNo() {
        return memberNo;
    }

    public String getStaffNo() {
        return staffNo;
    }

    AdminInfo loginStaff() {

        try {
            Path path = Paths.get("staff.txt");
            InputStream input = Files.newInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            System.out.println("\n Login Staff \n");
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            try {
                String line = null;
                boolean found = false;
                while ((line = reader.readLine()) != null) {
                    String[] user = line.split(" ");
                    if (user[0].equals(username) && user[3].equals(password)) {
                        found = true;
                        AdminInfo adminInfo = new AdminInfo(user[0], user[1], user[2], user[3], user[4]);
                        System.out.println("Login successful.");
                        System.out.println("-------------------------------");
                        return adminInfo;
                        // Direct to the staff menu
//                        AdminDashBoard adminDashBoard = new AdminDashBoard();
//                        adminDashBoard.display();
                    }
                }
                if (!found) {
                    System.out.println("Invalid username or password.");
                    System.out.println("-------------------------------");
                    return null;
//                    userAccount.displayLoginMenu();

                }

            } catch (IOException ex) {
                System.out.println("Error reading staff accounts: " + ex.getMessage());
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException ex) {
                    System.out.println("Error closing reader or input stream: " + ex.getMessage());
                }
            }
            System.in.read(); // PAUSE THE PROGRAM UNTIL USER PRESSES A KEY
            // Additional logic after login attempt
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    MemberInfo loginMember() {
        try {
            Path path = Paths.get(filename);
            InputStream input = Files.newInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            System.out.println("\n Login \n");
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            boolean loginSuccessful = false; // Flag to track login status
            try {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String[] user = line.split(" ");
                    if (user[0].equals(username) && user[3].equals(password)) {
                        memberNo = user[4];
                        // Additional logic if login is successful
                        System.out.println("Login successful.");
                        System.out.println("-------------------------------");
                        // Direct to the user menu
                        System.out.println("Press any key to continue...");
                        loginSuccessful = true; // Set the flag to true upon successful login
                        MemberInfo memberInfo = new MemberInfo(user[0], user[1], user[2], user[3], user[4], user[5]);

                        return memberInfo; //continue; 
                    }
                }
                if (!loginSuccessful) { // Check login status before displaying invalid message
                    boolean invalidInput = true;
                    while (invalidInput) {
                        System.out.println("\nInvalid username or password.");
                        System.out.println("-------------------------------");
                        return null;
//                        // Ask user to re-login or exit to menu
//                        System.out.println("1. Re-login");
//                        System.out.println("2. Exit to menu");
//                        System.out.print("Enter your choice: ");
//                        String choiceStr = scanner.nextLine();
//                        if (choiceStr.matches("[12]")) {
//                            int choice = Integer.parseInt(choiceStr);
//                            switch (choice) {
//                                case 1:
//                                    // Re-login
//                                    login();
//                                    invalidInput = false; // Break out of the loop after successful re-login
//                                    break;
//                                case 2:
//                                    userAccount.displayLoginMenu();
//                                    invalidInput = false; // Break out of the loop after exiting to menu
//                                    break;
//                                default:
//                                    System.out.println("Invalid choice. Exiting to menu.");
//                                    userAccount.displayLoginMenu();
//                                    break;
//                            }
//                        } else {
//                            System.out.println("Invalid choice. Please enter 1 or 2.");
//                        }
                    }
                }
            } catch (IOException ex) {
                System.out.println("Error reading user accounts: " + ex.getMessage());
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException ex) {
                    System.out.println("Error closing reader or input stream: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            ex.printStackTrace();
            
        }
        return null;

    }

}

class AdminInfo {

    private String username;
    private String email;
    private String phone;
    private String password;
    private String adminNo;

    public AdminInfo(String username, String email, String phone, String password, String adminNo) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.adminNo = adminNo;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getAdminNo() {
        return adminNo;
    }
}

class MemberInfo {

    private String username;
    private String email;
    private String phone;
    private String password;
    private String memberNo;
    private String referralMember;

    public MemberInfo(String username, String email, String phone, String password, String memberNo, String referralMember) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.memberNo = memberNo;
        this.referralMember = referralMember;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public String getReferralMember() {
        return referralMember;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMemberNo(String custAccountNo) {
        this.memberNo = custAccountNo;
    }

    public void setReferralMember(String referralMember) {
        this.referralMember = referralMember;
    }

}
