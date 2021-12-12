import java.util.Random;
import java.util.Scanner;

/**
 * @author Howard Wong
 * Date: 10-11-2021
 * @version 1.0
 * A board game with traps and secret passages
 */
public class SecretTrapsAndPassages {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final Random random = new Random();

        int position = rollDice(random);
        System.out.println("Your current position is: " + position);
        char input = getMCInput("What is 1+1?", new String[]{"1", "2", "3", "4"}, scanner);
        printInput(input);
        printAnswer('b');
    }

    public static char getMCInput(String q, String[] choices, Scanner s) {
        System.out.println(q + "\n");
        for (int i = 0; i <= 3; i++) System.out.printf("%c. %s%n", (char)(i + 97), choices[i]);
        return s.nextLine().charAt(0);
    }

    public static void printInput(char input) {
        System.out.println("You answered " + input);
    }

    public static void printAnswer(char answer) {
        System.out.println("The correct answer is " + answer);
    }

    public static int rollDice(Random r) {
        return r.nextInt(6) + 1;
    }
}
