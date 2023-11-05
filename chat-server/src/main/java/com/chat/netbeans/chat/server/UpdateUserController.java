/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.netbeans.chat.server;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.SQLException;

public class UpdateUserController{
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private String username;
    private String password;
    private UserDao userDao;
    private Stage stage;
    private User user;

    public UpdateUserController() throws SQLException, ClassNotFoundException {
        userDao = new UserDao();
    }

    public void initData(String username, Stage stage) throws SQLException {
        user = userDao.getUserByUsername(username);
        this.username = username;
        this.password = user.getPassword();
        this.stage = stage;
        usernameField.setText(username);
        passwordField.setText(password);
    }

    @FXML
    public void updateUser() {
        String newUsername = usernameField.getText();
        String newPassword = passwordField.getText();

        if (newUsername.isEmpty() && newPassword.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Missing Information", "Please enter either a new username or password.");
        } else {
            try {
                boolean usernameExists = userDao.usernameExists(newUsername);
                if (usernameExists && !newUsername.equals(username)) {
                    showAlert(AlertType.ERROR, "Error", "Username Already Exists", "The username already exists. Please choose a different username.");
                } else {
                    String updatedUsername = (newUsername.isEmpty()) ? username : newUsername;
                    String updatedPassword = (newPassword.isEmpty()) ? password : newPassword;

                    userDao.updateUser(username, updatedUsername, updatedPassword);
                    showAlert(AlertType.INFORMATION, "Success", "User Updated",
                            "The username and password have been updated successfully.");
                    // Close the current window
                    closeWindow();
                }
            } catch (SQLException e) {
                showAlert(AlertType.ERROR, "Error", "Database Error", "An error occurred while updating the user.");
            }
        }
    }

    private void showAlert(AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
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
