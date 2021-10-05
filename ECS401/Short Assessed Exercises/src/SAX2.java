import java.util.Scanner;

public class SAX2 {
    private static final Scanner reader = new Scanner(System.in);

    public static void main(String[] args) {
        float length = getUserInputFloat("Enter length of the room (cm): ");
        float width = getUserInputFloat("Enter width of the room (cm): ");
        float height = getUserInputFloat("Enter height of the room (cm): ");
        float balloonVolume = getUserInputFloat("Enter balloon volume (m³): ");
        float roomVolume = length * width * height / 1000000;

        System.out.println("Your room volume is " + roomVolume + " m³.");
        System.out.println("You can have at most " + (int)(roomVolume / balloonVolume) + " balloons");
    }

    public static float getUserInputFloat(String query) {
        System.out.print(query);
        return reader.nextFloat();
    }
}
