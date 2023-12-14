module com.example.lsfr_gui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lsfr_gui to javafx.fxml;
    exports com.example.lsfr_gui;
}