/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;


public abstract class Account  {

  private String username;
  private String email;
  private String phone;
  private String password;
  private String membershipNumber;
  private String transactionRecord;
  private User referrer;

  public Account(String username, String email, String phone, String password, String membershipNumber, String transactionRecord, User referrer) {
      this.username = username;
      this.email = email;
      this.phone = phone;
      this.password = password;
      this.membershipNumber = membershipNumber;
      this.referrer = referrer;
  }
  
  public Account(){
      
  }

  public User getReferrer() {
      return referrer;
  }

  public void setReferrer(User referrer) {
      this.referrer = referrer;
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

  public String getMembershipNumber() {
      return membershipNumber;
  }

  public void setMembershipNumber(String membershipNumber) {
      this.membershipNumber = membershipNumber;
  }

  public String getTransactionRecord() {
      return transactionRecord;
  }

  public void setTransactionRecord(String transactionRecord) {
      this.transactionRecord = transactionRecord;
  }

    public abstract void displayMenu();
  }

  
  
