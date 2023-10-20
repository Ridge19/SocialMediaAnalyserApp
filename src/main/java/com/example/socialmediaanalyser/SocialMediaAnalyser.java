package com.example.socialmediaanalyser;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

// main class
public class SocialMediaAnalyser extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        CreateTableLogin createTableLogin = new CreateTableLogin();
        createTableLogin.createLogin();
        CreateTablePosts createTablePosts = new CreateTablePosts();
        createTablePosts.createPosts();
        InsertPosts insertPosts = new InsertPosts();
        insertPosts.insertPosts();

        FXMLLoader fxmlLoader = new FXMLLoader(SocialMediaAnalyser.class.getResource("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 450);
        stage.setTitle("Social Media Manager - Login");
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) throws SQLException {
        launch();

    }
}