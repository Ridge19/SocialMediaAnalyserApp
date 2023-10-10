module com.example.socialmediaanalyser {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires junit;
    requires org.junit.jupiter.api;

    opens com.example.socialmediaanalyser to javafx.fxml;
    exports com.example.socialmediaanalyser;
}