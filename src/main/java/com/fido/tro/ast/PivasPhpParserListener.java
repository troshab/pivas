package com.fido.tro.ast;

import com.fido.tro.maps.PHPFunctionsMap;
import com.fido.tro.maps.VulnerabilitiesMap;
import com.fido.tro.vulnerabilities.Vulnerability;

import java.util.HashMap;
import java.util.LinkedList;

public class PivasPhpParserListener extends PhpParserBaseListener {
    private String filepath;

    public PivasPhpParserListener(String filepath, PHPFunctionsMap phpFunctionsMap, VulnerabilitiesMap vulnerabilitiesMap, HashMap<String, HashMap<Integer, LinkedList<Vulnerability>>> vulnerabilityFunctions) {
        this.filepath = filepath;
    }

    /*public void enterTopStatement(PhpParser.TopStatementContext ctx) {
        System.out.flush();
        System.out.println(filepath + " - " + ctx.getStart().getLine() + ":" + ctx.getText());
    }

    public void exitTopStatement(PhpParser.TopStatementContext ctx){
        System.out.flush();
        System.out.println("exit top: " + ctx.getText());
    }*/

    public void enterPhpBlock(PhpParser.PhpBlockContext ctx) {
        System.out.println("enterPhpBlock: " + ctx.getText());
    }

    public void exitPhpBlock(PhpParser.PhpBlockContext ctx) {
        System.out.println("exitPhpBlock: " + ctx.getText());
    }

    public void enterHtmlElement(PhpParser.HtmlElementContext ctx) {
        System.out.println("enterHtmlElement: " + ctx.getText());
    }

    public void exitHtmlElement(PhpParser.HtmlElementContext ctx) {
        System.out.println("exitHtmlElement: " + ctx.getText());
    }

    public void enterScriptTextPart(PhpParser.ScriptTextPartContext ctx) {
        System.out.println("enterScriptTextPart: " + ctx.getText());
    }

    public void exitScriptTextPart(PhpParser.ScriptTextPartContext ctx) {
        System.out.println("exitScriptTextPart: " + ctx.getText());
    }

    public void enterEchoStatement(PhpParser.ScriptTextPartContext ctx) {
        System.out.println("enterEchoStatement: " + ctx.getText());
    }

    public void exitEchoStatement(PhpParser.ScriptTextPartContext ctx) {
        System.out.println("exitEchoStatement: " + ctx.getText());
    }
}
