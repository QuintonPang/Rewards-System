package Assignment;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

    public static void writeToRedemptionHistory(RedemptionItem redemptionItem, String memberNo) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("redemptionHistory.txt", true));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        writer.write("Date: " + LocalDateTime.now().format(formatter) + ", Member No: " + memberNo + ", Redeemed Item: " + redemptionItem.getName() + ", Quantity: 1\n");
        writer.close();
    }

    // Record the redeemed item to the redemptionHistory.txt file
    public static void recordRedeemedItem(RedemptionItem redeemedItem, String memberNo, int quantity) {
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedNow = now.format(formatter);
            String record = "Date: " + formattedNow + ", Member No: " + memberNo + ", Redeemed Item: " + redeemedItem.getName() + ", Quantity: " + quantity;

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

    //Main Reward System Menu
    public static void printCustomerMainMenu() {
        System.out.println("\n--------------------------------------------------------");
        System.out.print("|           ");
        System.out.print(ANSI_COLORNAME + ANSI_RED_BACKGROUND + " Welcome to our rewards system " + ANSI_RESET);
        System.out.println("            |");
        System.out.println("--------------------------------------------------------");
        System.out.println("| 0. Exit                                              |");
        System.out.println("| 1. Earn rewards                                      |");
        System.out.println("| 2. Redeem rewards                                    |");
        System.out.println("| 3. View Profile                                      |");
        System.out.println("| 4. Show my referees                                  |");
        System.out.println("--------------------------------------------------------");

        System.out.print("Enter your choice: ");
//                if (!scanner.hasNextLine()) {
//                    System.out.println("STOP");
//                }

        //remove 3report
        //remove 6openg earning
        //remove 7update expiration
    }

//    public static void printAdminMainMenu() {
//        System.out.println("\n--------------------------------------------------------");
//        System.out.println("|                  Admin Dashboard                     |");
//        System.out.println("--------------------------------------------------------");
//        System.out.println("| 1. Check Customer Details                            |");
//        System.out.println("| 2. View all products                                 |");
//        System.out.println("| 3. Show Report                                       |"); // Added option to show report
//        System.out.println("| 4. View all reviews                                  |");
//        System.out.println("| 5. Open full earning history in external window      |");
//        System.out.println("| 6. Update Expiration Durations                       |");
//        System.out.println("| 7. Logout                                            |");
//        System.out.println("--------------------------------------------------------");
//        System.out.print("Enter your choice: ");
//
//    }
    public static void printAdminReportMenu() {
        System.out.println("\n-----------------------------------------");
        System.out.println("|              Report Menu              |");
        System.out.println("-----------------------------------------");
        System.out.println("| 1. View the most popular gift redeem  |");
        System.out.println("| 2. View the least gift redeem         |");
        System.out.println("| 3. User profile status                |");
        System.out.println("-----------------------------------------");
        System.out.print("Enter your choice: ");
    }

    public static void printAdminMainMenu() {
        System.out.println("\n Admin Main Menu \n");
        System.out.println("0 : Exit");
        System.out.println("1. Check Customer Details");
        System.out.println("2. View all products");
        System.out.println("3. Check Earning file");
        System.out.println("4. Top redeemed Item from customer");
        System.out.println("5. Least redeemed Item from Customer");
        System.out.println("6. User Activity Checking");
        System.out.println("7. Update TierMultiplier");
        System.out.println("8. Update Expiration Duration");
        System.out.print("  Enter your choice: ");
    }

    public static void printStartMenu() {
        System.out.println("------------------------------");
        System.out.println("|         Start Menu         |");
        System.out.println("------------------------------");
        System.out.println("| 1. Create User Account     |");
        System.out.println("| 2. Login                   |");
        System.out.println("| 3. Forgot Password         |");
        System.out.println("| 4. Exit                    |");
        System.out.println("------------------------------");
        System.out.print("Enter your choice: ");
    }

    public static void printRegisterMenu() {
        System.out.println("------------------------------");
        System.out.println("|       Register Menu        |");
        System.out.println("------------------------------");
        System.out.println("| 1. Customer Register       |");
        System.out.println("| 2. Admin Register          |");
        System.out.println("| 3. Exit                    |");
        System.out.println("------------------------------");
        System.out.print("Enter your choice: ");
    }

    public static void printLoginMenu() {
        System.out.println("------------------------------");
        System.out.println("|         Login Menu         |");
        System.out.println("------------------------------");
        System.out.println("| 1. Customer Login          |");
        System.out.println("| 2. Admin Login             |");
        System.out.println("| 3. Exit                    |");
        System.out.println("------------------------------");
        System.out.print("Enter your choice: ");
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

    public static void main(String[] args) throws FileNotFoundException {

        AccountOperations userAccount; // Use the interface type
        userAccount = new UserAccount();
        //userAccount.displayMenu();

        RedemptionItem[] redemptionItems = {new Product("Umbrella", 2000, "Calvin Klein"), new Product("Shampoo", 200, "Shokutbutsu"), new Product("Toothpaste", 250, "Colgate"), new Voucher("Year end sale voucher", 100, 50), new Voucher("Gold voucher", 500, 85)};
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        boolean validate = false;
        Loyalty loyalty = new Loyalty();
        Policy policy = new Policy();
        String expiryMonths;
        String startMenuChoice = "";
        String registerMenuChoice = "";
        String loginMenuChoice = "";
        String customerMainMenuChoice = "";
        String adminMainMenuCHoice = "";
        LoginManager loginManager = new LoginManager();
        AdminDashBoard adminDashBoard = new AdminDashBoard();
        RegistrationManager registrationManager = new RegistrationManager();

        String memberNo = userAccount.getMemberNo();

        //System.out.println("MemberNo: " + memberNo); //Checking purpose, remove before submit
        while (isRunning) {
            startMenuChoice = "";
            while (!startMenuChoice.equals("0")) {
                printStartMenu();
                startMenuChoice = scanner.nextLine();
                switch (startMenuChoice) {
                    case "1":
                        registerMenuChoice = "";
                        while (!registerMenuChoice.equals("3") && !registerMenuChoice.equals("exit")) {
                            printRegisterMenu();
                            registerMenuChoice = scanner.nextLine();
                            switch (registerMenuChoice) {
                                case "1":
                                    userAccount.createAccount();
                                    registerMenuChoice = "exit";
                                    break;
                                case "2":
                                    registrationManager.createAccountStaff();
                                    registerMenuChoice = "exit";
                                    //continue handling
                                    break;
                                case "3":
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                            }

                        }

                        break;
                    case "2":
                        loginMenuChoice = "";
                        while (!loginMenuChoice.equals("3") && !loginMenuChoice.equals("exit")) {
                            System.err.println("test in loginmenu");
                            printLoginMenu();
                            loginMenuChoice = scanner.nextLine();
                            switch (loginMenuChoice) {
                                case "1":
                                    if (userAccount.login()) {
                                        memberNo = userAccount.getMemberNo();
                                        System.out.println("MemberNo in login : " + memberNo);
                                        customerMainMenuChoice = "";
                                        while (!customerMainMenuChoice.equals("0") && !customerMainMenuChoice.equals("exit")) {
                                            printCustomerMainMenu();
                                            customerMainMenuChoice = scanner.nextLine();
                                            switch (customerMainMenuChoice) {
                                                case "0":
                                                    break;
                                                case "1":
                                                    System.out.print("Enter your invoice NO.: ");
                                                    String invoiceNo = scanner.nextLine();
                                                    System.out.print("Enter the total payment amount: ");
                                                    int value = (int) (Math.round(Double.parseDouble(scanner.nextLine()) * POINTS_PER_RM) * loyalty.getMultiplier(memberNo));
                                                    new Earning(invoiceNo, value, memberNo);
                                                    policy.updateExpiryDate();
                                                    System.out.println("You have earned a total of " + value + " points!");
                                                    System.out.print("\n");
                                                    break;
                                                case "2":
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
                                                    userAccount.viewProfile();
                                                    break;
                                                case "4":
                                                    userAccount.showReferees();
                                                    break;

                                                default:
                                                    System.out.println("Invalid input!");
                                            }
                                        }

                                    }
                                    break;
                                case "2":
                                    if (loginManager.loginStaff()) {
                                        adminMainMenuCHoice = "";
                                        while (!adminMainMenuCHoice.equals("0") && !adminMainMenuCHoice.equals("exit")) {
                                            printAdminMainMenu();
                                            adminMainMenuCHoice = scanner.nextLine();
                                            switch (adminMainMenuCHoice) {
                                                case "0":
                                                    break;
                                                case "1":
                                                    adminDashBoard.checkCustomerDetails();
                                                    break;
                                                case "2":
                                                    adminDashBoard.viewAllProducts();
                                                    break;
                                                case "3":
                                                    adminDashBoard.checkEarningFile();
                                                    break;
                                                case "4":
                                                    adminDashBoard.displayTopRedeemedItem();
                                                    break;
                                                case "5":
                                                    adminDashBoard.displayLowRedeemedItem();
                                                    break;
                                                case "6":
                                                    adminDashBoard.ActivityTracking();
                                                    break;
                                                case "7":
                                                    loyalty.printTierMultipliers();
                                                    scanner.nextLine();
                                                    System.out.print("Enter the tier you want to modify: ");
                                                    String tierChoice = scanner.nextLine();
                                                    ;
                                                    System.out.print("Enter the new multiplier: ");
                                                    double multiplier = scanner.nextDouble();

                                                    switch (tierChoice) {
                                                        case "1":
                                                            loyalty.updateMultiplier("Bronze", multiplier);
                                                            break;
                                                        case "2":
                                                            loyalty.updateMultiplier("Silver", multiplier);
                                                            break;
                                                        case "3":
                                                            loyalty.updateMultiplier("Gold", multiplier);
                                                            break;
                                                        case "4":
                                                            loyalty.updateMultiplier("Platinium", multiplier);
                                                            break;
                                                        default:
                                                            System.out.println("Error Selection");
                                                            break;
                                                    }
                                                    break;
                                                case "8":
                                                    validate = false;
                                                    while (!validate) {
                                                        scanner.nextLine();
                                                        System.out.println("Update the expiration durations in months (01-12)");
                                                        expiryMonths = scanner.nextLine();
                                                        if (policy.validateMonth(expiryMonths)) {
                                                            policy.setExpiryMonths(Integer.parseInt(expiryMonths));
                                                            System.out.println("New expiration durations : " + policy.getExpiryMonths());
                                                            validate = true;

                                                        } else {
                                                            System.err.println("Invalid");
                                                        }

                                                    }

                                                    break;
                                                default:
                                                    System.out.println("Invalid choice!");
                                                    break;
                                            }
                                        }
                                    }
                                    break;
                                case "3":
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                                //displayLoginMenu();
                            }

                        }

                        break;

                    case "3": {
//                        try {
//                            forgot();
//                        } catch (IOException ex) {
//                            Logger.getLogger(UserAccount.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                    }
                    break;

                    case "4":
                        System.exit(0);
                        System.out.println("ran");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                    //displayMenu();
                }

            }

//            try {
//                System.err.println("Test");//Remove before submit
//                scanner.nextLine();
//                printCustomerMainMenu();
//                String choice = scanner.nextLine();
//                switch (choice) {
//                    case "1":
//                        System.out.print("Enter your invoice NO.: ");
//                        String invoiceNo = scanner.nextLine();
//                        System.out.print("Enter the total payment amount: ");
//                        int value = (int) (Math.round(Double.parseDouble(scanner.nextLine()) * POINTS_PER_RM) * loyalty.getMultiplier(memberNo));
//                        new Earning(invoiceNo, value, memberNo);
//                        policy.updateExpiryDate();
//                        System.out.println("You have earned a total of " + value + " points!");
//                        System.out.print("\n");
//                        break;
//
//                    case "2":
//                        List<Earning> earnings = CSVWrite.getAllEarnings();
//
//                        List<Earning> filteredList = new ArrayList<>();
//                        List<Earning> otherMembers = new ArrayList<>();
//
//                        for (Earning earn : earnings) {
//
//                            if (earn.getMemberNo().equals(memberNo)) {
//
//                                filteredList.add(earn);
//                            } else {
//                                otherMembers.add(earn);
//                            }
//                        }
//                        Collections.sort(filteredList, new EarningComparator());
//                        int totalPoints = 0;
//                        for (Earning e : filteredList) { // for each Player p in list         
//                            totalPoints += e.getValue();
//                        }
//                        System.out.print("Current points: " + totalPoints + '\n');
//
//                        System.out.println("What would you like to redeem?");
//
//                        int total = 0;
//                        for (int i = 0; i < redemptionItems.length; i++) {
//                            if (redemptionItems[i].getRedemptionValue() <= totalPoints) {
//                                total++;
//                                System.out.printf("%d %s (%d points)\n", i + 1, redemptionItems[i].getName(), redemptionItems[i].getRedemptionValue());
//                            }
//
//                        }
//
//                        if (total <= 0) {
//                            System.out.println("Sorry, you have insufficient points to redeem any item\n");
//                            break;
//                        }
//
//                        System.out.print("Enter your choice:");
//                        String redemptionChoice = scanner.nextLine();
//
//                        while (!isNumeric(redemptionChoice) || Integer.parseInt(redemptionChoice) < 1 || Integer.parseInt(redemptionChoice) > redemptionItems.length) {
//                            System.out.println("Invalid input!");
//                            System.out.print("Enter your choice:");
//                            redemptionChoice = scanner.nextLine();
//
//                        }
//
//                         {
//                            try {
//                                // write to txt file
//                                writeToRedemptionHistory(redemptionItems[Integer.parseInt(redemptionChoice) - 1], memberNo);
//                            } catch (IOException ex) {
//                                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
//
//                        deductPoints(redemptionItems[Integer.parseInt(redemptionChoice) - 1].getRedemptionValue(), filteredList);
//
////                        for (Earning test : filteredList) {
////                            System.out.println(test.getValue());
////                        }
//                        List<Earning> newEarnings = new ArrayList<>();
//                        newEarnings.addAll(filteredList);
//                        newEarnings.addAll(otherMembers);
//                        Earning.rewriteToFile(newEarnings);
//                        System.out.println("Redemption successful!\n");
//                        break;
//
//                    case "3":
//                        //viewprofile from useraccount
//                        userAccount.viewProfile();
//                        break;
//                    case "4":
//                        userAccount.showReferees();
//                        break;
//                    case "0":
//                        isRunning = false;
//                        break;
//                    default:
//                        System.out.println("Invalid input!");
//
//                }
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }

    }

}

class EarningComparator implements java.util.Comparator<Earning> {

    @Override
    public int compare(Earning a, Earning b) {
        return a.getEarningDate().isBefore(b.getEarningDate()) ? -1 : 1;
    }
}

//                        // Code to display the report
//                        printAdminReportMenu();
//                        String reportChoice = scanner.nextLine();
//                        while (!isNumeric(reportChoice) || Integer.parseInt(reportChoice) < 1 || Integer.parseInt(reportChoice) > 3) {
//                            System.out.println("Invalid input!");
//                            System.out.print("Enter your choice: ");
//                            reportChoice = scanner.nextLine();
//                        }
//                        Reporting reporting = new Reporting();
//
//                        switch (reportChoice) {
//                            case "1":
//                                //to view the most popular gift redeem
//                                reporting.displayTopRedeemedItem();
//                                break;
//                            case "2":
//                                //to view the least gift redeem
//                                reporting.displayLowRedeemedItem();
//                                break;
//                            case "3":
//                                //to view user profile status
//                                reporting.userProfileStatus();
//                                break;
//                        }
//                        System.out.println(" ");
//                        break;
//                    case "5":
//                        
//                    case "6":
//                        try {
//                            open(new File("earning.csv"));
//                            System.out.println(" ");
//
//                        } catch (IOException ex) {
//                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        break;
//                    case "7":
//                        validate = false;
//                        while (!validate) {
//                            System.out.print("Update the expiration durations in months (01-12):");
//                            expiryMonths = scanner.nextLine();
//                            // System.out.println(policy.validateMonth(expiryMonths));
//                            if (policy.validateMonth(expiryMonths)) {
//                                System.out.println("Updated!Current earning points have " + expiryMonths + " months expiry.");
//                                policy.setExpiryMonths(Integer.parseInt(expiryMonths));
//                                //System.out.println(policy.getExpiryMonths());
//                                validate = true;
//
//                            } else {
//                                System.err.println("Invalid input!");
//                            }
//                        }
//                        break;
//Admin Main menu 
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
//                getCurrentValueFromCSV();
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
//Print Start Menu 
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
//Print login menu
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
