package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LibraryDatabaseService {
    Connection con = null;
    Statement stat = null;

    LibraryDatabaseService(){
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "dominika");
            stat = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    public String deleteClient(int cardNumber) {
        String command = "DELETE from client where cardNumber=" + cardNumber;
        try {
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
        String command = "DELETE from book where isbn='" + isbn + "'";
        try {
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
    public String addDeleteRental() {
        return " wyporzyczono oddano";
    }

    public String addClient(String name, String surname, char sex) {
        String command = "INSERT INTO client (cardnumber, name, surname, sex) VALUES (default, '" + name + "', '" + surname +"', '"+sex+"')";
        try {
            stat.execute(command);
        } catch (SQLException e) {
            return e.getMessage();
        }
        return "SUCCESS";
    }

    public String addClient(String name, String surname, int year, char sexC) {
        String command = "INSERT INTO client (cardnumber, name, surname, yearofbirth, sex) VALUES (default, '" + name + "', '" + surname +"', " + year +", '"+sexC+"')";
        try {
            stat.execute(command);
        } catch (SQLException e) {
            return e.getMessage();
        }
        return "SUCCESS";
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
}
