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
import org.w3c.dom.Text;

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

    //change username and password
    @FXML
    public Label NewUNameLabel;
    @FXML
    public Label NewPWordLabel;

    @FXML
    public TextField NewUNameField;
    @FXML
    public TextField NewPWordField;

    @FXML
    public Label CurrentUNameLabel;
    @FXML
    public TextField CurrentUNameField;

    // change First and Last Name
    @FXML
    public Label CurrentFNameLabel;
    @FXML
    public Label CurrentLNameLabel;
    @FXML
    public Label NewFNameLabel;
    @FXML
    public Label NewLNameLabel;
    @FXML
    public TextField CurrentLNameField;
    @FXML
    public TextField CurrentFNameField;
    @FXML
    public TextField NewFNameField;
    @FXML
    public TextField NewLNameField;



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

    public void UpdateUsernamePassword() throws IOException, SQLException {
        System.out.println("Update Username or password");

        // Get the new username and password from the text fields

        String CurrentUName = CurrentUNameField.getText();
        String UserName = NewUNameField.getText();
        String Password = NewPWordField.getText();



        // Create a prepared statement to update the username and password in the database
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Login SET UserName = ?, Password = ? WHERE UserName = ?");

        // Set the parameters of the prepared statement
        preparedStatement.setString(1, UserName);
        preparedStatement.setString(2, Password);
        preparedStatement.setString(3, CurrentUName);

        // Execute the prepared statement
        int updatedRows = preparedStatement.executeUpdate();

        // Close the prepared statement
        preparedStatement.close();

        // Show a success message to the user
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Account Details Updated");
        alert.setHeaderText("Success!");
        if (updatedRows > 0) {
            alert.setContentText("Your Account has been successfully Updated.");
        } else {
            alert.setContentText("Account was not updated! try again.");
        }
        alert.showAndWait();
    }

    public void UpdateFirstLastName() throws IOException, SQLException {
        System.out.println("Update First Name or Last Name");

        // Get the new First Name and Last Name from the text fields

        // DECLARE VARIABLES
        String CurrentFName = CurrentFNameField.getText();
        String CurrentLName = CurrentLNameField.getText();
        String FirstName = NewFNameField.getText();
        String LastName = NewLNameField.getText();


        // Create a prepared statement to update the username and password in the database
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Login SET FirstName = ?, LastName = ? WHERE FirstName = ? and LastName = ?");

        // Set the parameters of the prepared statement
        preparedStatement.setString(1, FirstName);
        preparedStatement.setString(2, LastName);
        preparedStatement.setString(3, CurrentFName);
        preparedStatement.setString(4, CurrentLName);

        // Execute the prepared statement
        int updatedRows = preparedStatement.executeUpdate();

        // Close the prepared statement
        preparedStatement.close();

        // Show a success message to the user
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Account Details Updated");
        if (updatedRows > 0) {
            alert.setHeaderText("Success!");
            alert.setContentText("Your Account has been successfully Updated.");
        } else {
            alert.setHeaderText("Attention!");
            alert.setContentText("Account was not updated! try again.");
        }
        alert.showAndWait();

        CurrentFNameField.clear();
        CurrentLNameField.clear();
        NewFNameField.clear();
        NewLNameField.clear();
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
