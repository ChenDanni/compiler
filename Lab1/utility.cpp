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

    if (('0' <= ch)&&(ch <= '9')){
        ret = TYPE_NUM;
//        cout << "num  " << ch << endl;
    } else if (((getQuote)&&(ch != '\''))||(('a' <= ch)&&(ch <= 'z'))||(('A' <= ch)&&(ch <= 'Z'))||(ch == '_')){
//        cout << "char  " << ch << endl;
        ret = TYPE_CHAR;
        getWord = true;
    } else if ((ch == ' ')||(ch == '\t')){
//        cout << "space  " << ch << endl;
        ret = TYPE_SPACE;
    } else if (ch == '\0'){
//        cout << "end  " << ch << endl;
        ret = TYPE_END;
    } else {
//        cout << "sign  " << ch << endl;
        ret = TYPE_SIGN;
    }

    return ret;
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
    cout << str << endl;
    str += '\0';
    string element = "";

    CharType typeBefore = TYPE_START;
    CharType typeCurrent = TYPE_START;

    int  i = 0,j = 0;
    //去除最前面空格
    for (j = 0;j < str.length();j++){
        if (defType(str[j]) != TYPE_SPACE) {
            i = j;
            break;
        }
    }

    for (i = j;i < str.length();i++){
//        cout << "str[i] : _____" << str[i] << endl;
        if ((str[i] == '\'')&&(!getQuote)){
            getQuote = !getQuote;
        }
        typeCurrent = defType(str[i]);

        if (stepOneJustify(typeBefore,typeCurrent)){

            Token test = checkSimpleSign(element);
            Token testComplex = NOT_MEET;
            if (element.length() == 2)
                testComplex = checkComplexSign(element);

            if (test.type != NOTMEET){
                printToken(test);
                element = "";
                getWord = false;
            } else if (testComplex.type != NOTMEET){
                printToken(testComplex);
                element = "";
                getWord = false;
            }
            element += str[i];
        }else {
//            cout << "element " << element << endl;
            printToken(getToken(element));
            if (str[i] == '\'') {
                getQuote = !getQuote;
            }
            element = "";
            getWord = false;
            if ((str[i] != ' ') && (str[i] != '\0')) {
                element += str[i];
            } else {
                typeCurrent = TYPE_START;
            }
        }
        typeBefore = typeCurrent;
    }
}

void getByLine(){
    ifstream in("test.txt");
    string line = "";
    if(in){
        while(getline(in,line)){
//            cout << line << endl;
            lineNum++;
            stepOne(line);
        }
    }else {
        cout << "can't find such file" << endl;
    }
}

