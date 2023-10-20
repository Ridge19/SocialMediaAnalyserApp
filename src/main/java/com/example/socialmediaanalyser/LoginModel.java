package com.example.socialmediaanalyser;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;


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


    public static boolean isLogin(String UserName, String Password, Connection connection) throws SQLException {
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
        if (databaseConnection != null) {
            try {
                databaseConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}