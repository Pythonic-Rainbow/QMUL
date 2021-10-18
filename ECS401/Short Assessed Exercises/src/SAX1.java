/**
 * @author Howard Wong
 * DATE 12-10-2021
 * @version 1.0
 * Prints my initials
 */
public class SAX1 {

    // Prints everything
    public static void main(String[] args) {
        printHW();
    }

    // Prints the character H
    public static void printH() {
        System.out.println("H     H");
        System.out.println("H     H");
        System.out.println("HHHHHHH");
        System.out.println("H     H");
        System.out.println("H     H");
    }

    // Prints the character W
    public static void printW() {
        System.out.println("W     W");
        System.out.println(" W W W");
        System.out.println("  W W");
    }

    public static void printHW() {
        printH();
        System.out.println();
        printW();
    }
}