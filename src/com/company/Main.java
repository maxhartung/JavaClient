package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Client client = new Client("127.0.0.1", 12125);
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;
        client.setOnResponseListener(new onResponse() {
            @Override
            public void onResponse(String response) {
                System.out.println("Server: " +response);
            }
        });
        client.start();

        while(!stop){
            System.out.print("Enter a message to send: ");
            String message = scanner.next();
            client.sendMessage(message);
            if(message.contains("stop")){
                stop = true;
            }
        }
    }
}
