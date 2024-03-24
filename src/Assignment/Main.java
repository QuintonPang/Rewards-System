package Assignment;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import java.io.FileNotFoundException;
import java.io.IOException;
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

            }
        }
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RedemptionItem[] redemptionItems = {new Product("Umbrella", 2000, "Calvin Klein"), new Product("Shampoo", 200, "Shokutbutsu"), new Product("Toothpaste", 250, "Colgate"), new Voucher("Year end sale voucher", 100, 50), new Voucher("Gold voucher", 500, 85)};
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.println("Welcome to our rewards system\n");

                System.out.println("0. Exit");

                System.out.println("1. Earn rewards");
                System.out.println("2. Redeem rewards");
                System.out.print("Enter your choice: ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        System.out.print("Enter your member ID: ");
                        String memberID = scanner.nextLine();
                        System.out.print("Enter your invoice NO.: ");
                        String invoiceNo = scanner.nextLine();
                        System.out.print("Enter the total payment amount: ");
                        int value = (int) Math.round(Double.parseDouble(scanner.nextLine()) * 10);
                        new Earning(invoiceNo, value, memberID);
                        System.out.print("\n");
                        break;

                    case "2":
                        System.out.print("Enter your member No.:");
                        String memberNo = scanner.nextLine();

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
                        for (int i = 0; i < redemptionItems.length; i++) {
                            if (redemptionItems[i].getRedemptionValue() <= totalPoints) {
                                System.out.printf("%d %s (%d points)\n", i + 1, redemptionItems[i].getName(), redemptionItems[i].getRedemptionValue());
                            }

                        }

                        System.out.print("Enter your choice:");
                        String redemptionChoice = scanner.nextLine();

                        while (!isNumeric(redemptionChoice)||Integer.parseInt(redemptionChoice)<1||Integer.parseInt(redemptionChoice)>redemptionItems.length) {
                            System.out.println("Invalid input!");
                            System.out.print("Enter your choice:");
                            redemptionChoice = scanner.nextLine();

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

}

class EarningComparator implements java.util.Comparator<Earning> {

    @Override
    public int compare(Earning a, Earning b) {
        return a.getEarningDate().isBefore(b.getEarningDate()) ? -1 : 1;
    }
}
