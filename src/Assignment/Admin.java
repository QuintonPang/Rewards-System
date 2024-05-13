/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Assignment;

/**
 *
 * @author Kang
 */
public class Admin extends Account {

    private String adminNo;
    private AdminDashBoard adminDashBoard = new AdminDashBoard();
    private LoginManager loginManager = new LoginManager();
    private RegistrationManager registrationManager = new RegistrationManager();

    public Admin(String username, String email, String phone, String password, String adminNo) {
        super(username, email, phone, password);
        this.adminNo = adminNo;
    }

    public Admin() {
    }
    

    public boolean login() {
        AdminInfo adminInfo = loginManager.loginStaff();
        if (adminInfo != null) {
            // Set the attributes of Admin class using data from AdminInfo
            setUsername(adminInfo.getUsername());
            setEmail(adminInfo.getEmail());
            setPhone(adminInfo.getPhone());
            setPassword(adminInfo.getPassword());
            // Set adminNo
            this.adminNo = adminInfo.getAdminNo();
            return true;
        }else{
            return false;
        }
        
    }
    
    public void createAccount(){
        registrationManager.createAccountStaff();
    }
    
    public void createAccountStaff(){
        registrationManager.createAccountStaff();
    }
    
    public void printAdminMainMenu(){
        adminDashBoard.printAdminMainMenu();
    }
    
    public void checkCustomerDetails(){
        adminDashBoard.checkCustomerDetails();
    }
    
    public void viewAllProducts(){
        adminDashBoard.viewAllProducts();
    }
    
    public void checkEarningFile(){
        adminDashBoard.checkEarningFile();
    }
    
    public void displayTopRedeemedItem(){
        adminDashBoard.displayTopRedeemedItem();
    }
    
    public void displayLowRedeemedItem(){
        adminDashBoard.displayLowRedeemedItem();
    }
    
    public void ActivityTracking(){
        adminDashBoard.ActivityTracking();
    }
    
    public String getAdminNo(){
        return loginManager.getStaffNo();
    }
    
    
    public static void main(String[] args) {
        Admin admin = new Admin();
        admin.printAdminMainMenu();
        admin.login();
        System.out.println(admin.getUsername());
    }
}
