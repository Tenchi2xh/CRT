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
    : statement* EOF
    ;

statement
    : definition
    | config
    | scene
    ;

definition
    : IDENTIFIER '=' expression
    ;

config
    : 'Config' '{' attribute* '}'
    ;

attribute
    : IDENTIFIER '->' expression
    ;

scene
    : 'Scene' '{' (definition | expression)* '}'
    ;

expression
    : primary
    | expression '[' expression ']'
    | expression '(' expression (',' expression)* ')'
    | expression LCHEVR modifier (',' modifier)* RCHEVR
    | ('+' | '-') expression
    | '!' expression
    | expression ('*' | '/' | '%') expression
    | expression ('+' | '-') expression
    | expression ('<=' | '>=' | GT | LT) expression
    | expression ('==' | '!=') expression
    ;

primary
    : '(' expression ')'
    | literal
    | IDENTIFIER
    ;

literal
    : INTEGER
    | FLOAT
    | STRING
    | IDENTIFIER
    | BOOLEAN
    ;

// Tokens

IDENTIFIER
    : LOWER (LOWER | UPPER | NUMBER)*
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

BOOLEAN
    : 'true'
    | 'false'
    ;

// Separators

LPAREN      : '(';
RPAREN      : ')';
LBRACE      : '{';
RBRACE      : '}';
LBRACK      : '[';
RBRACK      : ']';
LCHEVR      : '<';
RCHEVR      : '>';
COMMA       : ',';

// Operators

ASSIGN      : '=';
ATTRIBUTE   : '->';
ADD         : '+';
SUBTRACT    : '-';
MULTIPLY    : '*';
DIVIDE      : '/';
MODULO      : '%';
NOT         : '!';
LESS        : '<';
GT          : '>';
LE          : '<=';
GE          : '>=';
EQ          : '==';
NOTEQUAL    : '!=';

// Block names

CONFIG      : 'Config';
SCENE       : 'Scene';

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
    : '\\' SPACES? ( '\r'? '\n' | '\r' )
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