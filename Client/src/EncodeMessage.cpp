//
// Created by spl211 on 04/01/2021.
//

#include "../include/EncodeMessage.h"
#include <boost/algorithm/string.hpp>

EncodeMessage::EncodeMessage(std::string &line, ConnectionHandler *connectionHandler):message(line),
_connectionHandler(connectionHandler),input({}) {}

//the message is admiring register
void EncodeMessage::admiringReg() {
    boost::split(input,message,boost::is_any_of(" "));
    short num=1;
    char bytes[2];
    shortToBytes(num,bytes);
    _connectionHandler->sendBytes(bytes,2);
    _connectionHandler->sendLine(input[1]);
    _connectionHandler->sendLine(input[2]);
}

//the message is student register
void EncodeMessage::studentReg() {
    boost::split(input,message,boost::is_any_of(" "));
    short num=2;
    char bytes[2];
    shortToBytes(num,bytes);
    _connectionHandler->sendBytes(bytes,2);
    _connectionHandler->sendLine(input[1]);
    _connectionHandler->sendLine(input[2]);
}

//the login message
void EncodeMessage::login(){
    boost::split(input,message,boost::is_any_of(" "));
    short num=3;
    char bytes[2];
    shortToBytes(num,bytes);
    _connectionHandler->sendBytes(bytes,2);
    _connectionHandler->sendLine(input[1]);
    _connectionHandler->sendLine(input[2]);

}

//the logout message
void EncodeMessage::logout(){
    short num=4;
    char bytes[2];
    shortToBytes(num,bytes);
    _connectionHandler->sendBytes(bytes,2);
}
//the course register message
void EncodeMessage::courseReg(){
    boost::split(input,message,boost::is_any_of(" "));
    short num=5;
    char bytes[2];
    shortToBytes(num,bytes);
    _connectionHandler->sendBytes(bytes,2);
    short numOfCourse=std::stoi(input[1]);
    shortToBytes(numOfCourse,bytes);
    _connectionHandler->sendBytes(bytes,2);
}

//the kdam check message
void EncodeMessage:: kdamCheck(){
    boost::split(input,message,boost::is_any_of(" "));
    short num=6;
    char bytes[2];
    shortToBytes(num,bytes);
    _connectionHandler->sendBytes(bytes,2);
    short numOfCourse=std::stoi(input[1]);
    shortToBytes(numOfCourse,bytes);
    _connectionHandler->sendBytes(bytes,2);
}
//course stat message
void EncodeMessage::courseStat(){
    boost::split(input,message,boost::is_any_of(" "));
    short num=7;
    char bytes[2];
    shortToBytes(num,bytes);
    _connectionHandler->sendBytes(bytes,2);
    short numOfCourse=std::stoi(input[1]);
    shortToBytes(numOfCourse,bytes);
    _connectionHandler->sendBytes(bytes,2);
}

void EncodeMessage::studentStat(){
    boost::split(input,message,boost::is_any_of(" "));
    short num=8;
    char bytes[2];
    shortToBytes(num,bytes);
    _connectionHandler->sendBytes(bytes,2);
    _connectionHandler->sendLine(input[1]);
}

void EncodeMessage::isRegistered(){
    boost::split(input,message,boost::is_any_of(" "));
    short num=9;
    char bytes[2];
    shortToBytes(num,bytes);
    _connectionHandler->sendBytes(bytes,2);
    short numOfCourse=std::stoi(input[1]);
    shortToBytes(numOfCourse,bytes);
    _connectionHandler->sendBytes(bytes,2);
}

void EncodeMessage::unregister(){
    boost::split(input,message,boost::is_any_of(" "));
    short num=10;
    char bytes[2];
    shortToBytes(num,bytes);
    _connectionHandler->sendBytes(bytes,2);
    short numOfCourse=std::stoi(input[1]);
    shortToBytes(numOfCourse,bytes);
    _connectionHandler->sendBytes(bytes,2);
}

void EncodeMessage::myCourses(){
    short num=11;
    char bytes[2];
    shortToBytes(num,bytes);
    _connectionHandler->sendBytes(bytes,2);
}




short EncodeMessage::bytesToShort(char* bytesArr)
{
    short result = (short)((bytesArr[0] & 0xff) << 8);
    result += (short)(bytesArr[1] & 0xff);
    return result;
}
void EncodeMessage::shortToBytes(short num, char* bytesArr)
{
    bytesArr[0] = ((num >> 8) & 0xFF);
    bytesArr[1] = (num & 0xFF);
}
