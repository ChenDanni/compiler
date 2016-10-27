#ifndef LAB1_RECOGNIZE_H
#define LAB1_RECOGNIZE_H

#include <iostream>
using namespace std;

const int RESERVEDNUM = 9;
const int NUMNUM = 10;
const int SIMPLESINGNUM = 11;
const int COMPLEXSIGNNUM = 16;

extern bool getQuote;
extern bool getSingleComment; // get //
extern bool getMulComment; //get /*
extern bool getWord;

extern int lineNum;

typedef enum {
    ID, RESERVED, NUM, CHANGELINE, COMMENT,
    LBRACE, RBRACE, LPAREN, RPAREN, LBRACKET, RBRACKET,
    NE, SEMICOLON, OR, AND, QUOTE,
    ADD, ADDONE, MINUS, MINUSONE, ADDEQ, MINUSEQ, MUL, MULEQ,
    DIV, DIVEQ, EQ, IFEQ, LT, LE, GT, GE,
    INT,CHAR,VOID,PUBLIC,FOR,WHILE,IF,ELSE,RETURN,
    NOTMEET
} TokenType;


struct Token {
    string element;
    TokenType type;
};

bool checkEqualStr(string str1, string str2);

Token getToken(string str);
void printToken(Token token);
#endif //LAB1_RECOGNIZE_H