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
import javafx.stage.Window;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class VIPController implements Initializable {
    @FXML
    public AnchorPane AccountSetting;
    @FXML
    public Label VIPLabel;
    @FXML
    public Button NoButton;
    @FXML
    public Button YesButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void YesVIP(ActionEvent event) throws IOException {
        System.out.println("Signing up for VIP");
    }

    public void NoVIP(ActionEvent event) throws IOException {
        System.out.println("Cancelling VIP registration");

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
