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
        while (true) getMCInput("What is 1+1?", new String[]{"1", "2", "3", "4"}, (byte)1, scanner);
    }

    public static boolean getMCInput(String q, String[] choices, byte correct_index, Scanner s) {
        System.out.println(q + "\n");
        for (byte i = 0; i <= 3; i++) System.out.printf("%c. %s%n", (char)(i + 97), choices[i]);
        return s.nextLine().charAt(0) == (char)(correct_index + 97);
    }
}
