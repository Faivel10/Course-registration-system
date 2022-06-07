//
// Created by spl211 on 27/12/2020.
//

#include <cstdlib>
#include <iostream>
#include <thread>
#include "../include/connectionHandler.h"
#include "../include/Task.h"
#include "../include//Protocol.h"

void run(ConnectionHandler* connectionHandler,bool *close){
    Protocol p(connectionHandler,close);

    *close=false;
    //reading from the socket
    while(!(*close)) {
       //the protocol doing all the things
        *close= *(p.protocol()); //checks which message is receives from server
        if (*close) {//if its logout message

            break;
        }
    }


}

//The main thread-reading from the socket
int main (int argc, char *argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }

//the ip and port of the server-gets from args
    std::string host = argv[1];
    short port =atoi(argv[2]);


    ConnectionHandler *connectionHandler=new ConnectionHandler(host, port);

    bool isConnected=connectionHandler->connect();
    if (!isConnected) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }
    bool *close=new bool;
    Task task=Task(connectionHandler,close);
    //this thread is reading from keyboard
    std::thread thread1=std::thread(&Task::run,&task);
    //run the thread main
    run(connectionHandler,close);
    thread1.join(); //for doing the two rules of the client in simultaneously


    delete close;
    delete connectionHandler;

    return 0;

}
