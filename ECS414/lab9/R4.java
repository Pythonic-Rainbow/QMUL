import java.util.ArrayList;
import java.util.NoSuchElementException;

public class R4 {
    private ArrayList<Account> accounts = new ArrayList<>();

    // Adds a new Account
    public void addAccount(Account a) {
        accounts.add(a);
    }

    // Returns the account with highest bal
    public Account getHighestBalanceAccount() {
        Account account = accounts.get(0);
        for (Account a : accounts)
            if (a.getBalance() > account.getBalance())
                account = a;
        return account;
    }

    // Returns the total balance of all accounts
    public int getTotalBalance() {
        int total = 0;
        for (Account a : accounts)
            total += a.getBalance();
        return total;
    }

    // Deposit to the lowest balance account and return it
    public Account depositLowestBalanceAccount(int value) {
        Account account = accounts.get(0);
        for (Account a : accounts)
            if (a.getBalance() < account.getBalance())
                account = a;
        account.deposit(value);
        return account;
    }

    // Returns account with ID
    public Account getAccount(int id) throws NoSuchElementException {
        for (Account a : accounts)
            if (a.getUniqueId() == id)
                return a;
        throw new NoSuchElementException("No account found with that ID.");
    }

    public void removeAccount(int id) throws NoSuchElementException {
        if (!accounts.removeIf(a -> a.getUniqueId() == id))
            throw new NoSuchElementException("No account found with that ID.");
    }
}
