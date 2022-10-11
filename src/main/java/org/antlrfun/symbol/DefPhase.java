package org.antlrfun.symbol;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlrfun.CymbolBaseListener;
import org.antlrfun.CymbolParser;

public class DefPhase extends CymbolBaseListener {
    ParseTreeProperty<Scope> scopes = new ParseTreeProperty<>();
    GlobalScope globals;
    private Scope currentScope;

    @Override
    public void enterFile(CymbolParser.FileContext ctx) {
        globals = new GlobalScope(null);
        currentScope = globals;
    }

    @Override
    public void exitFile(CymbolParser.FileContext ctx) {
        System.out.printf("globals: %s\n", globals);
    }

    @Override
    public void enterFunctionDecl(CymbolParser.FunctionDeclContext ctx) {
        String funcName = ctx.ID().getText();
        Symbol.Type funcType = Symbol.getType(ctx.type().start.getType());

        // pass parent scope to function
        // so that we can trace back parent scope variables
        FunctionSymbol funcSymbol = new FunctionSymbol(funcName, funcType, currentScope);
        currentScope.define(funcSymbol);

        // function is both a symbol and a scope
        currentScope = funcSymbol;
        scopes.put(ctx, currentScope);
    }

    @Override
    public void exitFunctionDecl(CymbolParser.FunctionDeclContext ctx) {
        System.out.println(currentScope);
        currentScope = currentScope.getEnclosingScope();
    }

    @Override
    public void enterBlock(CymbolParser.BlockContext ctx) {
        currentScope = new LocalScope(currentScope);
        scopes.put(ctx, currentScope);
    }

    @Override
    public void exitBlock(CymbolParser.BlockContext ctx) {
        System.out.println(currentScope);
        currentScope = currentScope.getEnclosingScope();
    }

    @Override
    public void exitFormalParameter(CymbolParser.FormalParameterContext ctx) {
        String varName = ctx.ID().getText();
        Symbol.Type varType = Symbol.getType(ctx.type().start.getType());
        currentScope.define(new VariableSymbol(varName, varType));
    }

    @Override
    public void exitVarDecl(CymbolParser.VarDeclContext ctx) {
        String varName = ctx.ID().getText();
        Symbol.Type varType = Symbol.getType(ctx.type().start.getType());
        currentScope.define(new VariableSymbol(varName, varType));
    }
}
