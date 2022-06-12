module com.example.colorbox {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.colorbox to javafx.fxml;
    exports com.example.colorbox;
}