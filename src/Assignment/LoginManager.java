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

    public LoginManager() {
        scanner = new Scanner(System.in);
    }

    public void login() {
        try {
            Path path = Paths.get(filename);
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
                        // Additional logic if login is successful
                    }
                }
                if (!found) {
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
            System.in.read(); // PAUSE THE PROGRAM UNTIL USER PRESSES A KEY
            // Additional logic after login attempt
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
            ex.printStackTrace();
        }
    }
}

