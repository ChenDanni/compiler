//
// Created by chendanni on 16/10/26.
//

#include <zconf.h>
#include <iostream>

#include "recognize.h"

using namespace std;


Token reserved[RESERVEDNUM] = {{"int",INT},{"char",CHAR},
                               {"void",VOID},{"public",PUBLIC},
                               {"for",FOR},{"while",WHILE},
                               {"if",IF},{"else",ELSE},
                               {"return",RETURN}};
Token simpleSign[SIMPLESINGNUM] = {{"{",LBRACE},{"}",RBRACE},{"(",LBRACKET},
                                   {")",RBRACKET},{"[",LPAREN},{"]",RPAREN},
                                   {";",SEMICOLON},{"\'",QUOTE}};
Token complexSign[COMPLEXSIGNNUM] = {{"+",ADD},{"++",ADDONE},{"+=",ADDEQ},
                                     {"-",MINUS},{"--",MINUSONE},{"-=",MINUSEQ},
                                     {"*",MUL},{"*=",MULEQ},{"/",DIV},
                                     {"/=",DIVEQ},{"=",EQ},{"==",IFEQ},
                                     {">",GT},{">=",GE},{"<",LT},
                                     {"<=",LE},{"!=",NE},{"||",OR},
                                     {"&&",AND}};

char num[NUMNUM] = {'0','1','2','3','4','5','6','7','8','9'};

bool checkEqualStr(string str1, string str2){
    int i = 0;

    while((str1[i] != '\0')&&(str2[i] != '\0')){
        if (str1[i] != str2[i])
            return false;
        i++;
    }
    if (str1[i] != str2[i])
        return false;
    return true;
}

Token checkReserved(string str){
    int i = 0;
    bool check = false;
    TokenType type = NOTMEET;
    //保留字
    for (i = 0;i < RESERVEDNUM; i++){
        check = checkEqualStr(str,reserved[i].element);
        if (check){
            type = RESERVED;
            Token ret = {
                    str,
                    type
            };
            return ret;
        }
    }
    return NOT_MEET;
}

Token checkSimpleSign(string str){
    int i = 0;
    bool check = false;
    TokenType type = NOTMEET;
    check = false;
    for (i = 0;i < SIMPLESINGNUM;i++){
        check = checkEqualStr(str,simpleSign[i].element);
        if (check){
            type = simpleSign[i].type;
            Token ret = {
                    str,
                    type
            };
            return ret;
        }
    }
    return NOT_MEET;
}

Token checkComplexSign(string str){
    int i = 0;
    bool check = false;
    TokenType type = NOTMEET;
    check = false;
    for (i = 0;i < COMPLEXSIGNNUM;i++){
        check = checkEqualStr(str,complexSign[i].element);
        if (check){
            type = complexSign[i].type;
            Token ret = {
                    str,
                    type
            };
            return ret;
        }
    }
    return NOT_MEET;
}
//保留字 符号
Token checkType(string str){
//    cout << "check Type " << str << endl;
    int i = 0;
    bool check = false;
    TokenType type = NOTMEET;
    //保留字
    for (i = 0;i < RESERVEDNUM; i++){
        check = checkEqualStr(str,reserved[i].element);
        if (check){
//            cout << "reserved " << str << endl;
            type = RESERVED;
            Token ret = {
                    str,
                    type
            };
            return ret;
        }
    }
    //simple sign
    check = false;
    for (i = 0;i < SIMPLESINGNUM;i++){
        check = checkEqualStr(str,simpleSign[i].element);
        if (check){
//            cout << "simple " << str << endl;
            type = simpleSign[i].type;
            Token ret = {
                    str,
                    type
            };
            return ret;
        }
    }
    //complex sign
    check = false;
    for (i = 0;i < COMPLEXSIGNNUM;i++){
        check = checkEqualStr(str,complexSign[i].element);
        if (check){
//            cout << "complex " << str << endl;
            type = complexSign[i].type;
            Token ret = {
                    str,
                    type
            };
            return ret;
        }
    }
    return NOT_MEET;
}

//字符
Token checkChar(string str){
    Token ret = NOT_MEET;
    if ((getQuote)&&(str.length() == 1)){
        ret = {
                str,
                CHAR
        };
    }
    return ret;
}

