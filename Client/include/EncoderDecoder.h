//
// Created by spl211 on 27/12/2020.
//

#ifndef ASSIGNMENT3_ENCODERDECODER_H
#define ASSIGNMENT3_ENCODERDECODER_H

#include <vector>
#include "iostream"
#include "string"
#include "connectionHandler.h"

class EncoderDecoder {

private:
    ConnectionHandler* _connectionHandler;


public:
    EncoderDecoder(ConnectionHandler* connectionHandler);

    bool* decoderEncoderFromKeyboard(std::string &line);
    short numOFOpcode();
    short numOfMessage();
    short bytesToShort(char* bytesArr);
    void shortToBytes(short num, char* bytesArr);



};


#endif //ASSIGNMENT3_ENCODERDECODER_H
