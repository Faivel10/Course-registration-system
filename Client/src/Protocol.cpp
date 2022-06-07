//
// Created by spl211 on 09/01/2021.
//

#include "../include/Protocol.h"

Protocol::Protocol(ConnectionHandler *connectionHandler, bool *close):_connectionHandler(connectionHandler),_close(close) {}

bool * Protocol::protocol() {
    *_close=false;
    EncoderDecoder encoderDecoder(_connectionHandler);
    std::string print;
    std::string dataMessage;
    short numOfOpcode=encoderDecoder.numOFOpcode();


    if(numOfOpcode==12){//this is ACK message
        print="ACK";
        short numOfMessage=encoderDecoder.numOfMessage();
        print=print+ " "+std::to_string(numOfMessage);

        if(numOfMessage==4){
            *_close=true;

        }
        else if(numOfMessage==6){
            _connectionHandler->getLine(dataMessage);
            print=print+ "\n"+ dataMessage;
            dataMessage="";

        }
        else if(numOfMessage==7){
            _connectionHandler->getLine(dataMessage);
            print=print+ "\n"+dataMessage;
            dataMessage="";


        }
        else if(numOfMessage==8){
            _connectionHandler->getLine(dataMessage);
            print=print+ "\n"+ dataMessage;
            dataMessage="";


        }
        else if(numOfMessage==9){
            _connectionHandler->getLine(dataMessage);
            print=print+ "\n"+ dataMessage;
            dataMessage="";

        }
        else if(numOfMessage==11){
            _connectionHandler->getLine(dataMessage);
            print=print+ "\n"+ dataMessage;
            dataMessage="";

        }

        std::cout<<print<<std::endl;

    }
    else if(numOfOpcode==13){//this is error message
        print="ERROR";
        short numOfMessage=encoderDecoder.numOfMessage();
        print=print+" "+std::to_string(numOfMessage);
        std::cout<<print<<std::endl;
    }
    return _close;


};