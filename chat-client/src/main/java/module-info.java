module com.chat.netbeans.chat.client {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.chat.netbeans.chat.client to javafx.fxml;
    exports com.chat.netbeans.chat.client;
}
