package bgu.spl.net.srv.messages;

import bgu.spl.net.impl.BGRSServer.ServerProtocol;

import java.nio.ByteBuffer;

public class Logout extends Message{

    public Logout()
    {
        opCode=4;
    }
    @Override
    public Message execute(ServerProtocol protocol) {
        //if there is a user using the command we continue.
        if (protocol.getUser() != null) {
            //updating the database we logout.
            success = database.logout(protocol.getUser());
            if (success) {
                //updating the protocol there is no logged in user and we can terminate.
                protocol.setUser(null);
                protocol.setShouldTerminate(true);
                return new ACKMessage(this);
            }
        }
        return new ERRORMessage(opCode);
    }

    @Override
    public byte[] output() {
        //outputing the op codes.
        byte[] opNum=shortToBytes((short) 12);
        byte[] opNUmberMessage=shortToBytes(opCode);
        ByteBuffer buffer= ByteBuffer.wrap(new byte[opNum.length+opNUmberMessage.length]);
        buffer.put(opNum);
        buffer.put(opNUmberMessage);
        return buffer.array();
    }
}
