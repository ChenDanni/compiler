//
// Created by user on 16/10/27.
//

#include <iostream>
#include <fstream>
#include "utility.h"
#include "recognize.h"
using namespace std;

bool getQuote = false;
bool getSingleComment = false;
bool getMulComment = false;
bool getWord = false;

int lineNum = 0;

typedef enum {
    TYPE_NUM,
    TYPE_CHAR,
    TYPE_SIGN,
    TYPE_START,
    TYPE_SPACE,
    TYPE_END
} CharType;


CharType defType(char ch){
    CharType ret;
    if (('0' <= ch)&&('ch' <= '9')){
        ret = TYPE_NUM;
    } else if ((getQuote)||(('a' <= ch)&&('ch' <= 'z'))||(('A' <= ch)&&('ch' <= 'Z'))||(ch == '_')){
        ret = TYPE_CHAR;
    } else if (ch == ' '){
        ret = TYPE_SPACE;
    } else if (ch == '\0'){
        ret = TYPE_END;
    } else {
        ret = TYPE_SIGN;
    }
    return ret;
}

void handleStartEnd(char ch,CharType type){
    if (ch == '\'')
        getQuote = !getQuote;
    if (type == TYPE_CHAR)
        getWord = true;

}

//a001
bool stepOneJustify(CharType typeBefore,CharType typeCurrent){
    if ((typeBefore == TYPE_START)||(typeBefore == typeCurrent)){
        return true;
    }
    if (getWord){
        if ((typeCurrent == TYPE_CHAR)||(typeCurrent == TYPE_NUM))
            return true;
        else {
            getWord = false;
            return false;
        }
    }
    return false;
}

//() {} [] ;
void stepOne(string str){
    //test(){
    //if(a==1){
    //a=2++;
    //}
    string element = "";

    CharType typeBefore = TYPE_START;
    CharType typeCurrent = TYPE_START;

    int  i = 0;
    //去除最前面空格
    for (i = 0;i < str.length();i++){
        if (str[i] == ' ')
            continue;
    }
    for (i = i;i < str.length();i++){
        typeCurrent = defType(str[i]);
        handleStartEnd(str[i],typeCurrent);
        if (stepOneJustify(typeBefore,typeCurrent)){
            element += str[i];
        }else{
            printToken(getToken(element));
            element = "";
            getWord = false;
            if ((str[i] != ' ')||(str[i] != '\0')){
                element += str[i];
            }
        }
    }
}

void getByLine(){
    ifstream in("test.txt");
    string line = "";
    if(in){
        while(getline(in,line)){
            cout << line << endl;
            lineNum++;
            stepOne(line);
        }
    }else {
        cout << "can't find such file" << endl;
    }
}

