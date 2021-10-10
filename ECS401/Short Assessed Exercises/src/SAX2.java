import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Howard Wong
 * Date: 10-10-2021
 */
public class SAX2 {

    public static void main(String[] args) {
        float length = getUserInputFloat("Enter length of the room (cm): ");
        float width = getUserInputFloat("Enter width of the room (cm): ");
        float height = getUserInputFloat("Enter height of the room (cm): ");
        float balloonVolume = getUserInputFloat("Enter balloon volume (m³): ");
        float roomVolume = length * width * height / 1000000;

        System.out.println("Your room volume is " + roomVolume + " m³.");
        System.out.println("You can have at most " + (int)(roomVolume / balloonVolume) + " balloons");
    }

    /**
     * Asks a query message and gets the user input in float
     * @param query The query message
     * @return User input as a float
     */
    public static float getUserInputFloat(String query) {
        boolean test = true;
        float input = 0;
        while (test) { // Loops until the user inputs an actual float
            try {
                System.out.print(query);
                input = new Scanner(System.in).nextFloat();
                test = false;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! You should input a real number!");
            }
        }
        return input;
    }
}
