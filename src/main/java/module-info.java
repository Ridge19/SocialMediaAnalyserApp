module com.example.socialmediaanalyser {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.socialmediaanalyser to javafx.fxml;
    exports com.example.socialmediaanalyser;
}