package com.example.socialmediaanalyser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.RejectedExecutionException;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Database connected");
        String loggedInUser = "";
    }

    public LoginController() throws SQLException {
    }

    public LoginModel loginModel = new LoginModel();
    public void SignIn(ActionEvent event) throws SQLException, IOException {
        System.out.println("Signing in");

        try {
            if (loginModel.isLogin(UsernameField.getText(), PasswordField.getText())) {
                StatusLabel.setText("Username and password is correct!");
                System.out.println("logged in");
                String loggedInUser = UsernameField.getText();
                System.out.println("Welcome " + loggedInUser + " User!");
//                UserLabel.setText(UsernameField.getText());

                UsernameField.setText("Welcome " + UsernameField.getText());


                //pass method to MainController
                // display Username in UserLabel


//                UserLabel.setText(UsernameField.getText());
                ((Node)event.getSource()).getScene().getWindow().hide();
                Stage PrimaryStage = new Stage();
                Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main-Page.fxml")));
                Scene scene = new Scene(root);
                UserLabel.setText(loggedInUser);
                PrimaryStage.setScene(scene);
                PrimaryStage.show();
            } else {
                StatusLabel.setText("username and password is not correct!");
                System.out.println("Cannot log in!");
            }

        } catch (SQLException e) {
            StatusLabel.setText("Fields cannot be empty! enter a username and password!");
            e.printStackTrace();
        }


    }

    public void CreateAccount(ActionEvent event) throws IOException, SQLException {
        System.out.println("Creating account");

        // Get the AccountController instance.
        AccountController accountController = new AccountController();

        // Load the AccountPage.fxml file.
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AccountPage.fxml")));

        // Set the AccountController instance as the controller for the AccountPage.fxml file.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AccountPage.fxml"));
        loader.setController(accountController);

        // Create a new scene with the AccountPage.fxml file as the root node.
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
        }
        javafx.application.Platform.exit();
    }


}
