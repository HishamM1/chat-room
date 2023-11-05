/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.netbeans.chat.server;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.SQLException;
import java.util.Optional;

public class DeleteUserController {
    @FXML
    private TextField usernameField;

    private UserDao userDao;

    public DeleteUserController() throws SQLException, ClassNotFoundException {
        userDao = new UserDao();
    }
    public void deleteUser() throws SQLException {
        String username = usernameField.getText();

        if (username.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Missing Information", "Please enter a username.");
        } else {
            if (userDao.usernameExists(username)) {
                // Prompt user for confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Delete User");
                alert.setContentText("Are you sure you want to delete this user?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // User confirmed deletion
                    userDao.deleteUser(username);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "User Deleted", "The user has been deleted.");
                    // Close the window
                    closeWindow();
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "User Not Found", "The specified username does not exist.");
            }
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
