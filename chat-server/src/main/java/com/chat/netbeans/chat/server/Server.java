/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.netbeans.chat.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
    public static ServerSocket socket;
    // Save PrintWriter objects which used to send messages to the clients
    static ArrayList<PrintWriter> printWriters = new ArrayList();

    public static void main(String[] args) {
        try {
            socket = new ServerSocket(6968);
            while(true) {
                System.out.println("Waiting for clients...");
                // Accept client connection
                Socket client = socket.accept();
                System.out.println("A client connected!");
                // Create a thread for the clients to handle their requests
                ClientHandler clientThread = new ClientHandler(client);
                // Start the thread
                clientThread.start();
            }
        } catch (IOException e) {
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}