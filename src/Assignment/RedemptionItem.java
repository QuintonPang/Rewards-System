/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

/**
 *
 * @author quinton
 */
public class RedemptionItem {

    private String name;
    private int redemptionValue;

    public RedemptionItem() {
    }

    public RedemptionItem(String name, int redemptionValue) {
        this.name = name;
        this.redemptionValue = redemptionValue;
    }

    public String getName() {
        return name;
    }

    public int getRedemptionValue() {
        return redemptionValue;
    }
}
