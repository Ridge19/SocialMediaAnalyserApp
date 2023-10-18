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

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.socialmediaanalyser.LoginModel.*;

public class VIPController implements Initializable {
    @FXML
    public AnchorPane VIPpage;
    @FXML
    public Label VIPLabel;
    @FXML
    public Label FeeLabel;
    @FXML
    public Button NoButton;
    @FXML
    public Button YesButton;


    static Connection connection;

    public VIPController() throws SQLException {
    }


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
        private static final String SELECT_VIP_STATUS_SQL = "SELECT VIP FROM Login WHERE UserName = ?";

//        public static String isVIP(String username) throws SQLException {
//            PreparedStatement statement = connection.prepareStatement(SELECT_VIP_STATUS_SQL);
//            statement.setString(1, username);
//            ResultSet resultSet = statement.executeQuery();
//
//            if (resultSet.next()) {
//                int vipStatus = resultSet.getInt("VIP");
//                return vipStatus == "yes";
//            } else {
//                return false;
//            }
//        }

    public LoginModel LoginModel = new LoginModel();

    public void YesVIP(ActionEvent event) throws IOException, SQLException {
        System.out.println("Signing up for VIP");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("VIP upgrade - SIGNING OUT");
        alert.setHeaderText("Thanks for purchasing VIP!");
        alert.setContentText("Sign out and sign in to gain access to VIP features");

        // Get the user's response to the alert popup
        ButtonType response = alert.showAndWait().orElse(ButtonType.CANCEL);

        // If the user clicked Ok, sign them up for VIP
        if (response == ButtonType.OK) {
            // Check if the user is logged in
            String UserName = new String();
            String Password = new String();
            if (isLogin(UserName, Password)) {
                // Display an error message to the user
                System.out.println("You must be logged in to sign up for VIP.");
                return;
            } else if (!isLogin(UserName, Password)) {
                System.out.println("You are logged in");

                // Assign the VIP functionality to the user
                String user = String.valueOf(loggedInUser(UserName));

                // **New code:**
                // Set the logged in user's VIP status to 1
                LoginModel.setVIP(UserName, true);

                // Update the user's VIP status in the database
                // ...
                // Get the UserName
                UserName = String.valueOf(loggedInUser(UserName));

                // Create a prepared statement to update the user's VIP status
                PreparedStatement statement = connection.prepareStatement("UPDATE Login SET VIP = 1 WHERE UserName = ?");

                // Set the parameters of the prepared statement
                statement.setString(1, UserName);

                // Execute the prepared statement
                statement.executeUpdate();

                // Close the prepared statement and the connection
                statement.close();
                connection.close();

                // Update the VIP label based on the user's VIP status
                System.out.println("You are a VIP!");
            }

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
        }
    }

    public static boolean isVIP(String UserName) throws SQLException {
        // Create a prepared statement to get the user's VIP status.
        PreparedStatement statement = connection.prepareStatement("SELECT VIP FROM Login WHERE username = ?");

        // Set the parameter of the prepared statement.
        statement.setString(1, UserName);

        // Execute the prepared statement and get the results.
        ResultSet results = statement.executeQuery();

        // Check if the results are empty.
        if (!results.next()) {
            // If the results are empty, the user does not exist in the database.
            return false;
        }

        // Get the user's VIP status from the results.
        boolean isVIP = results.getBoolean("VIP");

        // Close the prepared statement and the connection.
        statement.close();
        connection.close();

        // Return the user's VIP status.
        return isVIP;
    }

//    public void YesVIP(ActionEvent event) throws IOException, SQLException {
//        // Check if the connection is null.
//        if (connection == null) {
//            // If the connection is null, throw an exception.
//            throw new SQLException("Connection is null.");
//        }
//
//        System.out.println("Signing up for VIP");
//
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("VIP upgrade - SIGNING OUT");
//        alert.setHeaderText("Thanks for purchasing VIP!");
//        alert.setContentText("Sign out and sign in to gain access to VIP features");
//
//        // Get the user's response to the alert popup
//        ButtonType response = alert.showAndWait().orElse(ButtonType.CANCEL);
//
//        // If the user clicked Ok, sign them up for VIP
//        if (response == ButtonType.OK) {
//            // Check if the user is logged in
//            String UserName = new String();
//            String Password = new String();
//            if (LoginModel.isLogin(UserName, Password)) {
//                // Display an error message to the user
//                System.out.println("You must be logged in to sign up for VIP.");
//                return;
//            } else if (!LoginModel.isLogin(UserName, Password)) {
//                System.out.println("Test");
//                System.out.println("You are VIP");
//
//                // Assign the VIP functionality to the user
//                String user = String.valueOf(loggedInUser(UserName));
//
//                // **New code:**
//                // Get the current logged-in user's VIP status
//                boolean isVIP = isVIP(UserName);
//
//                // Set the logged-in user's VIP status to the new value
//                LoginModel.setVIP(UserName, isVIP);
//
//                // Update the user's VIP status in the database
//                // ...
//                // Get the UserName
//                UserName = String.valueOf(loggedInUser(UserName));
//
//                // Create a prepared statement to update the user's VIP status
//                PreparedStatement statement = connection.prepareStatement("UPDATE Login SET VIP = 1 WHERE UserName = ?");
//
//                // Set the parameters of the prepared statement
//                statement.setBoolean(1, loggedInUser(UserName));
//
//                // Execute the prepared statement
//                statement.executeUpdate();
//
//                // Close the prepared statement and the connection
//                statement.close();
//                connection.close();
//            }
//        }
//    }


    public void NoVIP(ActionEvent event) throws IOException, SQLException {
        System.out.println("Cancelling VIP registration");

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
