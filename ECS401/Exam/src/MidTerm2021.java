import java.util.Scanner;

public class MidTerm2021 {

    public static void main(String[] args) {
        short[] studentAmounts = {0, 0, 0};
        Scanner scanner = new Scanner(System.in);
        studentAmounts[getStudentSubject((short) 1, scanner)] += 1;
        for (short i = 2; i <= 4; i++) {
            System.out.println("Next student:");
            studentAmounts[getStudentSubject(i, scanner)] += 1;
        }
        System.out.println();
        System.out.println(getPercentage(studentAmounts[1]) + "% of students are studying Computer Science");
        System.out.println(getPercentage(studentAmounts[0]) + "% of students are studying Robotics");
        System.out.println(getPercentage(studentAmounts[2]) + "% of students are studying Electronic Engineering");
    }

    public static byte getStudentSubject(short id, Scanner scanner) {
        System.out.printf("Student %d: what subject are you studying? ", id);
        String input = scanner.nextLine();
        byte ret;
        if (input.equals("Robotics")) ret = 0;
        else if (input.equals("Computer Science")) ret = 1;
        else ret = 2;
        System.out.println("Your lecture is at " + (ret+1) + "pm");
        return ret;
    }

    public static float getPercentage(short amount) {
        float percentage = amount / 7f;
        return (int)(percentage * 10) / 10f;
    }
}
