grammar UmlScript;

@header {package com.levelrin.antlr.generated;}

root
    : section*
    ;

section
    : metadata
    ;

metadata
    : metadataHeader metadataContent metadataFooter
    ;

metadataHeader
    : DOUBLE_EQUALS METADATA DOUBLE_EQUALS
    ;

metadataFooter
    : DOUBLE_DASHES METADATA DOUBLE_DASHES
    ;

metadataContent
    : TITLE COLON SINGLE_LINE_STRING
    ;

METADATA: 'metadata';
PARTICIPANTS: 'participants';
TITLE: 'title';
DOUBLE_EQUALS: '==';
DOUBLE_DASHES: '--';
COLON: ':';
BACK_QUOTE: '`';
SINGLE_LINE_STRING: BACK_QUOTE ~('`'|'\r'|'\n')* BACK_QUOTE;
COMMENT: '#' ~[\r\n]* -> channel(1);
WS: [ \t\r\n]+ -> skip;
