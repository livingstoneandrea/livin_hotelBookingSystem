package database_utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db_connect {
    public Db_connect(){

    }
    public static Connection get_connection() throws SQLException, ClassNotFoundException {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/hotel_bookingSystem" , "root", "@livingstone7");
            System.out.println("succesfully connected");
        return connection;
    }
}
