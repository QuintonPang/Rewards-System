/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

/**
 *
 * @author quinton
 */
public class Product extends RedemptionItem{
   
    private String brand;
    
    public Product() {
    }

    
    public Product(String name, int redemptionValue, String brand) {
        super(name,redemptionValue);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    
    
}
