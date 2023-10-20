package com.example.socialmediaanalyser;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//creates table where posts are stored.
public class CreateTablePosts {
    public void createPosts() throws SQLException {
        // Check if the table Posts already exists
        Connection connection = DatabaseConnection.getConnection();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(null, null, "Posts", null);

        // If the table does not exist, create it
        if (!resultSet.next()) {
            System.out.println("Posts Table created");
            final String TABLE_NAME = "Posts";

            try (Statement stmt = connection.createStatement();) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                        + "(PostID INT NOT NULL,"
                        + "PostContent VARCHAR(50) NOT NULL,"
                        + "PostAuthor VARCHAR(20) NOT NULL,"
                        + "PostLikes INT NOT NULL,"
                        + "PostShares INT NOT NULL,"
                        + "DateTime DATETIME NOT NULL,"
                        + "PRIMARY KEY (PostID))");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            // If the table already exists, display a confirmation message
            System.out.println("Posts Table already exists");
        }

        // Close the result set and connection
        resultSet.close();
        connection.close();
    }
}