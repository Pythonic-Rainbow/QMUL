package item;

import java.util.ArrayList;

public class Catalog {
    private String catalogName;
    private int catalogId;
    private ArrayList<Book> books;

    public Catalog(String catalogName, int catalogId) {
        this.catalogName = catalogName;
        this.catalogId = catalogId;
    }

    public void addBook(Book b) {}
    public void removeBook(Book b) {}
}
