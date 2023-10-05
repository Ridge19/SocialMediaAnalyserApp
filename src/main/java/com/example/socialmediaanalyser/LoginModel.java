package com.example.socialmediaanalyser;

//import java.awt.*;
import javafx.fxml.FXML;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginModel {
    Connection connection;

    public LoginModel() throws SQLException {
        connection = DatabaseConnection.getConnection();
        if (connection == null) {
            System.exit(1);
        }
    }

    public boolean isDbConnected() {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isLogin(String UserName, String PassWord) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Login where UserName = ? and Password = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, UserName);
            preparedStatement.setString(2, PassWord);

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
}

