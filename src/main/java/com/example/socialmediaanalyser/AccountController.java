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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class AccountController implements Initializable {
    @FXML
    public Label CreateLabel;
    @FXML
    public Label usernameLabel;
    @FXML
    public Label PasswordLabel;
    @FXML
    public Label FirstNameLabel;
    @FXML
    public Label LastNameLabel;
    @FXML
    public Label EmailLabel;

    @FXML
    public TextField UsernameField;
    @FXML
    public TextField PasswordField;
    @FXML
    public TextField FirstNameField;
    @FXML
    public TextField LastNameField;
    @FXML
    public TextField EmailField;
    @FXML
    public Button CreateAccountButton;
    @FXML
    public Button BackButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void CreateAccount() throws SQLException, IOException {
        System.out.println("Making account");
        System.out.println("Successfully passed to AccountController");

    }

    public void Back(ActionEvent event) throws SQLException, IOException {
        System.out.println("going back");

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
}
