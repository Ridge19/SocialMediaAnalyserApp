package com.example.socialmediaanalyser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.List;
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

    public ListView ShowPost;

    private ObservableList<Posts> posts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Connect to the database
            connection = DriverManager.getConnection("jdbc:sqlite:DataHub.db");
            System.out.println("Database Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void ListPost() throws SQLException {
        System.out.println("listing posts");

        // Get all posts from the database
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Posts");

        // Create a list of Post objects
        List<Posts> posts = new ArrayList<>();
        while (rs.next()) {
            posts.add(new Posts(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6)));
        }

        // If there are no posts in the database, then show an Alert
        if (posts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No posts found");
            alert.setHeaderText("No posts found in database");
            alert.setContentText("Please add a post first");
            alert.show();
        }

        // Create a ListView to display the posts
        ListView<Posts> listView = new ListView<>();
        ShowPost.setItems(FXCollections.observableArrayList(posts));

        // Set the cell factory for the ListView
        listView.setCellFactory(lv -> new ListCell<Posts>() {
            @Override
            protected void updateItem(Posts post, boolean empty) {
                super.updateItem(post, empty);
                if (post != null) {
                    setText(String.valueOf(post.getContent()));
                }
            }

        });

        // Close the database connection
//        rs.close();
//        stmt.close();
//        connection.close();
    }

    public void ClearPost() throws IOException {
        ShowPost.getItems().clear();
    }

    public void RemovePost(ActionEvent event) throws IOException, SQLException {
        System.out.println("Removing selected post");

        // Check if the RemovePostIDField is empty
        if (RemovePostIDField.getText() == null || RemovePostIDField.getText().isEmpty()) {
            // Show an error message to the user
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Post ID cannot be empty");
            alert.setContentText("enter a Post ID!");
            alert.showAndWait();
            return;
        }

        int PostID = Integer.parseInt(RemovePostIDField.getText());

        try {
            checkPostDoesNotExist(String.valueOf(PostID));

            // Create a prepared statement to insert the post into the database

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Posts WHERE PostID = ?");
            preparedStatement.setInt(1, PostID);


            // Execute the prepared statement
            preparedStatement.executeUpdate();

            // Show a success message to the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Post Removed");
            alert.setHeaderText("Success!");
            alert.setContentText("Your Post has been successfully removed.");
            alert.showAndWait();

            // Close the prepared statement
//        preparedStatement.close();

            // Clear the text fields
            RemovePostIDField.clear();
        } catch (NullPointerException n) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Post Cannot be removed!");
            alert.setHeaderText("Error");
            alert.setContentText("Your Post cannot be removed.");
            alert.showAndWait();

        }
    }


    // CHECK IF POST EXISTS BEFORE REMOVING METHOD
    public void checkPostDoesNotExist(String PostID) throws SQLException {
        // Create a prepared statement to check if the Post ID already exists in the database
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM Posts WHERE PostID = ?");
        preparedStatement.setString(1, PostID);

        // Execute the prepared statement and get the result
        ResultSet resultSet = preparedStatement.executeQuery();

        // If the result set is not empty, then the Post ID already exists in the database
        if (resultSet.next() && resultSet.getInt(1) == 0) {
            throw new SQLException("Post ID doesnt exist! Cannot delete");
        }

        // Close the prepared statement
//        preparedStatement.close();
    }

    public void AddPost(ActionEvent event) throws SQLException, IOException {
        System.out.println("Adding post");

        // Check if any of the text fields are empty
        if (PostIDField.getText() == null || PostIDField.getText().isEmpty() ||
                PostContentField.getText() == null || PostContentField.getText().isEmpty() ||
                PostAuthorField.getText() == null || PostAuthorField.getText().isEmpty() ||
                PostLikesField.getText() == null || PostLikesField.getText().isEmpty() ||
                PostSharesField.getText() == null || PostSharesField.getText().isEmpty() ||
                PostDateTimeField.getText() == null || PostDateTimeField.getText().isEmpty()) {
            // Show an error message to the user
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Adding Post");
            alert.setHeaderText("Please fill in all required fields");
            alert.showAndWait();
            return;
        }

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
            // Clear the text fields
            PostIDField.clear();
            PostContentField.clear();
            PostAuthorField.clear();
            PostLikesField.clear();
            PostSharesField.clear();
            PostDateTimeField.clear();
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
