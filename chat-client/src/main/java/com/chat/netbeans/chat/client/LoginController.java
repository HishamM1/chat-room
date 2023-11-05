/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.netbeans.chat.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button cancelButton;
    static Socket socket;
    static BufferedReader in;
    static PrintWriter out;
    String username;
    String status;

    public LoginController(){
        SocketHandler.establishConnection();
        socket = SocketHandler.getSocket();
        in = SocketHandler.getReader();
        out = SocketHandler.getWriter();
    }



    @FXML
    private void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
            // Send login request to the server
            out.println("LOGIN:" + username + ":" + password);
            // Getting a response
            String response = in.readLine();
            if (response.startsWith("LOGIN_SUCCESS")) {
                String[] parts = response.split(":");
                this.username = parts[1];
                this.status = parts[2];

                closeWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("chat-room.fxml"));
                Parent root = loader.load();
                Stage chatStage = new Stage();
                chatStage.setTitle("Chat Room");
                Scene scene = new Scene(root);
                ChatRoomController controller = loader.getController();
                // Send data to the chat room
                controller.initData(socket, username, status, in, out, chatStage);
                chatStage.setScene(scene);
                chatStage.show();

            } else if (response.equals("LOGIN_ERROR")) {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid Username or Password");
            }

        usernameField.clear();
        passwordField.clear();
    }


    @FXML
    private void handleSignup() throws IOException {
        // open signup window
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sign-up.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Sign Up!");
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Get the controller instance from the loader
        SignUpController signUpController = loader.getController();

        // Pass the socket, in, and out variables to the controller
        signUpController.initData(socket, in, out);

        // Show the new stage
        stage.show();

        // Close the login window
        closeWindow();
    }

    @FXML
    private void handleCancel() {
        // Close the window or perform any necessary cleanup
        cancelButton.getScene().getWindow().hide();
    }

    private void closeWindow() {
        // Get the window associated with any of the Node elements in the scene
        Window window = usernameField.getScene().getWindow();

        // Check if the window is a Stage and close it
        if (window instanceof Stage) {
            Stage stage = (Stage) window;
            stage.close();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}