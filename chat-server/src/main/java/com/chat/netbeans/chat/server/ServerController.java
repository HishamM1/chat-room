/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.netbeans.chat.server;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;




public class ServerController{

    @FXML
    protected void ShowAllUsers() {

        try {
            // Load the AllUsers.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("all-users.fxml"));
            // Create a new stage
            Stage stage = new Stage();
            stage.setTitle("All Users");
            // Create a new scene with the loaded root node
            Scene scene = new Scene(root);
            // Set the scene on the stage
            stage.setScene(scene);
            // Show the new stage
            stage.show();
        } catch (IOException e) {
        }
    }

    @FXML
    protected void onCreateUser() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("create-user.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Create User");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
        }
    }
    @FXML
    protected void onDeleteUser(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("delete-user.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Delete User");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
        }
    }
    @FXML
    protected void onEditUser(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("edit-user.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Edit User");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
        }
    }
}