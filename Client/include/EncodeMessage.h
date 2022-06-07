//
// Created by spl211 on 04/01/2021.
//

#ifndef ASSIGNMENT3_ENCODEMESSAGE_H
#define ASSIGNMENT3_ENCODEMESSAGE_H
#include <string>
#include <iostream>
#include <vector>
#include "../include/connectionHandler.h"

class EncodeMessage {
private:
    std::string& message;
    ConnectionHandler *_connectionHandler;
    std::vector<std::string>input;

public:
    EncodeMessage(std::string& line,ConnectionHandler* connectionHandler);
    EncodeMessage(const EncodeMessage&);
    EncodeMessage operator=(const EncodeMessage&);

    void admiringReg();
    void studentReg();
    void login();
    void logout();
    void courseReg();
    void kdamCheck();
    void courseStat();
    void studentStat();
    void isRegistered();
    void unregister();
    void myCourses();

    short bytesToShort(char* bytesArr);
    void shortToBytes(short num, char* bytesArr);

};


#endif //ASSIGNMENT3_ENCODEMESSAGE_H
