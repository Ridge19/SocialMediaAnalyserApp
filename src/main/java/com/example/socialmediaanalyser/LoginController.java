package com.example.socialmediaanalyser;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

// login page controller (checks username and password and if it matches with database).
public class LoginController implements Initializable {
    public AnchorPane LoginWindow;
    @FXML
    public Label StatusLabel;
    @FXML
    public Label UserLabel;
    @FXML
    public Label LoginLabel;
    @FXML
    public Label UsernameLabel;
    @FXML
    public Label PasswordLabel;
    @FXML
    public TextField UsernameField;
    @FXML
    public TextField PasswordField;
    @FXML
    public Label AccountLabel;
    @FXML
    public Button SignInButton;
    @FXML
    public Button CreateAccountButton;
    @FXML
    public Button QuitButton;

    private Connection connection;
    private String Password;
    private String UserName;

    private Scene scene;

    @FXML
    private CheckBox showPasswordCheckBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Database connected");
        UserLabel = new Label();
    }

    public LoginController() throws SQLException {
    }

    public LoginModel LoginModel = new LoginModel();

    public void SignIn(ActionEvent event) throws SQLException, IOException {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:DataHub.db");

            if (UsernameField.getText().isEmpty() || PasswordField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Empty fields");
                alert.setHeaderText("Please fill in the username and password fields.");
                alert.setContentText("Cannot sign in with empty fields.");

                alert.showAndWait();
                UsernameField.clear();
                PasswordField.clear();
                return;
            }

            if (com.example.socialmediaanalyser.LoginModel.isLogin(UsernameField.getText(), PasswordField.getText(), connection)) {
                System.out.println("Signing in");
                com.example.socialmediaanalyser.LoginModel.setLoggedInUser(UsernameField.getText());
                System.out.println("user name is: " + UsernameField.getText());

                // Get the loggedInUser variable
                String loggedInUser = String.valueOf(com.example.socialmediaanalyser.LoginModel.loggedInUser());

                // Set the text of the UserLabel label to the loggedInUser variable
                UserLabel.setText(loggedInUser);

                if (com.example.socialmediaanalyser.LoginModel.isLoggedIn()) {
                    System.out.println("You are now logged in!");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login successful");
                    alert.setHeaderText("welcome " + loggedInUser + "!");
                    alert.setContentText("You have access to DataHub features!");

                    Platform.runLater(() -> {
                        alert.show();
                    });

                    // Get the MainController instance.
                    MainController mainController = new MainController();
                    // Load the Main-Page.fxml file.
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main-Page.fxml")));
                    // Set the AccountController instance as the controller for the Main-Page.fxml file.
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
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Wrong Credentials");
                alert.setHeaderText("ERROR SIGNING IN");
                alert.setContentText("Incorrect Username or Password! enter again!");
                UsernameField.clear();
                PasswordField.clear();

                if (alert.showAndWait().get() == ButtonType.OK) {
                    Stage stage = (Stage) LoginWindow.getScene().getWindow();
                    System.out.println("login");
                } else {
                    //dont do anything
                }
            }
        } catch (SQLException e) {
            StatusLabel.setText("fields cannot be empty! try again!");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }


    public void CreateAccount(ActionEvent event) throws IOException, SQLException {
        System.out.println("Creating account");

        // Get the AccountController instance.
        AccountController accountController = new AccountController();

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AccountPage.fxml")));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountPage.fxml"));
        loader.setController(accountController);

        Scene scene = new Scene(root);

        // Get the stage from the event.

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the scene of the stage to the new scene.
        stage.setScene(scene);

        // Show the stage.
        stage.show();
    }

    public void Quit(ActionEvent event) throws SQLException, IOException {
        System.out.println("Quitting app");

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Quit");
        alert.setHeaderText("You're about to close the application!");
        alert.setContentText("Are you sure you want to close the application?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage stage = (Stage) LoginWindow.getScene().getWindow();
            System.out.println("you successfully logged out!");
            stage.close();
        } else {
            Stage stage = (Stage) LoginWindow.getScene().getWindow();
        }
        javafx.application.Platform.exit();
    }
}
