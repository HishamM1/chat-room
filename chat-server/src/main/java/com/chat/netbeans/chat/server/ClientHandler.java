/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.netbeans.chat.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

class ClientHandler extends Thread {
    private BufferedReader in;
    private PrintWriter out;
    private UserDao userDao = new UserDao();
    private boolean isLoggedIn;

    private String username;

    private String status;

    public ClientHandler(Socket socket) throws SQLException, ClassNotFoundException {
        try {
            // Create a BufferedReader to read input from the client
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Create a PrintWriter to write output to the client
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        try {
            // Handle login and signup requests
            while (!isLoggedIn) {
                // read input from user
                String input = this.in.readLine();
                if (input == null) {
                    return;
                }
                // Input is will be like action:username:password
                // action is the request the user wants
                String[] parts = input.split(":");
                String action = parts[0];
                String username = (parts.length > 1) ? parts[1] : null;
                String password = (parts.length > 2) ? parts[2] : null;
                // if the action is log in
                if (action.startsWith("LOGIN")) {
                    if (username != null && password != null && userDao.authenticateUser(username, password)) {
                        User user = userDao.getUserByUsername(username);
                        this.username = username;
                        if (user.getStatus().equals("Busy")) {
                            // Update status to Available when a user logs in
                            userDao.updateUserStatus(username, "Available");
                            user = userDao.getUserByUsername(username);
                            this.status = "Available";
                        } else {
                            this.status = user.getStatus();
                        }
                        this.out.println("LOGIN_SUCCESS:" + user.getUsername() + ":" + user.getStatus());
                        for (PrintWriter writer : Server.printWriters) {
                            writer.println(this.username + " is now " + this.status);
                        }
                    Server.printWriters.add(this.out);
                    isLoggedIn = true;
                    } else {
                            this.out.println("LOGIN_ERROR");
                    }

                } else if (action.equals("SIGNUP")) {
                    if (username != null && password != null && !userDao.usernameExists(username)) {
                        userDao.createUser(username, password);
                        User user = userDao.getUserByUsername(username);
                        this.username = username;
                        this.status = user.getStatus();
            
                        this.out.println("SIGNUP_SUCCESS");
                        isLoggedIn = true;
                    } else {
                         this.out.println("SIGNUP_ERROR");
                            }
                    }
                }
            
            // Handle messages
            while (true) {
                String message = this.in.readLine();
                if (message == null) {
                    return;
                }
                // Handle status change message
                if(message.startsWith("STATUS_CHANGE:"))
                {
                    String[] parts = message.split(":");
                    this.status = parts[2];
                    // Update the status in the database
                    userDao.updateUserStatus(this.username, this.status);
                    // Send new user status to the all clients
                    for (PrintWriter writer : Server.printWriters) {
                        writer.println(this.username +" Is now " + this.status);
                    }

                }else {
                    // Send the messages to all users
                    for (PrintWriter writer : Server.printWriters) {
                        writer.println(this.username + ": " + message);
                    }
                }
            }
        } catch (IOException | SQLException e) {
            System.out.println(e);
        }
    }
}


