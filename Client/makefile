CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system -lboost_thread -pthread

all: BGRSclient
	g++ -o bin/BGRSclient bin/client.o bin/connectionHandler.o bin/EncoderDecoder.o bin/EncodeMessage.o bin/Task.o bin/Protocol.o  $(LDFLAGS)

BGRSclient: bin/client.o bin/connectionHandler.o bin/EncoderDecoder.o bin/EncodeMessage.o bin/Task.o bin/Protocol.o 

bin/client.o: src/client.cpp
	g++ $(CFLAGS) -o bin/client.o src/client.cpp

bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp

bin/EncoderDecoder.o: src/EncoderDecoder.cpp
	g++ $(CFLAGS) -o bin/EncoderDecoder.o src/EncoderDecoder.cpp

bin/EncodeMessage.o: src/EncodeMessage.cpp
	g++ $(CFLAGS) -o bin/EncodeMessage.o src/EncodeMessage.cpp

bin/Task.o: src/Task.cpp
	g++ $(CFLAGS) -o bin/Task.o src/Task.cpp

bin/Protocol.o: src/Protocol.cpp
	g++ $(CFLAGS) -o bin/Protocol.o src/Protocol.cpp


.PHONY: clean
clean:
	rm -f bin/*
