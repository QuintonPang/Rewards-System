/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;


public class Account  {

  private String username;
  private String email;
  private String phone;
  private String password;

  public Account(String username, String email, String phone, String password) {
      this.username = username;
      this.email = email;
      this.phone = phone;
      this.password = password;
  }
  
  public Account(){
      
  }


  // Getters and setters
  public String getUsername() {
      return username;
  }

  public void setUsername(String username) {
      this.username = username;
  }

  public String getEmail() {
      return email;
  }

  public void setEmail(String email) {
      this.email = email;
  }

  public String getPhone() {
      return phone;
  }

  public void setPhone(String phone) {
      this.phone = phone;
  }

  public String getPassword() {
      return password;
  }

  public void setPassword(String password) {
      this.password = password;
  }
  
      public void printStartMenu() {
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
  
}




  
  