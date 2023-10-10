package com.example.socialmediaanalyser;


import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

// connects to database named DataHub
public class DatabaseConnection {
    private static final String DB_URL = "jdbc:sqlite:DataHub.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

}

