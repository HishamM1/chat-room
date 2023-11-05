/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.netbeans.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class SocketHandler {
    private static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;

    public static void establishConnection() {
        try {
            socket = new Socket("192.168.1.8", 6968);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Socket getSocket() {
        return socket;
    }

    public static BufferedReader getReader() {
        return in;
    }

    public static PrintWriter getWriter() {
        return out;
    }
}
