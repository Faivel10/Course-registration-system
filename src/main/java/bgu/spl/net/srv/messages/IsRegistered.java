package bgu.spl.net.srv.messages;

import bgu.spl.net.impl.BGRSServer.ServerProtocol;

import java.nio.ByteBuffer;

public class IsRegistered extends Message{
    private String courseNum;
    String answer;
    public IsRegistered(String courseNum)
    {
        opCode=9;
        this.courseNum=courseNum;
    }
    @Override
    //if there is a user logged in to the client continue.
    public Message execute(ServerProtocol protocol) {
        //we check if the user is registered and output accoringly.
        if (protocol.getUser() != null) {

            success = database.isRegistered(protocol.getUser(), Integer.parseInt(courseNum));
            if (success) {
                answer = "REGISTERED".trim();
            } else {
                answer = "NOT REGISTERED".trim();
            }

            return new ACKMessage(this);
        }
        return new ERRORMessage(opCode);
    }

    @Override
    public byte[] output() {
        //outputing the message with the string value.
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
