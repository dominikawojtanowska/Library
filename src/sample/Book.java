package sample;

public class Book {
    String isbn;
    String title;
    String author;
    String category;

    Book(){
    }

    Book(String isbn, String title, String author, String category){
        this.isbn=isbn;
        this.title=title;
        this.author= author;
        this.category = category;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
