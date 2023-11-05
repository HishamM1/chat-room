module com.chat.netbeans.chat.server {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.chat.netbeans.chat.server to javafx.fxml;
    exports com.chat.netbeans.chat.server;
}
