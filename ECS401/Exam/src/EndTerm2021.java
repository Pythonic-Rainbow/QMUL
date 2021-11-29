import java.util.Random;
import java.util.Scanner;

public class EndTerm2021 {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        int target = inputInt("Target score? ", scanner);
        int[] deck = new int[10];
        for (int i = 0; i < 10; i++) {
            deck[i] = 4;
        }
        gameplay(deck, target);
    }

    public static void gameplay(int[] deck, int target) {
        int i = 0;
        int r = roll(deck);
        deck[r] -= 1;
        System.out.println("I drew a " + r);
        while (i != target) {
            System.out.printf("Round %d: Higher or lower? (h/l) ", i);
            String c = new Scanner(System.in).nextLine();
            int rn = roll(deck);
            deck[rn] -= 1;
            System.out.println("I drew a " + rn);
            if ((c.equals("h") && rn < r) || (c.equals("l") && rn > r) ) {
                System.out.println("Nice try, you scored " + i);
                return;
            }
            i++;
            r = rn;
        }
        System.out.println("You win!");
    }

    public static int inputInt(String prompt, Scanner scanner) {
        System.out.print(prompt);
        return scanner.nextInt();
    }

    /*	Return	a	random	int	between	0	(inclusive)	and	bound	(exclusive)	*/
    public static int randomInt(int	bound) {
        Random r = new Random();
        return r.nextInt(bound);
    }

    public static int roll(int[] deck) {
        int r = 0, i = 0;
        while (r == 0) {
            i = randomInt(10);
            r = deck[i];
        }
        return i;
    }
}
