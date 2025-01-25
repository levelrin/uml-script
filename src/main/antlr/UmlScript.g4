grammar UmlScript;

@header {package com.levelrin.antlr.generated;}

root
    : section+
    ;

section
    : metadata
    | participants
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
    : title
    ;

title
    : TITLE COLON SINGLE_LINE_STRING
    ;

participants
    : participantsHeader participantsContent participantsFooter
    ;

participantsHeader
    : DOUBLE_EQUALS PARTICIPANTS DOUBLE_EQUALS
    ;

participantsFooter
    : DOUBLE_DASHES PARTICIPANTS DOUBLE_DASHES
    ;

participantsContent
    : participant+
    ;

participant
    : REFERENCE_NAME COLON SINGLE_LINE_STRING
    ;

METADATA: 'metadata';
PARTICIPANTS: 'participants';
TITLE: 'title';
DOUBLE_EQUALS: '==';
DOUBLE_DASHES: '--';
COLON: ':';
BACK_QUOTE: '`';
SINGLE_LINE_STRING: BACK_QUOTE ~('`'|'\r'|'\n')* BACK_QUOTE;
REFERENCE_NAME: [a-z]([a-z0-9-]* [a-z0-9])?;
COMMENT: '#' ~[\r\n]* -> channel(1);
WHITE_SPACES: [ \t\r\n]+ -> skip;
