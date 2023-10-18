package com.example.socialmediaanalyser;

import java.net.URL;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.socialmediaanalyser.VIPController.connection;

// checks if connected
// verifies username and password for login page.


public class LoginModel {
    private static Connection databaseConnection;
    private static Connection connection;

    public LoginModel() throws SQLException {
        try {
            // Connect to the database
            connection = DriverManager.getConnection("jdbc:sqlite:DataHub.db");
            System.out.println("Database Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        databaseConnection = DatabaseConnection.getConnection();
        if (databaseConnection == null) {
            System.exit(1);
        }
    }


    private static String loggedInUser;

    public static boolean loggedInUser(String UserName) {
        return Boolean.parseBoolean(UserName);
    }

    public static void setLoggedInUser(String loggedInUser) {
        LoginModel.loggedInUser = loggedInUser;
    }

    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public static boolean isVIP(String UserName) throws SQLException {
        // Get the user's VIP status from the database
        // ...
        // Create a prepared statement to get the user's VIP status.

        PreparedStatement statement = connection.prepareStatement("SELECT VIP FROM Login WHERE UserName = ?");

        // Set the parameter of the prepared statement.
        statement.setString(1, UserName);

        // Execute the prepared statement and get the results.
        ResultSet results = statement.executeQuery();

        // Check if the results are empty.
        if (!results.next()) {
            // If the results are empty, the user does not exist in the database.
            return false;
        }

        // Get the user's VIP status from the results.
        boolean isVIP = results.getBoolean("VIP");

        // Return the user's VIP status.
        return isVIP;

        // Return the user's VIP status
        // Replace this with the actual code to get the user's VIP status from the database
        // Close the prepared statement and the connection.
    }

    public static void setVIP(String UserName, boolean isVIP) {
        // Update the user's VIP status in the database
        // ...
    }

    public static boolean isLogin(String UserName, String Password) throws SQLException {
        if (!isDbConnected()) {
            throw new SQLException("Connection to database is closed.");
        }

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Login where UserName = ? and Password = ?";

        try {
            preparedStatement = databaseConnection.prepareStatement(query);
            preparedStatement.setString(1, UserName);
            preparedStatement.setString(2, Password);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    private static boolean isDbConnected() {
        try {
            return !databaseConnection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void logout() {
        try {
            databaseConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}