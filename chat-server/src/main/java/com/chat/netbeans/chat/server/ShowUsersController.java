/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.netbeans.chat.server;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShowUsersController implements Initializable {
    @FXML
    private ListView<String> availableUsersListView;
    @FXML
    private ListView<String> busyUsersListView;

    private UserDao userDao = new UserDao();

    public ShowUsersController() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void initialize(URL x, ResourceBundle y)
    {
        try {
            retrieveUsersFromDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void retrieveUsersFromDatabase() throws SQLException {
        // Get available users
        availableUsersListView.setItems(userDao.getAvailableUsers());
        // Get busy users
        busyUsersListView.setItems(userDao.getBusyUsers());
    }
}
