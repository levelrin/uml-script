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
    : metadataHeader metadataContent
    ;

metadataHeader
    : DOUBLE_EQUALS METADATA DOUBLE_EQUALS
    ;

metadataContent
    : title
    ;

title
    : TITLE COLON SINGLE_LINE_STRING
    ;

participants
    : participantsHeader participantsContent
    ;

participantsHeader
    : DOUBLE_EQUALS PARTICIPANTS DOUBLE_EQUALS
    ;

participantsContent
    : participantsElement+
    ;

participantsElement
    : participant
    | box
    ;

participant
    : AT REFERENCE_NAME participantPartAfterType?
    ;

participantPartAfterType
    : REFERENCE_NAME participantPartAfterReferenceName?
    ;

participantPartAfterReferenceName
    : SINGLE_LINE_STRING map?
    ;

box
    : SINGLE_LINE_STRING OPENING_BRACKET participantsContent CLOSING_BRACKET
    ;

list
    : OPENING_BRACKET elements CLOSING_BRACKET
    ;

elements
    : (element COMMA)*
    ;

element
    : SINGLE_LINE_STRING
    | NUMBER
    | BOOLEAN
    | list
    | map
    ;

map
    : OPENING_BRACE pairs CLOSING_BRACE
    ;

pairs
    : (pair COMMA)*
    ;

pair
    : REFERENCE_NAME COLON element
    ;

METADATA: 'metadata';
PARTICIPANTS: 'participants';
TITLE: 'title';
DOUBLE_EQUALS: '==';
COLON: ':';
BACK_QUOTE: '`';
COMMA: ',';
AT: '@';
OPENING_BRACKET: '[';
CLOSING_BRACKET: ']';
OPENING_BRACE: '{';
CLOSING_BRACE: '}';
NUMBER: [0-9]+('.'[0-9]+)?;
BOOLEAN: 'true'|'false';
SINGLE_LINE_STRING: BACK_QUOTE ~('`'|'\r'|'\n')* BACK_QUOTE;
REFERENCE_NAME: [a-z]([a-z0-9-]* [a-z0-9])?;
COMMENT: '#' ~[\r\n]* -> channel(1);
WHITE_SPACES: [ \t\r\n]+ -> skip;
