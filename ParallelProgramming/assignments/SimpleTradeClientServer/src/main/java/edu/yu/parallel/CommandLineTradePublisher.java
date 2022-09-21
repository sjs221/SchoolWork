package edu.yu.parallel;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandLineTradePublisher {

    public static void main(String[] args) {
        final String host = System.getProperty("host", "localhost");
        final int port = Integer.parseInt(System.getProperty("port", "8625"));

        System.out.format("Connecting to server on port %d ...", port);

        try (Socket socket = new Socket(host, port)) {
            System.out.println(" Done");

            Scanner keyboard = new Scanner(System.in);

            String input;
            while ((input = keyboard.nextLine()) != null) {
                System.out.println("TODO: Implement sending commands and reading response");
             }

        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
        catch (NoSuchElementException e)
        {
            System.out.println("No more command line input");
        }
    }
}
