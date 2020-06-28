package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class LibraryDatabaseService {
    Connection con = null;
    Statement stat = null;

    LibraryDatabaseService(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root", "");
            stat = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
    }

    public String deleteClient(int cardNumber) {

        String command = "DELETE from rental where cardNumber=" + cardNumber;
        try {
            stat.executeUpdate(command);
            command = "DELETE from client where cardNumber=" + cardNumber;
            stat.executeUpdate(command);
            if(stat.getUpdateCount()==1) {
                return "SUCCESS";
            }else{
                return "There wasn't such an item";
            }
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public String deleteBook(String isbn) {
        String command = "DELETE from rental where isbn='" + isbn + "'";
        try {
            stat.executeUpdate(command);
            command = "DELETE from book where isbn='" + isbn + "'";
            stat.executeUpdate(command);
            if(stat.getUpdateCount()==1) {
                return "SUCCESS";
            }else{
                return "There wasn't such an item";
            }
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
    public String addUpdateRental(String isbn, int cardNumber) {
        try {
            ResultSet rs=stat.executeQuery("SELECT * from book where isbn = '" + isbn + "'");
            if(!rs.next()) return "There isn't such a book in out library";

            rs=stat.executeQuery("SELECT * FROM client where cardNumber=" + cardNumber);
            if(!rs.next()) return "There isn't client with this cardnumber in our database";

            rs=stat.executeQuery("SELECT * FROM rental where isbn = '" + isbn + "' AND dateOfReturn IS NULL AND cardnumber=" + cardNumber);
            if (rs.next()) {
                //client want to return book so we have to do update and fill dateofreturn with current date
                stat.executeUpdate("UPDATE rental SET dateOfReturn=CURDATE() WHERE isbn = '" + isbn + "' AND dateOfReturn IS NULL");
                return "The book was returned";
            }
            rs = stat.executeQuery("SELECT * FROM rental where isbn = '" + isbn + "' AND dateOfReturn IS NULL") ;// we want to avoid situation where 2 people are renting one book in the same time so we are chcecking it
            if (rs.next()) return "This book hasn't been returned yet";
            else{
                //it possible to rent this book
                stat.executeUpdate("INSERT INTO rental (isbn, cardNumber, dateOfRent) VALUES ('"+isbn+"', "+cardNumber +", CURDATE())");
                return "The book has been rented";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String addClient(String name, String surname, char sex) {
        String command = "INSERT INTO client (cardnumber, name, surname, sex) VALUES (default, '" + name + "', '" + surname +"', '"+sex+"')";
        try {
            stat.execute(command);
        } catch (SQLException e) {
            return e.getMessage();
        }
        int id = 0;
        try {
            ResultSet rs=stat.executeQuery("SELECT MAX(cardNumber) from client");
            rs.next();
            id = Integer.parseInt(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "SUCCESS, this is client's card's number: " + id;
    }

    public String addClient(String name, String surname, int year, char sexC) {
        String command = "INSERT INTO client (cardnumber, name, surname, yearofbirth, sex) VALUES (default, '" + name + "', '" + surname +"', " + year +", '"+sexC+"')";
        try {
            stat.execute(command);
        } catch (SQLException e) {
            return e.getMessage();
        }
        int id = 0;
        try {
            ResultSet rs=stat.executeQuery("SELECT MAX(cardNumber) from client");
            rs.next();
            id = Integer.parseInt(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "SUCCESS, this is client's card's number: " + id;
    }

    public String addBook(String isbn, String title, String author, String category) {
        String command = "INSERT INTO book (isbn, title, author, category) VALUES ('" + isbn + "', '" + title +"', '" + author +"', '" + category + "')";
        try {
            stat.execute(command);
        } catch (SQLException e) {
            return e.getMessage();
        }
        return "SUCCESS";
    }

    public ObservableList<Book> getPopularBookList(int yearOfBirth, String category) {
        ObservableList<Book> list = FXCollections.observableArrayList();
        try {
            ResultSet rs=stat.executeQuery("SELECT book.isbn, title, author, category, count(*) from book, rental, client where book.isbn=rental.isbn AND rental.cardNumber=client.cardNumber AND category='"+category+"' AND(yearofbirth IS NULL OR yearofbirth BETWEEN "+ (yearOfBirth-3) + " AND " + (yearOfBirth+3) + ") group By isbn, title, author, category ORDER BY 5 LIMIT 10");
            while(rs.next()){
                list.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<Book> getPopularBookList(int yearOfBirth) {
        ObservableList<Book> list = FXCollections.observableArrayList();
        try {
            ResultSet rs=stat.executeQuery("SELECT book.isbn, title, author, category, count(*) from book, rental, client where book.isbn=rental.isbn AND rental.cardNumber=client.cardNumber AND(yearofbirth IS NULL OR yearofbirth BETWEEN "+ (yearOfBirth-3) + " AND " + (yearOfBirth+3) + ") group By isbn, title, author, category ORDER BY 5 LIMIT 10");
            while(rs.next()){
                list.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<Book> getPopularBookList(String category) {
        ObservableList<Book> list = FXCollections.observableArrayList();
        try {
            ResultSet rs=stat.executeQuery("SELECT book.isbn, title, author, category, count(*) from book, rental where book.isbn=rental.isbn AND category='"+category+"' group By isbn, title, author, category ORDER BY 5 LIMIT 10");
            while(rs.next()){
                list.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<Book> getPopularBookList() {
        ObservableList<Book> list = FXCollections.observableArrayList();
        try {
            ResultSet rs=stat.executeQuery("SELECT book.isbn, title, author, category, count(*) from book, rental where book.isbn=rental.isbn group By isbn, title, author, category ORDER BY 5 LIMIT 10");
            while(rs.next()){
                list.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
