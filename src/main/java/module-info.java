module com.example.socialmediaanalyser {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.socialmediaanalyser to javafx.fxml;
    exports com.example.socialmediaanalyser;
}