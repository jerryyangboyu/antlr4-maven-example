grammar LibExpr;

import CommonLexerRules;

prog: stmt+;

stmt: expr NEWLINE
        | ID '=' expr NEWLINE
        | NEWLINE
        ;

expr: expr ('*' | '/') expr
    | expr ('+' | '-') expr
    | '(' expr ')'
    | INT
    | ID
    ;