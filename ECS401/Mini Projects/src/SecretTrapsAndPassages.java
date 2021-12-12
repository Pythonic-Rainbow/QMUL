import java.util.Random;
import java.util.Scanner;

/**
 * @author Howard Wong
 * Date: 12-12-2021
 * @version 4.1
 * A board game with traps and secret passages
 */
public class SecretTrapsAndPassages {
    static class Question {
        private String msg;
        private String[] choices;
        private char answer;
    }

    // A boilerplate to create a new Question instance.
    public static Question newQuestion(String q, String[] choices, char ans) {
        Question question = new Question();
        question.msg = q;
        question.choices = choices;
        question.answer = ans;
        return question;
    }

    // Getter for msg of Question
    public static String getQuestionMsg(Question q) {return q.msg;}

    // Getter for choices of Question
    public static String[] getQuestionChocies(Question q) {return q.choices;}

    // Getter for answer of Question
    public static char getQuestionAnswer(Question q) {return q.answer;}

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final Random random = new Random();
        final Question question = newQuestion("What is 1+1?", new String[]{"1", "2", "3", "4"}, 'b');
        final int[] positions = new int[]{0, 0};

        for (int i = 0; i < positions.length; i++) {
            System.out.println("Current player: " + (i+1));
            int roll = rollDice(random);
            positions[i] += roll;
            System.out.printf("You rolled %d. Your current position is: %s%n", roll, positions[i]);

            // Actual gameplay
            boolean isCorrect = getMCInput(question, scanner);
            positions[i] = getNewPosition(positions[i], isCorrect);
            System.out.printf("Your new position is: %s. Press ENTER to confirm.%n", positions[i]);
            scanner.nextLine();  // Lets the player check the results before next player
            System.out.println("====================");
        }
    }

    // Asks question and returns whether the answer is correct
    public static boolean getMCInput(Question q, Scanner s) {
        System.out.println(getQuestionMsg(q) + "\n");
        for (int i = 0; i <= 3; i++) System.out.printf("%c. %s%n", (char)(i + 97), getQuestionChocies(q)[i]);
        char input = s.nextLine().charAt(0);
        System.out.println("You answered " + input);
        System.out.println("The correct answer is " + getQuestionAnswer(q));
        boolean correct = input == getQuestionAnswer(q);
        System.out.printf("Your answer is %s\n", correct ? "correct" : "wrong");
        return correct;
    }

    /**
     * Processes the position of the player
     * @param pos Current position to be processed
     * @param correct Whether the player has correctly answered the previous question
     * @return New position
     */
    public static int getNewPosition(int pos, boolean correct) {
        // Special board positions with traps, secret paths etc.
        final int[] specialPos = new int[]{3, 5};
        final String[] posInst = new String[]{"?4", "!4"};

        if (!correct) pos -= 1;  // Go back by 1 if wrong answer

        // Check whether current position is in the special positions array
        for (int i = 0; i < specialPos.length; i++) if (pos == specialPos[i]) {
            // Extracts the operand from the instruction corresponding to the special position
            String operand = posInst[i].substring(1);
            // The first char of the instruction is the actual command
            switch (posInst[i].charAt(0)) {
                case '?':  // If the answer is correct
                    if (correct)  {
                        System.out.println("You found a secret path! Jump forward by " + operand);
                        return pos + Integer.parseInt(operand);  // Increments position by operand
                    }
                    break;
                case '!':  // If the answer is wrong
                    if (!correct)  {
                        System.out.println("You fell into a trap! Jump backwards by " + operand);
                        return pos - Integer.parseInt(operand);  // Decrements position by operand
                    }
            }
        }
        return pos; // If current position is not special, just return
    }

    // returns an integer from 1 to 6 inclusive.
    public static int rollDice(Random r) {
        return r.nextInt(6) + 1;
    }
}
