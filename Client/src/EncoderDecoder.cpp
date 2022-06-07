//
// Created by spl211 on 27/12/2020.
//

#include "../include/EncoderDecoder.h"

#include "../include/EncodeMessage.h"

EncoderDecoder::EncoderDecoder(ConnectionHandler *connectionHandler):_connectionHandler(connectionHandler) {}

//this function decodes and encode the commands from the keyboards
//the commands pass to the function as parameter
bool* EncoderDecoder::decoderEncoderFromKeyboard(std::string &line) {
    bool *itClose=new bool;
    *itClose= false;
    EncodeMessage message(line,_connectionHandler);


    if (line.find("ADMINREG") != std::string::npos) {
         message.admiringReg();
    }
    else if(line.find("STUDENTREG") != std::string::npos){
        message.studentReg();
    }
    else if(line.find("LOGIN") != std::string::npos){
        message.login();
    }
    else if(line.find("LOGOUT") != std::string::npos){
        *itClose=true;
        message.logout();
        
    }
    else if(line.find("COURSEREG") != std::string::npos){
        message.courseReg();
    }
    else if(line.find("KDAMCHECK") != std::string::npos){
        message.kdamCheck();
    }
    else if(line.find("COURSESTAT") != std::string::npos){
        message.courseStat();
    }
    else if(line.find("STUDENTSTAT") != std::string::npos){
        message.studentStat();
    }
    else if(line.find("ISREGISTERED") != std::string::npos){
        message.isRegistered();
    }
    else if(line.find("UNREGISTER") != std::string::npos){
        message.unregister();
    }
    else if(line.find("MYCOURSES") != std::string::npos){
        message.myCourses();
    }
    return itClose;

}



short EncoderDecoder::numOFOpcode() {
    //gets the opcode from the message
    char* bytes=new char[2];
    _connectionHandler->getBytes(bytes,2);
    short opcode= bytesToShort(bytes);
    delete[] bytes;
    return opcode;

}
short EncoderDecoder::numOfMessage() {
    char* bytes=new char[2];
    _connectionHandler->getBytes(bytes,2);
    short numOfMessage=bytesToShort(bytes);
    delete[] bytes;
    return numOfMessage;
}

short EncoderDecoder::bytesToShort(char* bytesArr)
{
    short result = (short)((bytesArr[0] & 0xff) << 8);
    result += (short)(bytesArr[1] & 0xff);
    return result;
}
void EncoderDecoder::shortToBytes(short num, char* bytesArr)
{
    bytesArr[0] = ((num >> 8) & 0xFF);
    bytesArr[1] = (num & 0xFF);
}
