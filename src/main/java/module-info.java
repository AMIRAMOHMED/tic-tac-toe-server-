module com.example.tic_tac_toeserver {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;
    requires mysql.connector.j;
    requires jdk.httpserver;


    opens com.example.tic_tac_toeserver to javafx.fxml;
    exports com.example.tic_tac_toeserver;
}