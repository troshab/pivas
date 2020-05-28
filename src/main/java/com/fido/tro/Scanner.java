package com.fido.tro;

import com.fido.tro.ast.GoLexer;
import com.fido.tro.ast.GoParser;
import com.fido.tro.ast.GoParserBaseListener;
import com.fido.tro.maps.GoFunctionsMap;
import com.fido.tro.maps.VulnerabilitiesMap;
import com.fido.tro.vulnerabilities.Vulnerability;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

class Scanner {
    HashMap<String, HashMap<Integer, LinkedList<Vulnerability>>> scan(GoFunctionsMap goFunctionsMap, VulnerabilitiesMap vulnerabilitiesMap) {
        HashMap<String, HashMap<Integer, LinkedList<Vulnerability>>> vulnerabilityFunctions = new HashMap<>();
        List<String> files = findFiles();
        for(String filepath : files) {
            try {
                CharStream in = CharStreams.fromFileName(filepath);
                GoLexer lexer = new GoLexer(in);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                GoParser parser = new GoParser(tokens);
                GoParserBaseListener printer = new GoParserBaseListener();//(filepath, phpFunctionsMap, vulnerabilitiesMap, vulnerabilityFunctions);

                ParseTreeWalker walker = new ParseTreeWalker();
                System.out.flush();
                walker.walk(printer, parser.block());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return vulnerabilityFunctions;
    }


    private List<String> findFiles() {
        List<String> files = new ArrayList<>();
        try {
            Files.walk(Paths.get(Config.DIRECTORY)).filter(Files::isRegularFile).filter(Files::isRegularFile).filter(filepath -> filepath.toAbsolutePath().toString().endsWith("php")).forEach(filepath -> files.add(filepath.toAbsolutePath().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }
}
