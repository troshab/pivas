package com.fido.tro;

import com.fido.tro.antlr4.*;
import com.fido.tro.maps.PHPFunctionsMap;
import com.fido.tro.maps.VulnerabilitiesMap;
import com.fido.tro.vulnerabilities.Vulnerability;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class Scanner {
    HashMap<String, HashMap<Integer, LinkedList<Vulnerability>>> scan(PHPFunctionsMap phpFunctionsMap, VulnerabilitiesMap vulnerabilitiesMap) {
        HashMap<String, HashMap<Integer, LinkedList<Vulnerability>>> vulnerabilityFunctions = new HashMap<>();
        List<String> files = findFiles();
        for(String filepath : files) {
            try {
                AtomicInteger lineCounter = new AtomicInteger(1);

                CharStream in = CharStreams.fromFileName(filepath);

                PhpLexer lexer = new PhpLexer(in);
                CommonTokenStream tokens = new CommonTokenStream(lexer);

                PhpParser parser = new PhpParser(tokens);

                PhpParser.HtmlElementOrPhpBlockContext tree = parser.htmlElementOrPhpBlock();
                ParseTreeWalker walker = new ParseTreeWalker();
                PhpParserTreeListener printer = new PhpParserTreeListener(filepath);

                walker.DEFAULT.walk(printer, tree);
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
