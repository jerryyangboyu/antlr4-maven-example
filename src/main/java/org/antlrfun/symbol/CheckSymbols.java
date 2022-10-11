package org.antlrfun.symbol;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlrfun.CymbolLexer;
import org.antlrfun.CymbolParser;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CheckSymbols {
    public static void main(String[] args) {
        try (InputStream in = Files.newInputStream(Paths.get("vars.cymbol.txt"))) {
            CymbolLexer lexer = new CymbolLexer(CharStreams.fromStream(in));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CymbolParser parser = new CymbolParser(tokens);
            ParseTree tree = parser.file();
            ParseTreeWalker walker = new ParseTreeWalker();

            // first pass: define symbols
            DefPhase defPhase = new DefPhase();
            walker.walk(defPhase, tree);

            // second pass: use symbols
            RefPhase refPhase = new RefPhase(defPhase.globals, defPhase.scopes);
            walker.walk(refPhase, tree);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
