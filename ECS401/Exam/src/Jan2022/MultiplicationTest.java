package Jan2022;

import java.util.Scanner;

public class MultiplicationTest {
    public static void main(String[] args) {
        int start, end, correct; // Start/end of x, amount of correct inputs
        start = end = correct = 0;

        // Sets start&end of x
        while (start <= 0) start = readInt("Enter x start (start>0): ");
        while (end < start) end = readInt("Enter x end (end>=start): ");

        for (int x = start; x <= end; x++) {
            for (int y = 1; y < 13; y++) {
                int input = readInt(String.format("What is %d * %d? ", x, y));
                if (input == x * y) {
                    correct++;
                    System.out.println("Correct.");
                } else {
                    System.out.println("Incorrect.");
                }
            }
        }

        // Printing the final result.
        int total = (end - start + 1) * 12;
        int percentage = Math.round(correct / (float)total * 100);
        char grade;
        if (percentage >= 70) grade = 'A';
        else if (percentage >= 60) grade = 'B';
        else if (percentage >= 50) grade = 'C';
        else if (percentage >= 40) grade = 'D';
        else grade = 'F';
        System.out.printf("You scored %d/%d (%d%%) %c grade.", correct, total, percentage, grade);
    }

    public static int readInt(String prompt) {
        Scanner s = new Scanner(System.in);
        System.out.print(prompt);
        return s.nextInt();
    }
}
