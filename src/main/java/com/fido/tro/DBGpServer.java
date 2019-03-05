package com.fido.tro;

import java.io.*;
import java.net.Socket;

public class DBGpServer implements Runnable {
    private Socket clientSocket;

    DBGpServer(Socket acceptedSocket) {
        this.clientSocket = acceptedSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

            String userInput;
            while ((userInput = in.readLine()) != null) {
                System.out.println(userInput);
            }

            in.close();
            out.close();
        } catch (IOException ioe) {
            System.err.println("Server error: " + ioe.getMessage());
        } finally {
            try {
                clientSocket.close();

                if (Config.verbose) {
                    System.out.println("Connection closed.\n");
                }
            } catch (IOException ioe) {
                System.err.println("Error closing stream: " + ioe.getMessage());
            }
        }
    }
}
