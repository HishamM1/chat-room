/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.netbeans.chat.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SignUpController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public void initData(Socket socket, BufferedReader in, PrintWriter out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
    }

    @FXML
    public void handleSignup(ActionEvent actionEvent) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        out.println("SIGNUP:" + username + ":" + password);

        String response = in.readLine();

        if(response.startsWith("SIGNUP_SUCCESS"))
        {
            // return to login
            showAlert(Alert.AlertType.INFORMATION, "Signup Success", "Account created successfully.");
            closeWindow();
            openLoginWindow();

        } else {
            // invalid username of password
            showAlert(Alert.AlertType.ERROR, "Signup Failed", "Invalid username or password.");
            passwordField.clear();
        }
    }
    @FXML
    public void handleCancel(ActionEvent actionEvent) throws IOException {
        closeWindow();
        openLoginWindow();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }

    private void openLoginWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("log-in.fxml"));
        Parent root = loader.load();
        Stage loginStage = new Stage();
        loginStage.setTitle("Log in");
        loginStage.setScene(new Scene(root));
        loginStage.show();
    }
}