package Assignment;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TARUC
 */
public class Main {

    private final static int POINTS_PER_RM = 10;
    private final static String ANSI_COLORNAME = "\u001B[37m";
    private final static String ANSI_RED_BACKGROUND = "\u001B[41m";

    // Declaring ANSI_RESET so that we can reset the color 
    public static final String ANSI_RESET = "\u001B[0m"; 
  
    public static void writeToRedemptionHistory(RedemptionItem redemptionItem, String memberID) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("redemptionHistory.txt", true));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        writer.write("Date: " + LocalDateTime.now().format(formatter) + ", Member ID: " + memberID + ", Redeemed Item: " + redemptionItem.getName() + ", Quantity: 1\n");
        writer.close();
    }

    // Record the redeemed item to the redemptionHistory.txt file
    public static void recordRedeemedItem(RedemptionItem redeemedItem, String memberID, int quantity) {
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedNow = now.format(formatter);
            String record = "Date: " + formattedNow + ", Member ID: " + memberID + ", Redeemed Item: " + redeemedItem.getName() + ", Quantity: " + quantity;

            // Always add a new record
            try (FileWriter writer = new FileWriter("redemptionHistory.txt", true)) {
                writer.write(record + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while recording the redeemed item.");
            e.printStackTrace();
        }
    }

    static void deductPoints(int total, List<Earning> filteredList) {
        int counter = 0;
        while (total > 0) {
            if (total >= filteredList.get(counter).getValue()) {
                total -= filteredList.get(counter).getValue();
                filteredList.get(counter).setValue(0);
                counter++;
            } else {
                filteredList.get(counter).setValue(filteredList.get(counter).getValue() - total);
                total = 0;
                    /////////
            }
        }
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    /**
     * @param args the command line arguments
     */
    public static void open(File document) throws IOException {
        Desktop dt = Desktop.getDesktop();
        dt.open(document);
    }

    public static void main(String[] args) {

        AccountOperations userAccount; // Use the interface type
        userAccount = new UserAccount();
        userAccount.displayMenu();

        RedemptionItem[] redemptionItems = {new Product("Umbrella", 2000, "Calvin Klein"), new Product("Shampoo", 200, "Shokutbutsu"), new Product("Toothpaste", 250, "Colgate"), new Voucher("Year end sale voucher", 100, 50), new Voucher("Gold voucher", 500, 85)};
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        boolean validate = false;
        Loyalty loyalty = new Loyalty();
        Policy policy = new Policy();
        String expiryMonths;
        
        while (isRunning) {
            try {
                System.out.println(ANSI_COLORNAME + ANSI_RED_BACKGROUND + " Welcome to our rewards system \n" +ANSI_RESET);

                //System.out.println("Welcome to our rewards system\n");
                System.out.println("0. Exit");

                System.out.println("1. Earn rewards");
                System.out.println("2. Redeem rewards");
                System.out.println("3. Report");
                System.out.println("4. View Profile");
                System.out.println("5. Show my referees");
                System.out.println("6. Open full earning history in external window");
                System.out.println("7. Update Expiration Duration");

                System.out.print("Enter your choice: ");
//                if (!scanner.hasNextLine()) {
//                    System.out.println("STOP");
//                }
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        System.out.print("Enter your member ID: ");
                        String memberID = scanner.nextLine();

                        ////////////////////////////////////////////////////
                        if (!memberIsExists(memberID)) {
                            System.out.println("User not found\n");
                            break;
                        }

                        System.out.print("Enter your invoice NO.: ");
                        String invoiceNo = scanner.nextLine();
                        System.out.print("Enter the total payment amount: ");
                        int value = (int) (Math.round(Double.parseDouble(scanner.nextLine()) * POINTS_PER_RM) * loyalty.getMultiplier(memberID));
                        new Earning(invoiceNo, value, memberID);
                        policy.updateExpiryDate();
                        System.out.println("You have earned a total of " + value + " points!");
                        System.out.print("\n");
                        break;

                    case "2":
                        System.out.print("Enter your member No.:");
                        String memberNo = scanner.nextLine();
                        if (!memberIsExists(memberNo)) {
                            System.out.println("User not found\n");
                            break;
                        }
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
                        System.out.print("Current points: " + totalPoints + '\n');

                        System.out.println("What would you like to redeem?");

                        int total = 0;
                        for (int i = 0; i < redemptionItems.length; i++) {
                            if (redemptionItems[i].getRedemptionValue() <= totalPoints) {
                                total++;
                                System.out.printf("%d %s (%d points)\n", i + 1, redemptionItems[i].getName(), redemptionItems[i].getRedemptionValue());
                            }

                        }

                        if (total <= 0) {
                            System.out.println("Sorry, you have insufficient points to redeem any item\n");
                            break;
                        }

                        System.out.print("Enter your choice:");
                        String redemptionChoice = scanner.nextLine();

                        while (!isNumeric(redemptionChoice) || Integer.parseInt(redemptionChoice) < 1 || Integer.parseInt(redemptionChoice) > redemptionItems.length) {
                            System.out.println("Invalid input!");
                            System.out.print("Enter your choice:");
                            redemptionChoice = scanner.nextLine();

                        }

                         {
                            try {
                                // write to txt file
                                writeToRedemptionHistory(redemptionItems[Integer.parseInt(redemptionChoice) - 1], memberNo);
                            } catch (IOException ex) {
                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        deductPoints(redemptionItems[Integer.parseInt(redemptionChoice) - 1].getRedemptionValue(), filteredList);

//                        for (Earning test : filteredList) {
//                            System.out.println(test.getValue());
//                        }
                        List<Earning> newEarnings = new ArrayList<>();
                        newEarnings.addAll(filteredList);
                        newEarnings.addAll(otherMembers);
                        Earning.rewriteToFile(newEarnings);
                        System.out.println("Redemption successful!\n");
                        break;

                    case "3":
                        // Code to display the report
                        System.out.println("1. View the most popular gift redeem"+ "\n");
                        System.out.println("2. View the least gift redeem"+ "\n");
                        System.out.println("3. User profile status"+ "\n");
                        System.out.print("Enter your choice: ");
                        String reportChoice = scanner.nextLine();
                        while (!isNumeric(reportChoice) || Integer.parseInt(reportChoice) < 1 || Integer.parseInt(reportChoice) > 3) {
                            System.out.println("Invalid input!");
                            System.out.print("Enter your choice: ");
                            reportChoice = scanner.nextLine();
                        }
                        Reporting reporting = new Reporting();

                        switch (reportChoice) {
                            case "1":
                                //to view the most popular gift redeem
                                reporting.displayTopRedeemedItem();
                                break;
                            case "2":
                                //to view the least gift redeem
                                reporting.displayLowRedeemedItem();
                                break;
                            case "3":
                                //to view user profile status
                                reporting.userProfileStatus();
                                break;
                        }
                        System.out.println(" ");
                        break;
                    case "4":
                        //viewprofile from useraccount
                        userAccount.viewProfile();
                        break;
                    case "5":
                        userAccount.showReferees();
                        break;
                    case "6":
                        try {
                            open(new File("earning.csv"));
                            System.out.println(" ");

                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "7":
                        validate = false;
                        while (!validate) {
                            System.out.print("Update the expiration durations in months (01-12):");
                            expiryMonths = scanner.nextLine();
                           // System.out.println(policy.validateMonth(expiryMonths));
                            if (policy.validateMonth(expiryMonths)) {
                                System.out.println("Updated!");
                                policy.setExpiryMonths(Integer.parseInt(expiryMonths));
                                //System.out.println(policy.getExpiryMonths());
                                validate = true;

                            } else {
                                System.err.println("Invalid input!");
                            }
                        }
                        break;
                    case "0":
                        isRunning = false;
                        break;
                    default:
                        System.out.println("Invalid input!");

                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static boolean memberIsExists(String memberID) {
        boolean found = false;

        // validate member ID
        try (InputStream input = Files.newInputStream(Paths.get("user.txt")); BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            String membershipNumber = memberID;
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
}

class EarningComparator implements java.util.Comparator<Earning> {

    @Override
    public int compare(Earning a, Earning b) {
        return a.getEarningDate().isBefore(b.getEarningDate()) ? -1 : 1;
    }
}
