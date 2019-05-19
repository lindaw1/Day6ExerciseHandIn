//Prepared by Linda Wallace
//Day 6 Exercise
//Winter OOSD
//May 20, 2019
//Program opens a database connection to TravelExperts Database located on localhost

package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    //connect to the database and return a connection object
    public static Connection getConnection()
    {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/travelexperts",
                    "root",
                    "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return conn;
    }
}