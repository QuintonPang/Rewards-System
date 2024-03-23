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

    static void deductPoints(int total, List<Earning>filteredList) {
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.println("Welcome to our rewards system\n");

                System.out.println("0. Exit:");

                System.out.println("1. Earn rewards:");
                System.out.println("2. Redeem rewards:");
                System.out.print("Enter your choice:");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        System.out.print("Enter your member ID: ");
                        String memberID = scanner.nextLine();
                        System.out.print("Enter your invoice NO.: ");
                        String invoiceNo = scanner.nextLine();
                        System.out.print("Enter the total payment amount: ");
                        int value = (int) Math.round(scanner.nextDouble() * 10);
                        Earning earning = new Earning(invoiceNo, value, memberID);
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
                        System.out.println("1. Mug (150 points)");
                        System.out.println("2. Umbrella (300 points)");
                        System.out.println("3. Skipping Rope (500 points)");
                        System.out.print("Enter your choice:");
                        String redemptionChoice = scanner.nextLine();
                        switch (redemptionChoice) {
                            case "1":
                                deductPoints(150,filteredList);

                                break;
                        }
                        for (Earning test : filteredList) {
                            System.out.println(test.getValue());
                        }
                        System.out.println("Redemption successful!");
                        break;
                    case "3":
                        isRunning = false;
                        break;
                    default:
                        System.out.println("Invalid input!1");

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
