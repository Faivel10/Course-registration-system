//
// Created by spl211 on 23/12/2020.
//

#ifndef ASSIGNMENT3_TASK_H
#define ASSIGNMENT3_TASK_H
#include "../include/connectionHandler.h"
#include <string>

class Task {

private:
    ConnectionHandler* _connectionHandler;
    bool* _close;

public:

    Task(ConnectionHandler* connectionHandler,bool *close);
    void run();
    short bytesToShort(char* bytesArr);

};


#endif //ASSIGNMENT3_TASK_H
