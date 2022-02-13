public class PremiumClient extends Client {

    private static final double BONUS_RATE=0.001;
    private double bonus;

    public PremiumClient(String name) {
        super(name);
        bonus = 0;
    }

    public double getEarnedBonus(){
	return this.bonus;
    }

    @Override
    public void deposit(int amount) {
        super.deposit(amount);
        bonus += BONUS_RATE * amount;
    }
}
