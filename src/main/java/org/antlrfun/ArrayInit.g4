grammar ArrayInit;

init: '{' value (',' value)* '}';
value: init | INT; // the value is either array init or integer

INT: [0-9]+; // match integers digit of 0-0
WS: [ \t\r\n]+ -> skip; // skip spaces, tabs, newlines; WS mean white space