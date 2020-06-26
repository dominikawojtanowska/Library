package sample;

import java.sql.*;

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
    public String addDeleteRental(String isbn, int cardNumber) {
        try {
            ResultSet rs = stat.executeQuery("SELECT * FROM rental where isbn = '" + isbn + "' AND dateOfReturn IS NULL") ;// we want to avoid situation where 2 people are renting one book in the same time so we are chcecking it
            if (rs.next()) return "This book hasn't been returned yet";

            rs=stat.executeQuery("SELECT * FROM rental where isbn = '" + isbn + "' AND dateOfReturn IS NULL AND cardnumber=" + cardNumber);
            if (rs.next()) {
                //client want to return book so we have to do update and fill dateofreturn with current date
                //int r = stat.executeUpdate("UPDATE ON rental WHERE isbn = '\" + isbn + \"' AND dateOfReturn IS NULL AND cardnumber=\" + cardNumber");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
