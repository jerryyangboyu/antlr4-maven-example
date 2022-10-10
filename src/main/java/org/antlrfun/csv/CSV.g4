grammar CSV;

file: header row+; // a csv file has at least 2 line
header: row;
row: field (',' field)* NEWLINE;
field: TEXT #text
     | STRING #string
     | #empty
     ;

TEXT: ~[,\n\r"]+; // any thing except ',' newline '"' with lenght 1 or more
STRING: '"' ('""' | ~'"')* '"'; // left quote, followed by escaped quote or any other characters, followed by right quote
NEWLINE: '\r'? '\n'; // Windows and Unix newlines, question mark stands for optional
