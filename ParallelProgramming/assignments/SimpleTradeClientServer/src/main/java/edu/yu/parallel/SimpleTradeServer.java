package edu.yu.parallel;


import java.io.IOException;
import java.net.ServerSocket;

public class SimpleTradeServer {

    public static void main(String [] args) throws IOException {
        final int port = Integer.parseInt(System.getProperty("port", "8625"));
        System.out.format("Starting server on port %d ...", port);

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println(" Done");

            System.out.println(" Ok, nothing implemented yet, so just exiting.");
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        }

    }
}
