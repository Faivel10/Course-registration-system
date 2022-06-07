package bgu.spl.net.srv.messages;

import bgu.spl.net.impl.BGRSServer.ServerProtocol;
import bgu.spl.net.impl.BGRSServer.User;

import java.nio.ByteBuffer;

public class StudentReg extends Message{
    private String userName,password;
    public StudentReg(String userName,String password)
    {
        opCode=2;
        this.userName=userName;
        this.password=password;
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

    @Override
    public Message execute(ServerProtocol protocol) {
        //if there is no logged in user continue.
        if (protocol.getUser() == null) {
            //try to register the user in the database if success return ack.
            User x = new User(false, userName, password);
            success = database.RegisterAccount(x);
            if (success) {
                return new ACKMessage(this);

            }
        }
        return new ERRORMessage(opCode);

    }
}
