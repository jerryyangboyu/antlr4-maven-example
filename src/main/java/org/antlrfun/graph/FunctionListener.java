package org.antlrfun.graph;

import org.antlrfun.CymbolBaseListener;
import org.antlrfun.CymbolParser;

public class FunctionListener extends CymbolBaseListener {
    Graph graph = new Graph();
    String currentFunctionName;

    @Override
    public void enterFunctionDecl(CymbolParser.FunctionDeclContext ctx) {
        currentFunctionName = ctx.ID().getText();
        graph.addNode(currentFunctionName);
    }

    @Override
    public void exitCall(CymbolParser.CallContext ctx) {
        String callee = ctx.ID().getText();
        graph.addEdge(currentFunctionName, callee);
    }
}
