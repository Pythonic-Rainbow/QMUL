import java.util.Scanner;

/**
 * @author Howard Wong
 * Date: 10-11-2021
 * @version 1.0
 * Answering tourist questions about attractions
 */
public class SAX4 {

    /**
     * Defines an Attraction record as well as 3 given attractions
     */
    enum Attraction {
        EDEN("The Eden Project", true, (byte) 9),
        TATE("Tate Modern", false, (byte) 10),
        ZOO("London Zoo", true, (byte) 10);

        boolean openOnBankHolidays;
        byte openHour;
        String attractionName;

        Attraction(String name, boolean openOnBankHolidays, byte openHour) {
            this.attractionName = name;
            this.openOnBankHolidays = openOnBankHolidays;
            this.openHour = openHour;
        }
    }

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        loopAttractionInfo(scanner);
    }

    /**
     * Loop and ask the user what attractions he wants to know
     * @param s Scanner instance
     */
    public static void loopAttractionInfo(Scanner s) {
        System.out.print("How many attractions do you need to know about? ");
        byte a = s.nextByte();
        s.nextLine();
        for (byte i = 1;i <= a; i++) askAttraction(i, s);
    }

    /**
     * Asks the name of the attraction and display info, if available
     * @param i The i-th attraction that the user has input
     * @param s Scanner instance
     */
    public static void askAttraction(byte i, Scanner s) {
        System.out.printf("Name attraction %d? ", i);
        System.out.println(getAttractionInfo(s.nextLine()));
    }

    /**
     * Searches through the enum and returns the attraction, if available
     * @param name Name of the attraction
     * @return A String that represents the attraction details, or an error message.
     */
    public static String getAttractionInfo(String name) {
        for (Attraction a: Attraction.values()) if (a.attractionName.equals(name))
            return String.format("%s %sopen on bank holidays.%nIt opens at %dam.",
                    a.attractionName, a.openOnBankHolidays ? "" : "does not ", a.openHour);
        return "I have no information about that attraction.";
    }
}