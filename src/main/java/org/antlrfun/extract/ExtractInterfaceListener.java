package org.antlrfun.extract;

import org.antlr.v4.runtime.TokenStream;

public class ExtractInterfaceListener extends JavaBaseListener {
    JavaParser parser;

    public ExtractInterfaceListener(JavaParser parser) {
        this.parser = parser;
    }

    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        System.out.printf("\ninterface I%s {\n", ctx.Identifier());
    }

    @Override
    public void exitClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        System.out.println("}");
    }

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        String type = "void";
        if (ctx.type() != null) {
            type = ctx.type().getText();
        }
        String args = ctx.formalParameters().getText();
        System.out.printf("\t%s %s %s;\n", type, ctx.Identifier().getText(), args);
    }

    @Override
    public void enterImportDeclaration(JavaParser.ImportDeclarationContext ctx) {
        TokenStream tokens = parser.getTokenStream();
        System.out.println(tokens.getText(ctx));
    }
}
