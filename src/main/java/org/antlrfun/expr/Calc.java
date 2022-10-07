package org.antlrfun.expr;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;

public class Calc {
    public static void main(String[] args) throws Exception {
        FileInputStream input = new FileInputStream("expr.txt");
        LabeledExprLexer lexer = new LabeledExprLexer(CharStreams.fromStream(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LabeledExprParser parser = new LabeledExprParser(tokens);
        ParseTree tree = parser.prog(); // begin parsing at prog rule

        EvalVisitor eval = new EvalVisitor();
        eval.visit(tree);
    }
}
