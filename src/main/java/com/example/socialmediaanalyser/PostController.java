package com.example.socialmediaanalyser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

// update, delete, show and sort posts.

public class PostController implements Initializable {
    @FXML
    public Label AddPostLabel;
    @FXML
    public Label PostIDLabel;
    @FXML
    public Label PostContentLabel;
    @FXML
    public Label PostAuthorLabel;
    @FXML
    public Label PostLikesLabel;
    @FXML
    public Label PostSharesLabel;
    @FXML
    public Label PostDateTimeLabel;

    @FXML
    public TextField PostIDField;
    @FXML
    public TextField PostContentField;
    @FXML
    public TextField PostAuthorField;
    @FXML
    public TextField PostLikesField;
    @FXML
    public TextField PostSharesField;
    @FXML
    public TextField PostDateTimeField;

    // Remove post
    @FXML
    public Label RemovePostLabel;
    @FXML
    public Label RemovePostIDLabel;
    @FXML
    public TextField RemovePostIDField;
    @FXML
    public Button RemoveButton;
    @FXML
    public Button RemoveBackButton;

    @FXML
    public Button AddPostButton;
    @FXML
    public Button PostBackButton;

    private Connection connection;

//    private ListView<Post> listView;
//    private ObservableList<Post> posts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Connect to the database
            connection = DriverManager.getConnection("jdbc:sqlite:DataHub.db");
            System.out.println("Database Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        // Get all posts from the database
//        ObservableList<String> Posts = null;
//
//        try {
//            Posts = ListPost();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//
//        // Add the posts to the ListView
//        ShowPost.setItems(Posts);
    }

    public void RemoveBack(ActionEvent event) throws IOException {
        System.out.println("going back to main page");

        System.out.println("Adding/Listing Posts");

        // Get the AccountController instance.
        PostController postController = new PostController();

        // Load the Main-Page.fxml file.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main-Page.fxml")));

        // Set the PostController instance as the controller for the AccountPage.fxml file.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main-Page.fxml"));
        loader.setController(postController);

        // Create a new scene with the Main-Page.fxml file as the root node.
        Scene scene = new Scene(root);

        // Get the stage from the event.

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene of the stage to the new scene.
        stage.setScene(scene);

        // Show the stage.
        stage.show();
    }

//    @FXML
//    private ListView<String> ShowPost;
//

    public void ListPost() {

    }

    public void RemovePost(ActionEvent event) throws IOException, SQLException {
        System.out.println("Removing selected post");

        int PostID = Integer.parseInt(RemovePostIDField.getText());

        // Show a success message to the user
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Post Removed");
        alert.setHeaderText("Success!");
        alert.setContentText("Your Post has been successfully removed.");
        alert.showAndWait();


        // Create a prepared statement to insert the post into the database

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Posts WHERE PostID = ?");
        preparedStatement.setInt(1, PostID);


        // Execute the prepared statement
        preparedStatement.executeUpdate();

        // Close the prepared statement
        preparedStatement.close();

        // Clear the text fields
        RemovePostIDField.clear();
    }


        // CHECK IF POST EXISTS BEFORE REMOVING METHOD

//    public void checkPostDoesNotExist(String PostID) throws SQLException {
//        // Create a prepared statement to check if the Post ID already exists in the database
//        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM Posts WHERE PostID = ?");
//        preparedStatement.setString(1, PostID);
//
//        // Execute the prepared statement and get the result
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        // If the result set is not empty, then the Post ID already exists in the database
//        if (resultSet.next() && resultSet.getInt(1) == 0) {
//            throw new SQLException("Post ID doesnt exist! Cannot delete");
//        }
//
//        // Close the prepared statement
//        preparedStatement.close();
//    }

    public void AddPost(ActionEvent event) throws SQLException, IOException {
        System.out.println("Adding post");

        // Get the account information from the text fields
        int PostID = Integer.parseInt(PostIDField.getText());
        String PostContent = PostContentField.getText();
        String PostAuthor = PostAuthorField.getText();
        int PostLikes = Integer.parseInt(PostLikesField.getText());
        int PostShares = Integer.parseInt(PostSharesField.getText());
        String DateTime = PostDateTimeField.getText();

        try {
            checkForDuplicates(String.valueOf(PostID));
        } catch (SQLException e) {
            // Display an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Adding Post");
            alert.setHeaderText("Duplicate Post ID");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        // Create a prepared statement to insert the post into the database

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Posts (PostID,PostContent,PostAuthor,PostLikes,PostShares,DateTime) VALUES (?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, PostID);
        preparedStatement.setString(2, PostContent);
        preparedStatement.setString(3, PostAuthor);
        preparedStatement.setString(4, String.valueOf(PostLikes));
        preparedStatement.setString(5, String.valueOf(PostShares));
        preparedStatement.setString(6, DateTime);

        // Execute the prepared statement
        preparedStatement.executeUpdate();

        // Close the prepared statement
        preparedStatement.close();

        // Show a success message to the user
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Post Added");
        alert.setHeaderText("Success!");
        alert.setContentText("Your Post has been successfully created.");
        alert.showAndWait();

        // Clear the text fields
        PostIDField.clear();
        PostContentField.clear();
        PostAuthorField.clear();
        PostLikesField.clear();
        PostSharesField.clear();
        PostDateTimeField.clear();
    }

    //Check for duplicate Post ID
    private void checkForDuplicates(String PostID) throws SQLException {
        // Create a prepared statement to check if the Post ID already exists in the database
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM Posts WHERE PostID = ?");
        preparedStatement.setString(1, PostID);

        // Execute the prepared statement and get the result
        ResultSet resultSet = preparedStatement.executeQuery();

        // If the result set is not empty, then the Post ID already exists in the database
        if (resultSet.next() && resultSet.getInt(1) > 0) {
            throw new SQLException("Post ID already exists");
        }

        // Close the prepared statement
        preparedStatement.close();
    }


    //back button to send user back to Main Page.
    public void PostBack(ActionEvent event) throws IOException {
        System.out.println("going back to main page");

        System.out.println("Adding/Listing Posts");

        // Get the AccountController instance.
        PostController postController = new PostController();

        // Load the Main-Page.fxml file.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main-Page.fxml")));

        // Set the PostController instance as the controller for the AccountPage.fxml file.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main-Page.fxml"));
        loader.setController(postController);

        // Create a new scene with the Main-Page.fxml file as the root node.
        Scene scene = new Scene(root);

        // Get the stage from the event.

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene of the stage to the new scene.
        stage.setScene(scene);

        // Show the stage.
        stage.show();
    }

}
