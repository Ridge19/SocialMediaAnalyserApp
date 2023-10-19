package com.example.socialmediaanalyser;

//import com.sun.javafx.menu.MenuItemBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class SortPostController implements Initializable {
    @FXML
    public Label SortPost;
    @FXML
    public Label RetrieveLabel;
    @FXML
    public Label InfoLabel;
    @FXML
    public ListView SortView;
    @FXML
    public TextField SortPostField;
    @FXML
    public Button SortLikesButton;
    @FXML
    public Button ClearField;
    @FXML
    public Button SortSharesButton;
    @FXML
    public Button BackButton;

    private Connection connection;

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

    public void SortPostLikes() throws SQLException {
        System.out.println("getting posts...");
        System.out.println("Sorting posts by (n) likes");

        try {
            checkSortPostFieldEmpty(SortPostField);
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Sorting Post");
            alert.setHeaderText("Enter a number!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            SortPostField.clear();
        }
        // Get all posts from the database
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT PostID, PostContent, PostLikes, PostShares FROM Posts");

        // Create a list of Post objects
        List<Posts> posts = new ArrayList<>();
        while (rs.next()) {
            posts.add(new Posts(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
        }

        ObservableList<String> items = FXCollections.observableArrayList();

        // Set the items of the ListView to the ObservableList
        SortView.setItems(items);

        int num = Integer.parseInt(SortPostField.getText());

        if (num < posts.size()) {
            InfoLabel.setText("The top-" + num + " posts with the most likes are: ");
            for (int i = 0; i < num; i++) {
                Collections.sort(posts, Collections.reverseOrder());
                Posts p = posts.get(i);
                items.add(p.getPostID() + " | " + p.getContent() + " | " + p.getLikes());
            }
        } else if (num > posts.size()) {
            InfoLabel.setText("only " + posts.size() + " in the collection. Showing all of them.");
            for (Posts p : posts) {
                items.add(p.getPostID() + " | " + p.getContent() + " | " + p.getLikes());
            }
        }
    }

    public static void checkSortPostFieldEmpty(TextField sortPostField) {
        if (sortPostField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot sort posts");
            alert.setContentText("Please enter a number of posts to sort.");
            alert.showAndWait();
        }
    }

    public void SortPostShares(ActionEvent event) throws IOException, SQLException {
        System.out.println("sorting by (n) Shares");

        try {
            checkSortPostFieldEmpty(SortPostField);
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Sorting Post");
            alert.setHeaderText("Enter a number!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            SortPostField.clear();
        }
        // Get all posts from the database
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT PostID, PostContent, PostShares FROM Posts");

        // Create a list of Post objects
        List<Posts> posts = new ArrayList<>();
        while (rs.next()) {
            posts.add(new Posts(rs.getInt(1), rs.getString(2), rs.getInt(3)));
        }

        ObservableList<String> items = FXCollections.observableArrayList();

        // Set the items of the ListView to the ObservableList
        SortView.setItems(items);

        int num = Integer.parseInt(SortPostField.getText());

        if (num < posts.size()) {
            InfoLabel.setText("The top-" + num + " posts with the most likes are: ");
            for (int i = 0; i < num; i++) {
                Collections.sort(posts, Collections.reverseOrder());
//                posts.sort(Comparator.comparingInt(Posts::getShares));
                Posts p = posts.get(i);
                items.add(p.getPostID() + " | " + p.getContent() + " | " + p.getShares());
            }
        } else if (num > posts.size()) {
            InfoLabel.setText("only " + posts.size() + " in the collection. Showing all of them.");
            for (Posts p : posts) {
                items.add(p.getPostID() + " | " + p.getContent() + " | " + p.getShares());
            }
        }
    }

    public void ClearAll(ActionEvent event) {
        // Clear the TextField
        SortPostField.clear();
        SortView.getItems().clear();
        InfoLabel.setText("");
    }

    public void Back(ActionEvent event) throws IOException, SQLException {
        System.out.println("going back to main page");

        System.out.println("Adding/Listing Posts");

        // Close the prepared statement and the connection
        connection.close();

        // Get the postController instance.
        MainController mainController = new MainController();

        // Load the Main-Page.fxml file.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main-Page.fxml")));

        // Set the PostController instance as the controller for the AccountPage.fxml file.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main-Page.fxml"));
        loader.setController(mainController);

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
