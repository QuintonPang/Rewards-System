package Assignment;

import java.util.HashMap;
import java.util.Map;

public class Loyalty {
    private Map<String, Integer> accumulatedPoints; // Mapping between customer ID and accumulated points
    private Map<String, String> customerGrades; // Mapping between customer ID and grade
    private Map<String, Double> tierMultipliers; // Mapping between tier and multiplier


    public Loyalty() {
        this.accumulatedPoints = new HashMap<>();
        this.customerGrades = new HashMap<>();
        this.tierMultipliers = new HashMap<>();
        // Initialize accumulated points and customer grades
    }
    //initialize the Multiplier Rate , can be modify by admin
    public void initializeMultiplier(){
        tierMultipliers.put("Bronze", 1.0);
        tierMultipliers.put("Silver", 1.25);
        tierMultipliers.put("Gold", 1.5);
        tierMultipliers.put("Platinium", 2.0);
    }

    // Do Every time when customer earn points
    public void trackActivity(String customerId, int pointsEarned) {
        accumulatedPoints.put(customerId, accumulatedPoints.getOrDefault(customerId, 0) + pointsEarned);
        upgradeCustomerGrade(customerId);
    }

    //check points and upgrade when reach threshold
    public void upgradeCustomerGrade(String customerId) {
        int points = accumulatedPoints.getOrDefault(customerId, 0);
        String grade = determineGrade(points);
        customerGrades.put(customerId, grade);
    }

    //return grade level
    public String determineGrade(int points) {
        if (points >= 3000){
            return "Platinium";
        }
        else if (points >= 2000) {
            return "Gold";
        } else if (points >= 1000) {
            return "Silver";
        } else {
            return "Bronze";
        }
    }

    public String getCustomerGrade(String customerId) {
        return customerGrades.getOrDefault(customerId, "Bronze"); // Default grade is Bronze
    }

    //toString Customer Grade Details
    public String toString(String customerId) {
        String grade = getCustomerGrade(customerId);
        return "Dear Customer " + customerId + ", your grade is: " + grade;
    }
    


}
