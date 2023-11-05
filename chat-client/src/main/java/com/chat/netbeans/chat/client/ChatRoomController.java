/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.netbeans.chat.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import java.io.*;
import java.net.Socket;
import javafx.stage.Stage;


public class ChatRoomController extends Thread {
    @FXML
    private TextArea messageTextArea;
    @FXML
    private TextField messageField;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private Label welcomeLabel;

    Socket socket;
    BufferedReader in;
    PrintWriter out;
    private String username;
    private String status;
    private Stage stage;

    public void initData(Socket socket, String username, String status, BufferedReader in, PrintWriter out, Stage stage) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.username = username;
        this.status = status;
        this.stage = stage;

        welcomeLabel.setText("Welcome "+ this.username +"!");

        statusComboBox.setValue(status);

        // Listen for Enter key press in the message field to send messages
        messageField.setOnKeyPressed(this::handleMessageFieldKeyPress);

        messageField.requestFocus();

        // Create a new Thread to listen for messages from the server
        new Thread(this::listenForMessages, "ListenForMessages").start();

        // Set the close event handler for the stage
        stage.setOnCloseRequest(event -> handleCloseEvent());
    }

    private void listenForMessages() {
        try {
            while (true) {
                String msg = in.readLine();
                if(msg.contains("STATUS_CHANGE:"))
                {
                    continue;
                }
                messageTextArea.appendText(msg + "\n");
            }
        } catch (IOException e) {
        }
    }

    public void send() {
        String msg = messageField.getText();
        if (msg == null || msg.trim().isEmpty()) {
            return; // Ignore null or empty messages
        }
        out.println(msg);
        messageField.setText("");
        if (msg.equalsIgnoreCase("logout")) {
            handleCloseEvent();
        }
    }

    public void handleSendEvent() {
        send();
    }

    private void handleMessageFieldKeyPress(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            send();
        }
    }

    @FXML
    public void handleStatusChange(ActionEvent event) {
        String newStatus = statusComboBox.getValue();
        if (!newStatus.equals(status)) {
            out.println("STATUS_CHANGE:" + username + ":" + newStatus);
            status = newStatus;
        }
    }

    @FXML
    private void handleSaveLogChat() {
        String content = messageTextArea.getText();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Chat Log");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(content);
                showConfirmationAlert("Chat Log Saved", "The chat log has been saved successfully.");
            } catch (IOException e) {
            }
        }
    }

    private void showConfirmationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void changeStatusToBusy() {
        if("Busy".equals(status))
        {
            return;
        } else {
            out.println("STATUS_CHANGE:" + username + ":Busy");
        }
    }

    public void handleCloseEvent() {
        changeStatusToBusy(); // Change status to "busy" on window close
        System.exit(0);
    }
}
