public class TestAccount {
    public static void main(String[] args) {
        AccountOperations userAccount = new UserAccount(); // Use the interface type
        userAccount.displayMenu(); // Invoke methods through the interface
    }
}
