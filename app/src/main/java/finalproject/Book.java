package finalproject;

import java.util.ArrayList;

public class Book {
    String title;
    String category;
    String desc;
    double price;

    int imageID;

    public static final ArrayList<Book> books = initializeBooks();

    private static ArrayList<Book> initializeBooks() {
        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(new Book("programe","xsdnk","cdsncjds", 3.5 , R.drawable.diavolo));
        bookList.add(new Book("programe","xsdnk","cdsncjds", 3.5 , R.drawable.diavolo)
        );
        bookList.add(new Book("programe","xsdnk","cdsncjds", 3.5 , R.drawable.diavolo));
        bookList.add(new Book("programe","xsdnk","cdsncjds", 3.5 , R.drawable.diavolo));
        return bookList;
    }

    public Book(String title, String category, String desc, double price, int imageID) {
        this.title = title;
        this.category = category;
        this.desc = desc;
        this.price = price;
        this.imageID = imageID;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
