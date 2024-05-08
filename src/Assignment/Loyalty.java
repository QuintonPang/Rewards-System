package Assignment;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Loyalty {

    private Map<String, Double> tierMultipliers; // Mapping between tier and multiplier

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
        for (Map.Entry<String, Double> entry : tierMultipliers.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
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
        List<Earning> earnings = CSVWrite.getAllEarnings();
        int totalPoints = 0;
        for (Earning earn : earnings) {

            if (earn.getMemberNo().equals(customerId)) {

                totalPoints += earn.getOriValue();
            }
        }
        return determineGrade(totalPoints); // Default grade is Bronze
    }

    public double getMultiplier(String customerId) throws FileNotFoundException {
        return tierMultipliers.get(getCustomerGrade(customerId));
    }

    //toString Customer Grade Details
    public String toString(String customerId) throws FileNotFoundException {
        String grade = getCustomerGrade(customerId);
        return "Dear Customer " + customerId + ", your grade is: " + grade;
    }

    public static void main(String[] args) throws FileNotFoundException {

        Loyalty loyalty = new Loyalty();

        String memberNo = "m1001";
        System.out.println(loyalty.toString(memberNo));
        System.out.println(loyalty.getMultiplier(memberNo));

    }

}
