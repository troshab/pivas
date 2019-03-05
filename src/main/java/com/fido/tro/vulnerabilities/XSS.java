package com.fido.tro.vulnerabilities;

import java.util.LinkedList;
import java.util.List;

public class XSS implements Vulnerability {
    public String getTitle() {
        return "XSS";
    }

    @Override
    public String getDescription() {
        return "Cross-Site Scripting (XSS) enables attackers to inject client-side scripts into web pages viewed by other users.";
    }

    @Override
    public List<String> exploitableFunction() {
        List<String> functionsList = new LinkedList<>();

        functionsList.add("header");
        functionsList.add("echo");
        functionsList.add("print");

        return functionsList;
    }
}
