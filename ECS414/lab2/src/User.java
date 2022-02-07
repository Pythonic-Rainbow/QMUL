public class User{

    private String name;
    private int accountAmount = 0;
    private final Account[] accounts = new Account[3];

    public User(String name){
	    this.name = name;
        for (int i = 0; i < accounts.length; i++) accounts[i] = new Account();
    }
    
    public void setName(String name){
	this.name = name;
    }

    public String getName(){
	return this.name;
    }

    public void addAccount(Account a) {
        if (accountAmount != 3) {
            accounts[accountAmount] = a;
            accountAmount++;
        } else {
            System.err.println("You can only have 3 accounts!");
        }
    }

    public int getFunds() {
        int fund = 0;
        for (Account a : accounts) {
            fund += a.getBalance();
        }
        return fund;
    }

    public void deposit(int account, int amount) {
        if (0 <= account && account < accountAmount) {
            accounts[account].deposit(amount);
        } else {
            System.err.println("Invalid account!");
        }
    }

    public void withdraw(int account, int amount) {
        if (0 <= account && account < accountAmount) {
            accounts[account].withdraw(amount);
        } else {
            System.err.println("Invalid account!");
        }
    }

    public String generateReport() {
        return String.format("Name: %s. Funds: %d", this.name, this.getFunds());
    }

    public double calculateEarnings() {
        double earnings = 0;
        for (Account a : accounts) {
            earnings += a.getBalance() * a.getInterestRate();
        }
        return earnings;
    }
}
