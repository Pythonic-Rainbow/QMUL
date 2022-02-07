public class Account{
    private int balance;
    private final int id;
    static int idBase = 0;
    private double interestRate;


    public Account(int balance, double interestRate) {
        this.balance = balance;
        this.interestRate = interestRate;
        ++idBase;
        this.id = idBase;
    }

    public Account(int balance) {
        this(balance, 0.01);
    }

    public Account() {
        this(0, 0.01);
    }

    public int getUniqueId() { return this.id;}

    public int getBalance(){
        return this.balance;
    }

    public double getInterestRate() { return this.interestRate; }

    public void setBalance(int amount){
        this.balance = amount;
    }

    public void deposit(int amount){
        this.balance += amount;
    }
    
    public void withdraw(int amount){
	if (amount > this.balance){
	    System.out.println("Error. The amount to be withdrawn exceeds this account's balance.");
	} else{
	    this.balance -= amount;
	}
    }    
}
