lexer grammar CommonLexerRules;

ID: [a-zA-Z_][a-zA-Z0-9_]*; // identifier for variables
INT: [0-9]+;
NEWLINE: '\r'? '\n'; // Windows and Unix newlines, question mark stands for optional
WS: [ \t]+ -> skip; // skip whitespace and tabs