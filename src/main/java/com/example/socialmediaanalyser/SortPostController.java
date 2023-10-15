package com.example.socialmediaanalyser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SortPostController implements Initializable {
    @FXML
    public Label SortPost;
    @FXML
    public Label RetrieveLabel;
    @FXML
    public ListView SortView;
    @FXML
    public TextField SortPostField;
    @FXML
    public Button SortButton;
    @FXML
    public Button BackButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void SortPostLikes(ActionEvent event) throws IOException {
        System.out.println("getting posts...");
        System.out.println("Sorting posts by likes");
    }

    public void Back(ActionEvent event) throws IOException {
        System.out.println("going back to main page");

        System.out.println("Adding/Listing Posts");

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
