grammar lunegrammar;

options {
    output=AST;
    ASTLabelType=CommonTree;
}

@header {
package main.java.lexparse;
}

top:	(def)+ EOF;
expr:	app -> app
       | 	let -> let
       | 	lambda -> lambda 
       | 	ifxp -> ifxp;
app:	p_expr^ (p_expr)*;
p_expr:	basic_expr -> basic_expr
           | 	paren_expr -> paren_expr ;
basic_expr:	ID | EQ | INT | FLOAT | STRING;
paren_expr:	'(' expr ')' -> expr;
idd :	ID | (ID)+;
let:	'let' (ID)+ EQ e1=expr 'in' e2=expr -> ^('let' (ID)+ $e1 $e2);
def:	'def' (ID)+ EQ expr -> ^('def' (ID)+ expr);
lambda:	'fun' (ID)+ '->' expr -> ^('fun' (ID)+ expr);
ifxp:	'if' cond=expr 'then' body=expr 'else' alt=expr -> ^('if' $cond $body $alt);

ID:	('a'..'z'|'A'..'Z'|'_'|'+'|'-'|'/'|'*'|'|'|'<'|'>') ('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'+'|'-'|'/'|'*'|'|'|'<'|'>')*;
EQ:	'=';


INT :	'0'..'9'+
    ;

FLOAT
    :   ('0'..'9')+ '.' ('0'..'9')* EXPONENT?
    |   '.' ('0'..'9')+ EXPONENT?
    |   ('0'..'9')+ EXPONENT
    ;

COMMENT
    :   '--' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    |   '(*' ( options {greedy=false;} : . )* '*)' {$channel=HIDDEN;}
    ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) {$channel=HIDDEN;}
    ;

STRING
    :  '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
    ;

fragment
EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

fragment
HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNICODE_ESC
    |   OCTAL_ESC
    ;

fragment
OCTAL_ESC
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;

