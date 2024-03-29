public class Client extends User{

    private Account account;

    public Client(String name) {
        super(name);
        this.account = new Account();
    }

    public Account getAccount(){
	return this.account;
    }

    public void deposit(int amount){
	this.account.deposit(amount);
    }

    public int getBalance(){
	return this.account.getBalance();
    }    
}
