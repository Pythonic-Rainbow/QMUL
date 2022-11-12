import item.*;
import system.Loan;
import user.Member;

import java.util.ArrayList;
import java.util.List;

public class LibraryInventory {
    private static List<Item> items;
    private static List<Catalog> catalogs = new ArrayList<>();
    private static List<Loan> loans = new ArrayList<>();
    private static List<Member> members = new ArrayList<>();

    public static void addItem(Item item) {}
    public static void removeItem(Item item) {}

    public static List<Item> search(String text) {
        return null;
    }

    public static List<Book> searchBooks(String text) {
        return null;
    }

    public static List<Magazine> searchMagazines(String text) {
        return null;
    }

    public static List<Movie> searchMovies(String text) {
        return null;
    }
}
