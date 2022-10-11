package org.antlrfun.symbol;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlrfun.CymbolBaseListener;
import org.antlrfun.CymbolParser;

public class RefPhase extends CymbolBaseListener {
    private final ParseTreeProperty<Scope> scopes;
    private final GlobalScope globals;
    private Scope currentScope;

    public RefPhase(GlobalScope globals, ParseTreeProperty<Scope> scopes) {
        this.globals = globals;
        this.scopes = scopes;
    }

    @Override
    public void enterFile(CymbolParser.FileContext ctx) {
        currentScope = globals;
    }

    @Override
    public void enterFunctionDecl(CymbolParser.FunctionDeclContext ctx) {
        currentScope = scopes.get(ctx);
    }

    @Override
    public void exitFunctionDecl(CymbolParser.FunctionDeclContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }

    @Override
    public void enterBlock(CymbolParser.BlockContext ctx) {
        currentScope = scopes.get(ctx);
    }

    @Override
    public void exitBlock(CymbolParser.BlockContext ctx) {
        currentScope = currentScope.getEnclosingScope();
    }

    @Override
    public void enterVar(CymbolParser.VarContext ctx) {
        String name = ctx.ID().getText();
        Symbol var = currentScope.resolve(name);

        if ( var==null ) {
            System.err.printf("%s: no such variable %s\n", ctx.ID().getSymbol(), name);
        }
        if ( var instanceof FunctionSymbol ) {
            System.err.printf("%s: %s is not a variable\n", ctx.ID().getSymbol(), name);
        }
    }

    @Override
    public void enterCall(CymbolParser.CallContext ctx) {
        String name = ctx.ID().getText();
        Symbol var = currentScope.resolve(name);

        if ( var==null ) {
            System.err.printf("%s: no such function %s\n", ctx.ID().getSymbol(), name);
        }
        if ( var instanceof VariableSymbol ) {
            System.err.printf("%s: %s is not a function\n", ctx.ID().getSymbol(), name);
        }
    }
}
