package org.antlrfun.csv;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LoadCSV {
    static class Loader extends CSVBaseListener {
        public static final String EMPTY = "";
        List<Map<String, String>> rows = new ArrayList<>();
        List<String> header = new ArrayList<>();
        List<String> currentRowFieldValues = new ArrayList<>();

        @Override
        public void exitString(CSVParser.StringContext ctx) {
            currentRowFieldValues.add(ctx.STRING().getText());
        }

        @Override
        public void exitText(CSVParser.TextContext ctx) {
            currentRowFieldValues.add(ctx.TEXT().getText());
        }

        @Override
        public void exitEmpty(CSVParser.EmptyContext ctx) {
            currentRowFieldValues.add(EMPTY);
        }

        @Override
        public void exitHeader(CSVParser.HeaderContext ctx) {
            header.addAll(currentRowFieldValues); // assume the row has been parsed
        }

        @Override
        public void enterRow(CSVParser.RowContext ctx) {
            currentRowFieldValues.clear();
        }

        @Override
        public void exitRow(CSVParser.RowContext ctx) {
            if (ctx.getParent().getRuleIndex() == CSVParser.RULE_header) return;

            Map<String, String> row = new LinkedHashMap<>();
            for (int i = 0; i < header.size(); i++) {
                row.put(header.get(i), currentRowFieldValues.get(i));
            }
            rows.add(row);
        }
    }

    public static void main(String[] args) {
        try (FileInputStream inputStream = new FileInputStream("demo.csv.txt")) {
            CSVLexer lexer = new CSVLexer(CharStreams.fromStream(inputStream));
            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            CSVParser parser = new CSVParser(tokenStream);
            ParseTree ast = parser.file();
            ParseTreeWalker walker = new ParseTreeWalker();
            Loader loader = new Loader();

            walker.walk(loader, ast);
            System.out.println(loader.rows);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
