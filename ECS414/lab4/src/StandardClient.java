public class StandardClient extends Client implements Resettable{

    public StandardClient(String name){
	    super(name);
    }

    @Override
    public void deposit(int amount) {
        this.getAccount().deposit(amount);
    }

    @Override
    public void reset() {
        this.setAccount(new Account());
    }
}
