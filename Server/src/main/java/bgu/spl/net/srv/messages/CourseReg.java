package bgu.spl.net.srv.messages;

import bgu.spl.net.impl.BGRSServer.ServerProtocol;

import java.nio.ByteBuffer;

public class CourseReg extends Message{
    private short courseNum;
    public  CourseReg(short courseNum)
    {
        opCode=5;
        this.courseNum=courseNum;
    }
    //execute happens when we get the message and handle it.
    @Override
    public Message execute(ServerProtocol protocol) {
        //if there is an online user that is calling the function we continue.
        if (protocol.getUser() != null) {
            //trying to register to the course.
            success = database.courseRegister(protocol.getUser(), courseNum);
            if(success)
            return new ACKMessage(this);
        }
        return new ERRORMessage(opCode);
    }
    //outputing the needed  values.
    @Override
    public byte[] output() {
        byte[] opNum=shortToBytes((short) 12);
        byte[] opNUmberMessage=shortToBytes(opCode);
        ByteBuffer buffer= ByteBuffer.wrap(new byte[opNum.length+opNUmberMessage.length]);
        buffer.put(opNum);
        buffer.put(opNUmberMessage);
        return buffer.array();
    }
}
