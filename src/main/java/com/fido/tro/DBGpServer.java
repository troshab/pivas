package com.fido.tro;

import com.fido.tro.vulnerabilities.Vulnerability;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DBGpServer implements Runnable {
    private Socket clientSocket;

    DBGpServer(Socket acceptedSocket, Map<String, HashMap<Integer, LinkedList<Vulnerability>>> potentialVulnerabilities) {
        this.clientSocket = acceptedSocket;
    }

    private String readPacket() {
        String line = "";
        try {
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
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
                System.err.println("Broken came from xdebug.");
            }

            isr.close();
        } catch (IOException ioe) {
            System.err.println("Error during reading packet: " + ioe.getMessage());
        }

        return line;
    }

    private Document parseXML(String answer) {
        Document document = null;
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(answer));
            document = docBuilder.parse(is);
            System.out.println("XML parsed");
        } catch (ParserConfigurationException | IOException | SAXException xmle) {
            System.err.println("Parse XML error: " + xmle.getMessage());
        }
        return document;
    }

    @Override
    public void run() {
        System.out.println("Reading xdebug packet");
        String answer = readPacket();

        if (answer == null) {
            System.err.println("Error during xdebug packet reading - packet is null");
            closeSocket();
        }

        Document document = parseXML(answer);

        if (document == null) {
            System.err.println("Error during xdebug packet deXMLing - packet is null");
            closeSocket();
        }

        /*PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            out.close();*/
        closeSocket();
    }

    private void closeSocket() {
        try {
            clientSocket.close();

            if (Config.VERBOSE) {
                System.out.println("Connection closed.\n");
            }
        } catch (IOException ioe) {
            System.err.println("Error closing stream: " + ioe.getMessage());
        }
    }
}
