package bgu.spl.net.srv.messages;

import bgu.spl.net.impl.BGRSServer.ServerProtocol;

public class ACKMessage extends Message{
    private Message answerto;
    //we get the message that called the ack.
    public ACKMessage(Message m)
    {
        answerto=m;
        opCode=12;
    }
    //we output the message accoring to its output function.
    public byte[] output() {
        return answerto.output();
    }
    //we don't need to do anything because we never get this message.
    @Override
    public Message execute(ServerProtocol p) {
        return null;
    }

}
