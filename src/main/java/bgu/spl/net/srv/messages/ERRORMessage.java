package bgu.spl.net.srv.messages;

import bgu.spl.net.impl.BGRSServer.ServerProtocol;

import java.nio.ByteBuffer;

public class ERRORMessage extends Message{
    private short opMessage;
    public ERRORMessage(short opMessage)
    {
        this.opMessage=opMessage;
        opCode=13;
    }

    @Override
    public byte[] output() {
        //outputing the error message.
        byte[] opNUm=shortToBytes((short) 13);
        byte[] opMessageNum= shortToBytes(opMessage);
        ByteBuffer buffer= ByteBuffer.wrap(new byte[opNUm.length+opMessageNum.length]);
        buffer.put(opNUm);
        buffer.put(opMessageNum);
        return buffer.array();
    }

    @Override
    //no need to put in the execute anything since its never called from the client.
    public Message execute(ServerProtocol p) {
        return null;
    }
}
