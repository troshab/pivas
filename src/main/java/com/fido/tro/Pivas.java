package com.fido.tro;

import com.fido.tro.maps.GoFunctionsMap;
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

        GoFunctionsMap goFunctionsMap = new GoFunctionsMap();
        VulnerabilitiesMap vulnerabilitiesMap = new VulnerabilitiesMap();

        Scanner scanner = new Scanner();
        Map<String, HashMap<Integer, LinkedList<Vulnerability>>> potentialVulnerabilities = scanner.scan(goFunctionsMap, vulnerabilitiesMap);
    }
}
