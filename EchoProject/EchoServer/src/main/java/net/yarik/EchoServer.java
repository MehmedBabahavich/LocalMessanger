package net.yarik;

import java.io.*;
import java.net.*;

public class EchoServer {
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        System.out.println("start method is thread: " + Thread.currentThread().getName());
        serverSocket = new ServerSocket(port);
        while (true) {
            new Thread(new EchoClientHandler(serverSocket.accept())).start();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class EchoClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public EchoClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            System.out.println("client handler is running on thread: " + Thread.currentThread().getName());
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (".".equals(inputLine)) {
                        out.println("End of the message!");
                        break;
                    }
                    out.println(inputLine);
                }

                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("main method is running on thread: " + Thread.currentThread().getName());
        EchoServer server = new EchoServer();
        try {
            server.start(9999);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}