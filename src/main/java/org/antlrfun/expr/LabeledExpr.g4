grammar LabeledExpr;

prog: stmt+;

stmt: expr NEWLINE #printExpr // anything begin with # represent a label
        | CLEAR NEWLINE #clear
        | ID '=' expr NEWLINE #assign
        | NEWLINE #blank
        ;

expr: expr op=('*' | '/') expr #mulDiv
        | expr op=('+' | '-') expr #addSub
        | INT #int
        | ID #id
        | '(' expr ')' #parens
        ;

MUL :   '*' ; // assigns token name to '*' used above in grammar
DIV :   '/' ;
ADD :   '+' ;
SUB :   '-' ;
CLEAR : 'clear';
ID  :   [a-zA-Z]+ ;      // match identifiers
INT :   [0-9]+ ;         // match integers
NEWLINE:'\r'? '\n' ;     // return newlines to parser (is end-statement signal)
WS  :   [ \t]+ -> skip ; // toss out whitespace
