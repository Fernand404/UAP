module com.example.uapfixed {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.uapfixed to javafx.fxml;
    exports com.example.uapfixed;
}