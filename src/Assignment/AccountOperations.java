/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

import java.io.IOException;


public interface AccountOperations {
    void displayMenu();
    void createAccount();
    boolean login();
    void showReferees();
    void viewProfile();
    String getMemberNo();
    void forgot() throws IOException;
    
}