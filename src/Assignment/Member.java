package Assignment;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Member extends Account {

    private String memberNo;
    private LoginManager loginManager = new LoginManager();
    private RegistrationManager registrationManager = new RegistrationManager();
    private MemberDashBoard memberDashBoard = new MemberDashBoard();

    public Member(String username, String email, String phone, String password, String memberNo) {
        super(username, email, phone, password);
        this.memberNo = memberNo;
    }

    public Member() {
    }

    public boolean login() {
        MemberInfo memberInfo = loginManager.loginMember();
        if (memberInfo != null) {
            // Set the attributes of Admin class using data from AdminInfo
            setUsername(memberInfo.getUsername());
            setEmail(memberInfo.getEmail());
            setPhone(memberInfo.getPhone());
            setPassword(memberInfo.getPassword());
            // Set adminAccountNo
            this.memberNo = memberInfo.getMemberNo();
            return true;
        } else {
            return false;
        }

    }
    
    public void printMemberMainMenu(){
        memberDashBoard.printMemberMainMenu();
    }
    
    public void showReferees(){
        memberDashBoard.showReferees();
    }
    
    public void viewProfile() throws FileNotFoundException{
        memberDashBoard.viewProfile(memberNo);
    }
    
    public void forgot() throws IOException{
        memberDashBoard.forgot();
    }
    
    public void createAccount(){
        registrationManager.createAccountUser();
    }

    public String getMemberNo() {
        return memberNo;
    }
    
    

    public static void main(String[] args) {
        Member member = new Member();
        member.login();
        member.printMemberMainMenu();
        System.out.println(member.getUsername());
    }
    

}


