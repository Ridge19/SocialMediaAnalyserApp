package com.example.socialmediaanalyser;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

//creates table where users are stored.
public class CreateTableLogin {
    public void createLogin() throws SQLException {
        System.out.println("Login Table created");
        final String TABLE_NAME = "Login";

        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                    + "(id INT NOT NULL,"
                    + "UserName VARCHAR(20) NOT NULL,"
                    + "Password VARCHAR(20) NOT NULL,"
                    + "FirstName VARCHAR(20) NOT NULL,"
                    + "LastName VARCHAR(20) NOT NULL,"
                    + "Email VARCHAR(70) NOT NULL,"
                    + "VIP INTEGER NOT NULL,"
                    + "PRIMARY KEY (id))");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}

