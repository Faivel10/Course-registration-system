package bgu.spl.net.srv.messages;

import bgu.spl.net.impl.BGRSServer.ServerProtocol;

import java.nio.ByteBuffer;

public class StudentStat extends Message{

    private String userName;
    private String value;
    public StudentStat(String userName)
    {
        this.userName=userName;
        opCode=8;
    }

    @Override
    public Message execute(ServerProtocol protocol) {
        //if there is a user logged in continue.
        if (protocol.getUser() != null) {
            success = database.getUser(protocol.getUser(), userName) != null;
            if (success) {
                value = database.getUser(protocol.getUser(), userName).toString();
                return new ACKMessage(this);
            }
        }
        return new ERRORMessage(opCode);
    }

    @Override
    public byte[] output() {
        //outputing the op codes with the stats. will be outputted after sort.
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
