package com.example.socialmediaanalyser;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

//creates table where posts are stored.
public class CreateTablePosts {
    public static void main(String[] args) {
        final String TABLE_NAME = "Posts";

        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();) {
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
    }
}


