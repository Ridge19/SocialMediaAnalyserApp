package com.example.socialmediaanalyser;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//creates table where users are stored.
public class CreateTableLogin {
    public void createLogin() throws SQLException {
        // Check if the table Login already exists
        Connection connection = DatabaseConnection.getConnection();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(null, null, "Login", null);

        // If the table does not exist, create it
        if (!resultSet.next()) {
            System.out.println("Login Table created");
            final String TABLE_NAME = "Login";

            try (Statement stmt = connection.createStatement();) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                        + "(UserName VARCHAR(20) NOT NULL,"
                        + "Password VARCHAR(20) NOT NULL,"
                        + "FirstName VARCHAR(20) NOT NULL,"
                        + "LastName VARCHAR(20) NOT NULL,"
                        + "PRIMARY KEY (UserName))");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            // If the table already exists, display a confirmation message
            System.out.println("Login Table already exists");
        }

        // Close the result set and connection
        resultSet.close();
        connection.close();
    }
}