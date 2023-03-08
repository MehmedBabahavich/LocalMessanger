package org.example;

import java.io.*;
import java.net.*;
import java.sql.SQLOutput;

public class EchoClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws Exception{
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws Exception {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() throws Exception {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) {
        EchoClient client = new EchoClient();
        try {
            client.startConnection("127.0.0.1", 9999);

            String message = "Message test!";

            System.out.println("------------------------------");
            System.out.println("Sending message " + message + " and waiting for response...");
            System.out.println("------------------------------");

            String response = client.sendMessage(message);

            System.out.println("------------------------------");
            System.out.println("Response from the server: " + response);
            System.out.println("------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}