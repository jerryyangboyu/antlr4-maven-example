package org.antlrfun.extract;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExtractInterfaceTool {

    public static void main(String[] args) throws IOException {
        JavaLexer lexer = new JavaLexer(CharStreams.fromStream(Files.newInputStream(Paths.get("demo.java.txt"))));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit();

        ParseTreeWalker walker = new ParseTreeWalker();
        ExtractInterfaceListener listener = new ExtractInterfaceListener(parser);
        walker.walk(listener, tree);
    }
}
