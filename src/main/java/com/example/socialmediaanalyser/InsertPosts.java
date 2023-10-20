package com.example.socialmediaanalyser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsertPosts {
    public void insertPosts() throws SQLException {
        // Create a list of Post objects.
        List<Posts> posts = new ArrayList<>();
        posts.add(new Posts(20582,"Come and meet us at Building 14 of RMIT.", "SD2C45", 10, 24, "12/05/2023 10:10"));
        posts.add(new Posts(10,"Check out this epic film", "A567VF", 1000, 1587, "1/06/2023 14:25"));
        posts.add(new Posts(37221,"Are we into Christmas month already?!", "3827F2", 526, 25, "15/11/2022 23:30"));
        posts.add(new Posts(382, "What a miracle!", "38726I", 2775, 13589,"12/02/2023 18:18"));
        posts.add(new Posts(36778,"Fantastic day today. Congratulations to all winners.", "1258XE", 240, 1214, "6/06/2023"));

        // Create a connection to the database.
        Connection connection = DriverManager.getConnection("jdbc:sqlite:DataHub.db");

        // Check if the Posts table has any posts.
        PreparedStatement checkStatement = connection.prepareStatement("SELECT COUNT(*) FROM Posts");
        ResultSet resultSet = checkStatement.executeQuery();
        int rowCount = resultSet.getInt(1);
        resultSet.close();
        checkStatement.close();

        // If the Posts table has no posts, insert the posts from the list.
        if (rowCount == 0) {
            // Create a prepared statement to insert the post data into the database.
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Posts (PostID,PostContent,PostAuthor,PostLikes,PostShares,DateTime) VALUES (?, ?, ?, ?, ?, ?)");

            // Iterate over the list of Post objects and insert each post into the database.
            for (Posts post : posts) {
                insertStatement.setInt(1, post.getPostID());
                insertStatement.setString(2, post.getContent());
                insertStatement.setString(3, post.getAuthor());
                insertStatement.setInt(4, post.getLikes());
                insertStatement.setInt(5, post.getShares());
                insertStatement.setString(6, post.getdateTime());

                insertStatement.executeUpdate();
            }

            System.out.println("5 posts have been inserted into database DataHub.db and Tabke Posts");

            // Close the prepared statement.
            insertStatement.close();
        } else {
            System.out.println("The Posts table already has posts.");
        }

        // Close the connection to the database.
        connection.close();
    }
}