/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.netbeans.chat.server;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Window;


import java.io.IOException;
import java.sql.SQLException;

public class EditUserController {
    @FXML
    private TextField usernameField;

    private UserDao userDao;
    private Stage stage;

    public EditUserController() throws SQLException, ClassNotFoundException {
        userDao = new UserDao();
    }

    @FXML
    public void editUser() throws SQLException, IOException, ClassNotFoundException {
        String username = usernameField.getText();

        if (username.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Missing Information", "Please enter a username.");
        } else {
            if (userDao.usernameExists(username)) {
                // Close the current window
                closeWindow();

                // Load the UpdateUser.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("update-user.fxml"));
                Parent root = loader.load();
                // Create a new stage for the update user screen
                Stage updateStage = new Stage();
                updateStage.setTitle("Edit User");
                // Create a new scene with the loaded root node
                Scene scene = new Scene(root);
                UpdateUserController controller = loader.getController();
                controller.initData(username, stage);

                // Set the scene on the stage
                updateStage.setScene(scene);

                // Show the update user screen
                updateStage.show();
            } else {
                showAlert(AlertType.ERROR, "Error", "User Not Found", "The specified username does not exist.");
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


