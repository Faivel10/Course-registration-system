//
// Created by spl211 on 26/12/2020.
//
#include "../include/Task.h"
#include "../include/EncoderDecoder.h"


Task::Task(ConnectionHandler* connectionHandler,bool *close):_connectionHandler(connectionHandler),_close(close){}

//the run function that reading from the keyboard-and send the message to server
void Task::run() {
    EncoderDecoder encoderDecoder(_connectionHandler);
    *_close = false;
    while (!(*_close)) {//while its not close
        if(*_close){
            _connectionHandler->close();
            std::cout << "Exiting...\n" << std::endl;
            break;
        }

        const short bufsize = 1024;
        char buf[bufsize];
        //get line from keyboard and put the data on ^buf
        std::cin.getline(buf, bufsize);

        //creates string from the buf
        std::string line(buf);

        //translate the message from keyboard
       _close= encoderDecoder.decoderEncoderFromKeyboard(line);
            }
            delete _close;




        }

    


short Task::bytesToShort(char* bytesArr)
{
    short result = (short)((bytesArr[0] & 0xff) << 8);
    result += (short)(bytesArr[1] & 0xff);
    return result;
}