//换行
Token checkChangeLine(string str){
    Token ret = NOT_MEET;
    if(str.length() == 2){
        if ((str[0] == '\\')&&(str[1] == 'n')){
            ret = {
                    str,
                    CHANGELINE
            };
        }
    }
    return ret;
}

//变量
Token checkVariable(string str){
    Token ret = NOT_MEET;

    if (!getQuote){
        ret = {
                str,
                ID
        };
    }
    return ret;
}

//注释
//Token checkComment(string str){
//    Token ret = NOT_MEET;
//    //单行注释
//    if ((getSingleComment)&&(!getQuote)){
//        ret = {
//                str,
//                COMMENT
//        };
//        getSingleComment = false;
//        return ret;
//    }
//    //多行注释
//    if ((getMulComment)&&(!getQuote)){
//        ret = {
//                str,
//                COMMENT
//        };
//        return ret;
//    }
//    return ret;
//}

//数字
Token checkNumber(string str){
    int i = 0,j = 0;
    bool check = false;
    while(str[i] != '\0'){
        check = false;
        for (j = 0;j < NUMNUM;j++){
            if (str[i] == num[j]){
                check = true;
                break;
            }
        }
        if (!check)
            return NOT_MEET;
        i++;
    }
    Token ret = {
            str,
            NUM
    };
    return ret;
}

//得到token
Token getToken(string str){
    Token ret;
//    cout << "type " << endl;
    ret = checkType(str);
    if (ret.type != NOTMEET) return ret;
//    cout << "char " << endl;
    ret = checkChar(str);
    if (ret.type != NOTMEET) return ret;
    ret = checkChangeLine(str);
    if (ret.type != NOTMEET) return ret;
    ret = checkNumber(str);
    if (ret.type != NOTMEET) return ret;
    ret = checkVariable(str);
    if (ret.type != NOTMEET) return ret;
//    ret = checkComment(str);
//    if (ret.type != NOTMEET) return ret;
    return NOT_MEET;
}

//输出Token
void printToken(Token token){
    string type = "";
    switch(token.type){
        case ID:
            type = "ID"; break;
        case RESERVED:
            type = "RESERVED"; break;
        case NUM:
            type = "NUM"; break;
        case CHANGELINE:
            type = "CHANGELINE"; break;
        case COMMENT:
            type = "COMMENT"; break;
        case LBRACE:
            type = "LBRACE"; break;
        case RBRACE:
            type = "RBRACE"; break;
        case LPAREN:
            type = "LPAREN"; break;
        case RPAREN:
            type = "RPAREN"; break;
        case LBRACKET:
            type = "LBRACKET"; break;
        case RBRACKET:
            type = "RBRACKET"; break;
        case NE:
            type = "NE"; break;
        case SEMICOLON:
            type = "SEMICOLON"; break;
        case OR:
            type = "OR"; break;
        case AND:
            type = "AND"; break;
        case QUOTE:
            type = "QUOTE"; break;
        case ADD:
            type = "ADD"; break;
        case ADDONE:
            type = "ADDONE"; break;
        case MINUS:
            type = "MINUS"; break;
        case MINUSONE:
            type = "MINUSONE"; break;
        case ADDEQ:
            type = "ADDEQ"; break;
        case MINUSEQ:
            type = "MINUSEQ"; break;
        case MUL:
            type = "MUL"; break;
        case MULEQ:
            type = "MULEQ"; break;
        case DIV:
            type = "DIV"; break;
        case DIVEQ:
            type = "DIVEQ"; break;
        case EQ:
            type = "EQ"; break;
        case IFEQ:
            type = "IFEQ"; break;
        case LT:
            type = "LT"; break;
        case LE:
            type = "LE"; break;
        case GT:
            type = "GE"; break;
        case INT:
            type = "INT"; break;
        case CHAR:
            type = "CHAR"; break;
        default:
            type = "NOTMEET"; break;
    }
    cout << lineNum << ":" << "(" << type << ":" << token.element << ")" << endl;
}

//int main() {
//    printToken(getToken("\'"));
//}