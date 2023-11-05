/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.netbeans.chat.server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.SQLException;

public class CreateUserController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private UserDao userDao;

    public CreateUserController() throws SQLException, ClassNotFoundException {
        userDao = new UserDao();
    }

    @FXML
    public void signup(ActionEvent actionEvent) throws SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Missing Information", "Please enter a username and password.");
        }else if (userDao.usernameExists(username)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Username Exists", "Username already exists. Please choose a different username.");
        } else {
            userDao.createUser(username, password);
            showAlert(Alert.AlertType.INFORMATION, "Success", "User Created", "User Created!");

            // Close the window
            closeWindow();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
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
}
