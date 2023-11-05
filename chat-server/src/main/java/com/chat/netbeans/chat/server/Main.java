/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.netbeans.chat.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
@Override
public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Server.class.getResource("server-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Admin Options");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
                System.exit(0);
        });
        stage.show();
        }

public static void main(String[]args){
        launch();
        }
}
