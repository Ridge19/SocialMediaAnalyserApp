package com.example.socialmediaanalyser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    public Label TitleLabel;
    @FXML
    public Label UserLabel;
    @FXML
    public Label AddPost;
    @FXML
    public Label RemovePost;
    @FXML
    public Label SortPost;
    @FXML
    public Button AccountButton;
    @FXML
    public Button AddListButton;
    @FXML
    public Button RemovePostButton;
    @FXML
    public Button SortPostButton;
    @FXML
    public Button SignOut;
    public AnchorPane MainPage;

    // main page controller
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void SignOut(ActionEvent event) throws SQLException, IOException {
        System.out.println("Signing Out");

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Signing out");
        alert.setHeaderText("You're about to sign out of your account!");
        alert.setContentText("Are you sure you want to sign out?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) MainPage.getScene().getWindow();
            System.out.println("you successfully logged out!");
            stage.close();
        } else {
            Stage stage = (Stage) MainPage.getScene().getWindow();
        }

        // Get the AccountController instance.
        LoginController loginController = new LoginController();

        // Load the AccountPage.fxml file.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginPage.fxml")));

        // Set the AccountController instance as the controller for the AccountPage.fxml file.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        loader.setController(loginController);

        // Create a new scene with the AccountPage.fxml file as the root node.
        Scene scene = new Scene(root);

        // Get the stage from the event.

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene of the stage to the new scene.
        stage.setScene(scene);

        // Show the stage.
        stage.show();
    }

    public void AddList(ActionEvent event) throws IOException {
        System.out.println("Adding/Listing Posts");

        // Get the AccountController instance.
        PostController postController = new PostController();

        // Load the AccountPage.fxml file.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Add-ListPost.fxml")));

        // Set the AccountController instance as the controller for the AccountPage.fxml file.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Add-ListPost.fxml"));
        loader.setController(postController);

        // Create a new scene with the AccountPage.fxml file as the root node.
        Scene scene = new Scene(root);

        // Get the stage from the event.

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene of the stage to the new scene.
        stage.setScene(scene);

        // Show the stage.
        stage.show();
    }

    public void ShowRemovePost(ActionEvent event) throws IOException {
        System.out.println("going back to main page");

        System.out.println("Adding/Listing Posts");

        // Get the AccountController instance.
        PostController postController = new PostController();

        // Load the Main-Page.fxml file.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RemovePost.fxml")));

        // Set the PostController instance as the controller for the AccountPage.fxml file.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RemovePost.fxml"));
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


    public void AccountSettings(ActionEvent event) throws IOException {
        System.out.println("Going to account Settings");

        // Get the AccountController instance.
        EditAccountController editAccountController = new EditAccountController();

        // Load the AccountPage.fxml file.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AccountSetting.fxml")));

        // Set the AccountController instance as the controller for the AccountPage.fxml file.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountSetting.fxml"));
        loader.setController(editAccountController);

        // Create a new scene with the AccountPage.fxml file as the root node.
        Scene scene = new Scene(root);

        // Get the stage from the event.

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene of the stage to the new scene.
        stage.setScene(scene);

        // Show the stage.
        stage.show();
    }
}