package bgu.spl.net.srv.messages;

import bgu.spl.net.impl.BGRSServer.ServerProtocol;

import java.nio.ByteBuffer;

public class CourseStat extends Message{
    private String numCourse;
    private String value;

    public CourseStat(String numCourse)
    {
        opCode=7;
        this.numCourse=numCourse;
        value="";
    }

    @Override
    public Message execute(ServerProtocol protocol) {
        //if we got a user that is calling the message and the user is an admin continue.
        if (protocol.getUser() != null && protocol.getUser().isAdmin()) {
            //if there is a course like that, we update the course stats string.
            success = database.hasCourse(numCourse);
            value = database.getCourse(protocol.getUser(), Integer.parseInt(numCourse)).toString();
            if(success)
                return new ACKMessage(this);
        }
        return new ERRORMessage(opCode);
    }

    @Override
    public byte[] output() {
        //outputing the values with the string needed.
        byte[] opNum=shortToBytes((short) 12);
        byte[] opNUmberMessage=shortToBytes(opCode);
        byte[] values = value.getBytes();
        ByteBuffer buffer= ByteBuffer.wrap(new byte[opNum.length+opNUmberMessage.length+values.length+1]);
        buffer.put(opNum);
        buffer.put(opNUmberMessage);
        buffer.put(values);
        return buffer.array();
    }
}
