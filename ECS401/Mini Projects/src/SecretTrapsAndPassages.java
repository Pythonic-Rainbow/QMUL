import java.util.Random;
import java.util.Scanner;

/**
 * @author Howard Wong
 * Date: 12-12-2021
 * @version 7.0
 * A board game with traps and secret passages
 */


public class SecretTrapsAndPassages {
    static class Question {
        String msg;
        String[] choices;
        char answer;
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
    public static String[] getQuestionChoices(Question q) {return q.choices;}

    // Getter for answer of Question
    public static char getQuestionAnswer(Question q) {return q.answer;}

    // Sets up the String array for choices
    public static String[] newMC(String a, String b, String c, String d) {
        String[] mc = new String[4];
        mc[0] = a;
        mc[1] = b;
        mc[2] = c;
        mc[3] = d;
        return mc;
    }

    // Simulating how arrays are implemented through a stack (ADT)
    static class QuestionBank {
        static final Question ONE = newQuestion("What is 1+1?", newMC("1", "2", "3", "4"), 'b');
        static final Question TWO = newQuestion("What is the nuclear reaction type of the Sun?",
                newMC("Fusion", "Fission", "Friction", "Tension"), 'a');
        static final Question THREE = newQuestion("Where is Queen Mary, University of London located at?",
                newMC("Queen", "Mary", "University", "London"), 'd');
        static final Question FOUR = newQuestion("In Chinese, both '您' and '你' means 'you', but when should we use 您 over 你?",
                newMC("When that person has a heart", "When you respect that person",
                        "When we are praying", "When that person gets COVID-19"), 'b');
        static final Question FIVE = newQuestion("Who directed Star Wars Ep. 7 and 9?",
                newMC("Jar Jar Binks", "Luke Starkiller", "J. J. Abrams", "George Lucas"), 'c');
        static final Question SIX = newQuestion("How do you pronounce で(De)す(Su) in spoken Japanese?",
                newMC("De su", "BAN ZAI", "D esu", "Des"), 'd');
    }

    // Simulating how we access elements in a stack by traversing through it linearly until we found a match
    public static Question getRandomQuestion(Random r) {
        switch (rollDice(r)) {
            case 1:
                return QuestionBank.ONE;
            case 2:
                return QuestionBank.TWO;
            case 3:
                return QuestionBank.THREE;
            case 4:
                return QuestionBank.FOUR;
            case 5:
                return QuestionBank.FIVE;
            default:
                return QuestionBank.SIX;
        }
    }

    // Bubble sort
    public static int[] sortedPositions(int[] poss) {
        int temp;
        for (int j = 0; j < poss.length; j++) {
            for (int i = j; i < poss.length - 1; i++) {
                if (poss[i] < poss[i+1]) {
                    temp = poss[i];
                    poss[i] = poss[i+1];
                    poss[i+1] = temp;
                }
            }
        }
        return poss;
    }

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final Random random = new Random();
        // Special board positions with traps, secret paths etc.
        final int[] specialPos = new int[]{3, 5};
        final String[] posInst = new String[]{"?4", "!4"};

        System.out.print("Please enter amount of players: ");
        int playerCount = scanner.nextInt();
        scanner.nextLine();
        int[] positions = new int[playerCount];

        boolean won = false;
        while (!won) {
            for (int i = 0; i < positions.length; i++) {
                System.out.println("Current player: " + (i+1));
                int roll = rollDice(random);
                positions[i] += roll;
                System.out.printf("You rolled %d. Your current position is: %s%n", roll, positions[i]);

                // Actual gameplay
                boolean isCorrect = getMCInput(getRandomQuestion(random), scanner);
                positions[i] = getNewPosition(positions[i], isCorrect, specialPos, posInst);
                System.out.printf("Your new position is: %s. Press ENTER to confirm.%n", positions[i]);
                scanner.nextLine();  // Lets the player check the results before next player
                System.out.println("====================");
                if (!won) won = positions[i] >= 10;
            }
            System.out.print("Current leaderboard: ");
            for (int p : sortedPositions(positions)) System.out.print(p + " ");
            System.out.println();
            System.out.println("Press ENTER to confirm.");
            scanner.nextLine();
            System.out.println("====================");
        }
        // Current player's position is at least 10, he finished the game.
        System.out.println("You won!");
        scanner.nextLine();
    }

    // Asks question and returns whether the answer is correct
    public static boolean getMCInput(Question q, Scanner s) {
        System.out.println(getQuestionMsg(q) + "\n");
        for (int i = 0; i <= 3; i++) System.out.printf("%c. %s%n", (char)(i + 97), getQuestionChoices(q)[i]);
        char input = s.nextLine().charAt(0);
        System.out.println("You answered " + input);
        System.out.println("The correct answer is " + getQuestionAnswer(q));
        boolean correct = input == getQuestionAnswer(q);
        System.out.println("Your answer is " + (correct ? "correct" : "wrong"));
        return correct;
    }

    /**
     * Processes the position of the player
     * @param pos Current position to be processed
     * @param correct Whether the player has correctly answered the previous question
     * @param specialPos Special positions that need further processing
     * @param posInst Instructions for each special position
     * @return New position
     */
    public static int getNewPosition(int pos, boolean correct, int[] specialPos, String[] posInst) {
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
