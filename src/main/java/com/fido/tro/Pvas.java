package com.fido.tro;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;

public class Pvas {
    public static void main(String[] args) {
        try {
            ServerSocket serverConnect =  new ServerSocket(Config.PORT);
            while(true) {
                DBGpServer myServer = new DBGpServer(serverConnect.accept());
                if (Config.verbose) {
                    System.out.println("Connection opened. (" + new Date() + ")");
                }

                new Thread(myServer).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
