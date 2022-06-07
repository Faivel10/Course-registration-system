package bgu.spl.net.srv.messages;

import bgu.spl.net.impl.BGRSServer.ServerProtocol;

import java.nio.ByteBuffer;

public class KDAMCheck extends Message{
    private short numCourse;
    private String answer;

    public KDAMCheck(short numCourse){
        opCode=6;
        this.numCourse=numCourse;
    }
    @Override
    public Message execute(ServerProtocol protocol) {
        //if there is a logged in and the user is not admin we continue.
        if (protocol.getUser() != null && !protocol.getUser().isAdmin()) {
            success = database.hasCourse(numCourse + "");
            answer = database.KdamCheck(numCourse + "");
            if(success)
                return new ACKMessage(this);
        }
        return new ERRORMessage(opCode);
    }
    @Override
    public byte[] output() {
        //outputing the values  of the op with the string we got of the courses.
            byte[] opNum=shortToBytes((short) 12);
            byte[] opNUmberMessage=shortToBytes(opCode);
            byte[] values = answer.getBytes();
            ByteBuffer buffer= ByteBuffer.wrap(new byte[opNum.length+opNUmberMessage.length+values.length+1]);
            buffer.put(opNum);
            buffer.put(opNUmberMessage);
            buffer.put(values);
            return buffer.array();
        }
}
