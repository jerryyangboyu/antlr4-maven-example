package org.antlrfun.graph;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.IOException;

public class CallGraph {
    public static void main(String[] args) {
        try (FileInputStream in = new FileInputStream("ackerman.cymbol.txt")) {
            CymbolLexer lexer = new CymbolLexer(CharStreams.fromStream(in));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CymbolParser parser = new CymbolParser(tokens);

            ParseTree tree = parser.file();
            ParseTreeWalker walker = new ParseTreeWalker();
            FunctionListener listener = new FunctionListener();
            walker.walk(listener, tree);

            System.out.println(listener.graph.toDOT());
            System.out.println(listener.graph.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

