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

    LoginModel loginModel = new LoginModel();

    public AccountController() throws SQLException {
    }



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

    public void CreateAccount() throws SQLException, IOException {
        System.out.println("Making account");
        System.out.println("Successfully passed to AccountController");

        // Get the account information from the text fields
        int id = 0;
        String UserName = UsernameField.getText();
        String Password = PasswordField.getText();
        String FirstName = FirstNameField.getText();
        String LastName = LastNameField.getText();
        String Email = EmailField.getText();

        try {
            checkForDuplicates(UserName, Email);
        } catch (SQLException e) {
            // Display an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Creating Account");
            alert.setHeaderText("Duplicate Username or Email");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        // Create a prepared statement to insert the post into the database
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Login (id,UserName,Password,FirstName,LastName,Email) VALUES (?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, UserName);
        preparedStatement.setString(3, Password);
        preparedStatement.setString(4, FirstName);
        preparedStatement.setString(5, LastName);
        preparedStatement.setString(6, Email);

        // Execute the prepared statement
        preparedStatement.executeUpdate();

        // Close the prepared statement
        preparedStatement.close();

        // Show a success message to the user
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Account Added");
        alert.setHeaderText("Success!");
        alert.setContentText("Your Account has been successfully created.");
        alert.showAndWait();

        // Clear the text fields
        UsernameField.clear();
        PasswordField.clear();
        FirstNameField.clear();
        LastNameField.clear();
        EmailField.clear();
    }

    private void checkForDuplicates(String UserName, String Email) throws SQLException {
        // Create a prepared statement to check if the username already exists in the database
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM Login WHERE username = ?");
        preparedStatement.setString(1, UserName);

        // Execute the prepared statement and get the result
        ResultSet resultSet = preparedStatement.executeQuery();

        // If the result set is not empty, then the username already exists in the database
        if (resultSet.next() && resultSet.getInt(1) > 0) {
            throw new SQLException("Username already exists");
        }

        // Close the prepared statement
        preparedStatement.close();

        // Create a prepared statement to check if the email address already exists in the database
        preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM Login WHERE Email = ?");
        preparedStatement.setString(1, Email);

        // Execute the prepared statement and get the result
        resultSet = preparedStatement.executeQuery();

        // If the result set is not empty, then the email address already exists in the database
        if (((ResultSet) resultSet).next() && resultSet.getInt(1) > 0) {
            throw new SQLException("Email address already exists");
        }

        // Close the prepared statement
        preparedStatement.close();
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
