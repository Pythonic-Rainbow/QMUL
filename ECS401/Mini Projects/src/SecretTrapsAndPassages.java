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
        final char answer = 'b';

        int position = rollDice(random);
        System.out.println("Your current position is: " + position);
        char input = getMCInput("What is 1+1?", new String[]{"1", "2", "3", "4"}, scanner);
        printInput(input);
        printAnswer(answer);

        boolean isCorrect = input == answer;
        position = isCorrect ? position : position - 1;
        printIsCorrect(isCorrect);
    }

    // Asks question and returns user choice
    public static char getMCInput(String q, String[] choices, Scanner s) {
        System.out.println(q + "\n");
        for (int i = 0; i <= 3; i++) System.out.printf("%c. %s%n", (char)(i + 97), choices[i]);
        return s.nextLine().charAt(0);
    }

    // prints input from user
    public static void printInput(char input) {
        System.out.println("You answered " + input);
    }

    // prints correct answer
    public static void printAnswer(char answer) {
        System.out.println("The correct answer is " + answer);
    }

    // prints whether the user has inputted correct answer
    public static void printIsCorrect(boolean correct) {
        System.out.printf("Your answer is %s\n", correct ? "correct" : "wrong");
    }

    // returns an integer from 1 to 6 inclusive.
    public static int rollDice(Random r) {
        return r.nextInt(6) + 1;
    }
}
