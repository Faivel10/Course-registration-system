//
// Created by spl211 on 09/01/2021.
//

#ifndef ASSIGNMENT3_PROTOCOL_H
#define ASSIGNMENT3_PROTOCOL_H
#include "../include/EncoderDecoder.h"
#include "../include/connectionHandler.h"

class Protocol {
private:
    ConnectionHandler* _connectionHandler;
    bool* _close;

public:
    Protocol(ConnectionHandler *connectionHandler,bool *close);
    bool* protocol();


};


#endif //ASSIGNMENT3_PROTOCOL_H
