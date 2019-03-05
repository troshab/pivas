package com.fido.tro;

import com.fido.tro.maps.PHPFunctionsMap;
import com.fido.tro.maps.VulnerabilitiesMap;
import com.fido.tro.vulnerabilities.Vulnerability;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Pivas {
    static ServerSocket serverConnect;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (serverConnect != null) {
                try {
                    serverConnect.close();
                    System.err.println("Server closed");
                } catch (IOException e) {
                    System.err.println("Error during closing server: " + e.getMessage());
                }
            }
        }));

        PHPFunctionsMap phpFunctionsMap = new PHPFunctionsMap();
        VulnerabilitiesMap vulnerabilitiesMap = new VulnerabilitiesMap();

        Scanner scanner = new Scanner();
        Map<String, HashMap<Integer, LinkedList<Vulnerability>>> potentialVulnerabilities = scanner.scan(phpFunctionsMap, vulnerabilitiesMap);
        try {
            System.out.println("Running server");
            serverConnect =  new ServerSocket(Config.PORT);
            while(true) {
                DBGpServer myServer = new DBGpServer(serverConnect.accept(), potentialVulnerabilities);
                if (Config.VERBOSE) {
                    System.out.println("Connection opened. (" + new Date() + ")");
                }

                new Thread(myServer).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
