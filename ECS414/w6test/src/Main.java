public class Main {
    public static void main(String[] args) {
        Account acc1 = new Account();
        Account acc2 = acc1;
        acc2.deposit(100);
        System.out.println(acc1.getBalance());
    }
}
