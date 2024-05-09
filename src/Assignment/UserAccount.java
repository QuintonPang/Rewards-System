/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

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
import java.util.List;
import java.util.Random;
import java.util.Scanner;



public class UserAccount extends Account implements AccountOperations {

  private static final String filename = "user.txt";
  private Scanner scanner;

  public UserAccount() {
    super(null, null, null, null, null, null); // Set initial values to null
    scanner = new Scanner(System.in);
  }

  public static void clearScreen() {
    try {
      System.out.print("\033[H\033[2J");
    } catch (Exception e) {
      System.out.println("Error clearing screen!");
    }
  }

  @Override
  public void displayMenu() {
    System.out.println("------------------------------");
    System.out.println("|         Main Menu          |");
    System.out.println("------------------------------");
    System.out.println("| 1. Create User Account     |");
    System.out.println("| 2. Login                   |");
    System.out.println("| 3. Forgot Password         |");
    System.out.println("| 4. Exit                    |");
    System.out.println("------------------------------");
    System.out.print("Enter your choice: ");

    String choice = scanner.nextLine();
    switch (choice) {
      case "1":
        createAccount();
        break;
      case "2":
        login();
        break;
      case "3":
        forgot();
        break;
      case "4":
        System.exit(0);
      default:
        System.out.println("Invalid choice.");
        displayMenu();
    }
  }

  @Override

  public void createAccount() {
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

        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        String membershipNumber = "ABC" + String.format("%04d", randomNumber);
        System.out.println("Your membership number is: " + membershipNumber);
        writer.write(username + " " + email + " " + phone + " " + password + " " + membershipNumber + " " + "0");
        writer.newLine();

        writer.close();
        break;
      }

    } catch (IOException ex) {
      System.out.println("Error creating account: " + ex.getMessage());
    } finally {
      displayMenu();
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

  public void login() {
    // Implement login logic here
    try {
      Path path = Paths.get(filename.toString());
      InputStream input = Files.newInputStream(path);
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));
      System.out.println("\n Login \n");
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
          }
        }
        if (found) {
          System.out.println("Login successful.");
          displayMenuOption();

        } else {
          System.out.println("Invalid username or password.");
        }
        System.out.println("-------------------------------");
        System.out.println("Press any key to continue...");
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
      System.in.read(); // PAUSE THE PROGRAM UNTIL USER PRESSE
      displayMenu(); // DISPLAY THE MENU AGAIN
      reader.close(); // CLOSE THE READER
      input.close(); // CLOSE THE INPUT STREAM

    } catch (Exception ex) {
      System.out.print(ex.getMessage());
      ex.printStackTrace();
    }
  }

  public void forgot() {
    try (Scanner scanner = new Scanner(System.in);
        BufferedReader reader = Files.newBufferedReader(Paths.get(filename));
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), StandardOpenOption.TRUNCATE_EXISTING)) {

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
            System.out.println("Passwords do not match. Try again.");
            return; // Exit the method if passwords do not match
          }
        }
      }

      if (updated) {
        Files.write(Paths.get(filename), lines, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Password reset successful.");
        displayMenuOption();
      } else {
        System.out.println("Username or email not found.");
      }

    } catch (IOException ex) {
      System.out.println("Error: " + ex.getMessage());
    }
  }

  public void displayMenuOption() {
    System.out.println("------------------------------");
    System.out.println("|         Main Menu          |");
    System.out.println("------------------------------");
    System.out.println("| 1. Update User Details     |");
    System.out.println("| 2. Exit                    |");
    System.out.println("------------------------------");
    System.out.print("Enter your choice: ");

    String choice = scanner.nextLine();
    switch (choice) {
      case "1":
        viewProfile();
        break;
      case "2":
        System.exit(0);
      default:
        System.out.println("Invalid choice.");
        displayMenuOption();
    }
  }

  public String viewProfile() {
    System.out.println("View Profile");

    try (InputStream input = Files.newInputStream(Paths.get(filename));
        BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {

      System.out.println("\nEnter your Membership Number: ");
      String membershipNumber = scanner.nextLine();
      String line;

      while ((line = reader.readLine()) != null) {
        String[] user = line.split(" ");
        if (user[4].equals(membershipNumber)) {
          System.out.println("Username: " + user[0]);
          System.out.println("Email: " + user[1]);
          System.out.println("Phone: " + user[2]);
          System.out.println("Membership Number: " + user[4]);
          System.out.println("Transaction Record: " + user[5]);

          // Prompt the user outside of the loop
          boolean modifyDetails = promptModifyDetails();
          if (modifyDetails) {
            updateAccount(membershipNumber);
          } else {
            displayMenuOption();
          }

          return membershipNumber; // Return the membership number if the profile is found
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

    return null; // Return null if the profile is not found
  }

  private boolean promptModifyDetails() {
    System.out.println("Do you want to modify your account details? (Y/N): ");
    String choice = scanner.nextLine();
    return choice.equalsIgnoreCase("Y");
  }

  public void updateAccount(String membershipNumber) {
    System.out.println("Which details do you want to modify?");
    System.out.println("1. Username");
    System.out.println("2. Email");
    System.out.println("3. Phone");
    System.out.println("4. Password");
    System.out.println("5. Transaction Record");
    System.out.println(". 6Go back to main menu");
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
        updateTransactionRecord(membershipNumber);
      case "6":
        displayMenuOption();
        break;
      default:
        System.out.println("Invalid choice.");
        updateAccount(membershipNumber); // Pass membershipNumber to recursive call
    }
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
            System.out.println("Invalid username format should be (6-20 characters, no special characters)");
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
        System.out.println("Membership number not found.");
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
            System.out.println("Invalid email format. Email not updated.");
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
        System.out.println("Membership number not found.");
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
      System.out.println("Invalid email format. Please try again.");
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
            System.out.println("Invalid phone number format should be (digits only)");
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
        System.out.println("Membership number not found.");
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
            System.out.println("Invalid password format should be (6-20 characters)");
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
        System.out.println("Membership number not found.");
      }
    } catch (IOException ex) {
      System.out.println("Error updating password: " + ex.getMessage());
    }

  }

  public void updateTransactionRecord(String membershipNumber) {
    try {
      Path path = Paths.get(filename);
      List<String> lines = Files.readAllLines(path);
      boolean updated = false;

      for (int i = 0; i < lines.size(); i++) {
        String line = lines.get(i);
        String[] user = line.split(" ");
        if (user.length >= 5 && user[4].equals(membershipNumber)) {
          System.out.print("Enter new transaction record: ");
          String newTransactionRecord = scanner.nextLine();

          // Update the transaction record in the line
          user[5] = newTransactionRecord;
          lines.set(i, String.join(" ", user));
          updated = true;
          break; // Exit the loop after updating the transaction record
        }
      }

      if (updated) {
        Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Transaction record updated successfully.");
      } else {
        System.out.println("Membership number not found.");
      }
    } catch (IOException ex) {
      System.out.println("Error updating transaction record: " + ex.getMessage());
    }

  }

}
