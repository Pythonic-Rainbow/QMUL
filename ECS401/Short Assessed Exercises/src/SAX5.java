import java.util.Scanner;

/**
 * @author Howard Wong
 * Date: 13-12-2021
 * @version 1.1
 * Find the average and all information of olympians
 */
public class SAX5 {
    static class Olympian {
        String name;
        byte medals;
        String category;
    }

    public static void main(String[] args) {
        final byte arraySize = 5;
        final Scanner scanner = new Scanner(System.in);
        askOlympians(arraySize, scanner);
    }

    public static Olympian newOlympian(String name, byte medals, String cat) {
        Olympian o = new Olympian();
        o.name = name;
        o.medals = medals;
        o.category = cat;
        return o;
    }

    // Obtains all the info of all olympian from the user
    public static void askOlympians(byte size, Scanner s) {
        Olympian[] olympians = new Olympian[size];
        float sum = 0;
        for (byte i = 0; i < size; i++) {
            System.out.printf("Name Olympians/Paralympian %d? ", i + 1);
            String name = s.nextLine();
            System.out.print("How many medals did he/she win? ");
            byte medals = s.nextByte();
            sum += medals;
            s.nextLine();
            System.out.print("What sport did he/she compete in? ");
            String category = s.nextLine();
            System.out.println();
            olympians[i] = newOlympian(name, medals, category);
        }
        printOlympiansInfo(olympians, sum);
    }

    // Prints all info for all olympians
    public static void printOlympiansInfo(Olympian[] olympians, float sum) {
        System.out.printf("Between them they won an average of %d medals each.%n", Math.round(sum / olympians.length));
        for (Olympian o : olympians) System.out.printf("%s, %s, %d%n", o.category, o.name, o.medals);
    }
}
