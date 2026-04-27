module org.example {

    requires javafx.controls;
    requires javafx.fxml;

    opens controllers to javafx.fxml;
    opens org.example to javafx.fxml;

    exports org.example;
}