/*
 * Copyright (C) 2015 Hamza Haiken (hamza.haiken@heig-vd.ch)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

grammar CRT;

script
    : statement*
    ;

statement
    : settings
    | scene
    | expression
    ;

settings
    : 'Settings' '{' attribute* '}'
    ;

scene
    : 'Scene' '{' expression* '}'
    ;

expression
    : primary                                                               # primaryExpr                      
    | object                                                                # objectExpr
    | macro                                                                 # macroExpr
    | '[' expressionList? ']'                                               # list
    | expression '[' expression ']'                                         # listAccess
    | expression '(' expressionList? ')'                                    # call
    | expression '<' modifier (',' modifier)* '>'                           # modifiers
    | <assoc=right> ('+' | '-') expression                                  # unarySign
    | '!' expression                                                        # unaryNot
    | expression ('*' | '/' | '%') expression                               # multiplication
    | expression ('+' | '-' | '^') expression                               # addition
    | expression ('<=' | '>=' | '<' | '>'| '==' | '!=') expression          # comparison
    | expression '&&' expression                                            # binaryAnd
    | expression '||' expression                                            # binaryOr
    | <assoc=right> expression '?' expression ':' expression                # ternary
    | <assoc=right> expression '=' expression                               # assignment
    ;

expressionList
    : expression (',' expression)*
    ;

primary
    : '(' expression ')'
    | literal
    | identifierPrimary
    ;

object
    : NAME '{' attribute* '}'
    ;

macro
    : 'Macro' '(' paramList? ')' '{' expression* '}'
    ;

paramList
    : IDENTIFIER (',' IDENTIFIER)*
    ;

identifierPrimary
    : IDENTIFIER
    ;

literal
    : integerLiteral
    | floatLiteral
    | stringLiteral
    | booleanLiteral
    ;

attribute
    : IDENTIFIER '->' expression
    ;

modifier
    : 'scale' expression
    | 'translate' expression
    | 'rotate' expression
    ;

integerLiteral
    : INTEGER
    ;
    
floatLiteral
    : FLOAT
    ;

stringLiteral
    : STRING
    ;

booleanLiteral
    : 'true'
    | 'false'
    ;

// Reserved names

SETTINGS        : 'Settings';
SCENE           : 'Scene';
SCALE           : 'scale';
TRANSLATE       : 'translate';
ROTATE          : 'rotate';

// Types

IDENTIFIER
    : LOWER (LOWER | UPPER | NUMBER)*
    ;

NAME
    : UPPER (LOWER | UPPER | NUMBER)*
    ;

STRING
    : '"' ( ESCAPED_QUOTE | ~('\n'|'\r') )*? '"'
    ;

SKIP
    : (SPACES | COMMENT | LINE_JOINING) -> skip
    ;

INTEGER
    : DIGITS
    ;

FLOAT
    : DIGITS '.' DIGITS?
    | '.' DIGITS
    ;

// Separators

LPAREN          : '(';
RPAREN          : ')';
LBRACE          : '{';
RBRACE          : '}';
LBRACK          : '[';
RBRACK          : ']';
COMMA           : ',';

// Operators

ASSIGN          : '=';
ATTRIBUTE       : '->';
ADD             : '+';
SUBTRACT        : '-';
INTERSECTION    : '^';
MULTIPLY        : '*';
DIVIDE          : '/';
MODULO          : '%';
NOT             : '!';
LESS            : '<';
GREATER         : '>';
LESS_EQUAL      : '<=';
GREATER_EQUAL   : '>=';
EQUAL           : '==';
NOT_EQUAL       : '!=';
AND             : '&&';
OR              : '||';
QUESTION        : '?';
COLON           : ':';

// Fragments

fragment DIGITS
    : NUMBER NUMBER*
    ;

fragment SPACES
    : [ \t]+
    ;

fragment COMMENT
    : '--' ~[\r\n]*
    ;

fragment LINE_JOINING
    : [\n\r]
    ;

fragment LOWER
    : 'a'..'z'
    ;

fragment UPPER
    : 'A'..'Z'
    ;

fragment NUMBER
    : '0'..'9'
    ;

fragment ESCAPED_QUOTE 
    : '\\"'
    ;