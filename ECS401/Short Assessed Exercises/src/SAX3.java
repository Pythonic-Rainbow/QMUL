import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Howard Wong
 * Date: 18-10-2021
 * @version 1.0
 * Finds the parking fee for the users.
 */
public class SAX3 {

    public static void main(String[] args) {
        System.out.println("Parking for you is " + getParkingFee(new Scanner(System.in)));
    }

    /**
     * Asks the prompt message and loops until the user either inputs a yes or no.
     * @param prompt The prompt message
     * @return true if user inputs yes; false if user inputs no
     */
    public static boolean ask(final Scanner reader, String prompt) {
        while (true) {
            System.out.print(prompt + " ");
            String input = reader.nextLine().toLowerCase();
            if (input.equals("yes")) return true;
            else if (input.equals("no")) return false;
            else System.out.println("I am not sure about what you meant. Please input yes or no.");
        }
    }

    /**
     * Asks the user for the hours to park and ensures that it is within 1-8
     * @return hours to park
     */
    public static byte askParkingHours(final Scanner reader) {
        while (true) {
            System.out.print("How many hours do you wish to park (1-8)? ");
            try {
                byte hours = reader.nextByte();
                if (1 <= hours & hours <= 8) return hours;
                else System.out.println("Invalid input!");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input!");
                reader.nextLine();
            }
        }
    }

    /**
     * Computes the parking fee to be printed based on whether the users are disabled, are local or are old persons.
     * @return the parking fee as a String.
     */
    public static String getParkingFee(final Scanner reader) {
        if (ask(reader, "Are you disabled?")) return "free";
        byte parkingFee = askParkingHours(reader);
        float cost;
        if (parkingFee == 1) cost = 3;
        else if (2 <= parkingFee & parkingFee <= 4) cost = 4;
        else if (5 <= parkingFee & parkingFee <= 6) cost = 4.5f;
        else cost = 5.5f;
        return cost - getDiscount(reader) + " pounds.";
    }

    /**
     * Asks users whether they are local/old person
     * @return the discount that the user can get.
     */
    public static byte getDiscount(final Scanner reader) {
        byte discount = ask(reader, "Do you have an ”I live locally badge”?") ? (byte)1 : 0;
        if (ask(reader, "Are you an OAP?")) discount += 2;
        return discount;
    }

}
