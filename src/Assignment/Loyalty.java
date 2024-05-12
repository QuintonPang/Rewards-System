package Assignment;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Loyalty {

    private Map<String, Double> tierMultipliers; // Mapping between tier and multiplier
    private String nextTiers;

    public Loyalty() {
        //initialize the tierMultiplier
        tierMultipliers = new HashMap<>();
        tierMultipliers.put("Bronze", 1.0);
        tierMultipliers.put("Silver", 1.25);
        tierMultipliers.put("Gold", 1.5);
        tierMultipliers.put("Platinium", 2.0);
    }

    public void updateMultiplier(String tier, double multiplier) {
        tierMultipliers.put(tier, multiplier);
    }

    //display to admin to modify
    public void printTierMultipliers() {

        int counter = 1;
        System.out.println("---------------------------------");
        System.out.println("|     Tier Multiplier Menu      |");
        System.out.println("---------------------------------");
        for (Map.Entry<String, Double> entry : tierMultipliers.entrySet()) {
            System.out.println("| " + counter + ". " + entry.getKey() + "\t: " + entry.getValue() + "\t\t|");
            counter++;
        }
        System.out.println("---------------------------------");
    }

    //return grade level
    public String determineGrade(int points) {
        if (points >= 3000) {
            return "Platinium";
        } else if (points >= 2000) {
            return "Gold";
        } else if (points >= 1000) {
            return "Silver";
        } else {
            return "Bronze";
        }
    }

    public String getCustomerGrade(String customerId) throws FileNotFoundException {
        int totalPoints = calculateAccumulatedPoint(customerId);
        return determineGrade(totalPoints); // Default grade is Bronze
    }

    public int calculateAccumulatedPoint(String customerId) throws FileNotFoundException {
        List<Earning> earnings = CSVWrite.getAllEarnings();
        int totalPoints = 0;
        for (Earning earn : earnings) {

            if (earn.getMemberNo().equals(customerId)) {

                totalPoints += earn.getOriValue();
            }
        }
        return totalPoints;
    }

    public double getMultiplier(String customerId) throws FileNotFoundException {
        return tierMultipliers.get(getCustomerGrade(customerId));
    }

    //toString Customer Grade Details
    public String toString(String customerId) throws FileNotFoundException {
        String grade = getCustomerGrade(customerId);
        int accumulatedPoint = calculateAccumulatedPoint(customerId);
        if (accumulatedPoint >= 3000) {
            nextTiers = "No more tiers";
        } else if (accumulatedPoint >= 2000) {
            nextTiers = String.valueOf(accumulatedPoint) + "/3000" + " Platinium";
        } else if (accumulatedPoint >= 1000) {
            nextTiers = String.valueOf(accumulatedPoint) + "/2000" + " Gold";
        } else {
            nextTiers = String.valueOf(accumulatedPoint) + "/2000" + " Sliver";
        }

        return "Current Grade : " + grade + "(Earning Points x " + String.valueOf(getMultiplier(customerId)) + ")"
                + "\nNext Tier : " + nextTiers;
    }

}
