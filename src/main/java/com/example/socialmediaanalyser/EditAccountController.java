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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditAccountController implements Initializable {
    @FXML
    public Label UpdateAccountLabel;
    @FXML
    public Label DisplayUser;
    @FXML
    public Label ChangeUserPassLabel;
    @FXML
    public Label ChangeFirstLastNameLabel;

    @FXML
    public Button UpdateUserandPassword;
    @FXML
    public Button UpdateFirstLastName;
    @FXML
    public Button AccountBackButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    public void AccountBack(ActionEvent event) throws SQLException, IOException {
        System.out.println("going back to main page");

        // Get the AccountController instance.
        MainController mainController = new MainController();

        // Load the AccountPage.fxml file.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main-Page.fxml")));

        // Set the AccountController instance as the controller for the AccountPage.fxml file.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main-Page.fxml"));
        loader.setController(mainController);

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
