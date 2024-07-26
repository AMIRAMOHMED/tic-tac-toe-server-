module com.example.tic_tac_toeserver {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;


    opens com.example.tic_tac_toeserver to javafx.fxml;
    exports com.example.tic_tac_toeserver;
}