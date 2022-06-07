package bgu.spl.net.srv.messages;

import bgu.spl.net.impl.BGRSServer.ServerProtocol;

import java.nio.ByteBuffer;

public class Unregister extends Message {
    private String courseNum;

    public Unregister(String courseNum) {
        this.courseNum = courseNum;
        opCode = 10;
    }

    @Override
    public Message execute(ServerProtocol protocol) {
        //if there is a logged in user continue.
        if (protocol.getUser() != null) {
            //try to unregister in the database
            success = database.unRegisterCourse(protocol.getUser(), courseNum);
            if (success) {

                return new ACKMessage(this);
            }
       }
        return new ERRORMessage(opCode);
    }

    @Override
    public byte[] output() {
        byte[] opNum = shortToBytes((short) 12);
        byte[] opNUmberMessage = shortToBytes(opCode);
        ByteBuffer buffer = ByteBuffer.wrap(new byte[opNum.length + opNUmberMessage.length]);
        buffer.put(opNum);
        buffer.put(opNUmberMessage);
        return buffer.array();
    }
}