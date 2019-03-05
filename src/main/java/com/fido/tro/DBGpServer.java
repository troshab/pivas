package com.fido.tro;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DBGpServer implements Runnable {
    private Socket clientSocket;

    DBGpServer(Socket acceptedSocket) {
        this.clientSocket = acceptedSocket;
    }

    @Override
    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

            List<String> lines = new ArrayList<>();
            String line = "";
            int intChar, charsCount;

            while ((intChar = isr.read()) > 0) {
                line += (char) intChar;
            }

            charsCount = Integer.valueOf(line);
            line = "";

            while ((intChar = isr.read()) > 0) {
                line += (char) intChar;
            }
            if(line.length() == charsCount) {
                System.out.println(line);
            } else {
                System.err.println("broken packet!!!");
            }

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
