package com.fido.tro;

import com.fido.tro.ast.PhpLexer;
import com.fido.tro.ast.PhpParser;
import com.fido.tro.ast.PhpParserListenerAll;
import com.fido.tro.ast.PivasPhpParserListener;
import com.fido.tro.maps.PHPFunctionsMap;
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
    HashMap<String, HashMap<Integer, LinkedList<Vulnerability>>> scan(PHPFunctionsMap phpFunctionsMap, VulnerabilitiesMap vulnerabilitiesMap) {
        HashMap<String, HashMap<Integer, LinkedList<Vulnerability>>> vulnerabilityFunctions = new HashMap<>();
        List<String> files = findFiles();
        for(String filepath : files) {
            try {
                CharStream in = CharStreams.fromFileName(filepath);
                PhpLexer lexer = new PhpLexer(in);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                PhpParser parser = new PhpParser(tokens);
                PhpParserListenerAll printer = new PhpParserListenerAll();//(filepath, phpFunctionsMap, vulnerabilitiesMap, vulnerabilityFunctions);

                ParseTreeWalker walker = new ParseTreeWalker();
                System.out.flush();
                walker.walk(printer, parser.htmlElementOrPhpBlock());
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
