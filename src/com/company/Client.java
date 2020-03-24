package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private String IP;
    private int PORT;
    private PrintWriter out;
    private BufferedReader in;
    private onResponse onResponseListener;
    private boolean connectionFailed = false;

    public Client(String IP, int PORT) {
        this.IP = IP;
        this.PORT = PORT;
    }

    public void setOnResponseListener(onResponse onResponseListener) {
        this.onResponseListener = onResponseListener;
    }

    public void start() {
        try {
            Socket clientSocket = new Socket(IP, PORT);
            if (clientSocket.isConnected()) {
                connectionFailed = false;
            }
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e) {
            System.out.println("Retrying every 5 seconds...");
            try {
                connectionFailed = true;
                Thread.sleep(5000);
                start();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendMessage(String msg) throws IOException {
        try {
            out.println(msg);
            String resp = in.readLine();
            onResponseListener.onResponse(resp);
        } catch (Exception e) {
            System.out.println("Connection lost !");
            start();
        }
    }
}
