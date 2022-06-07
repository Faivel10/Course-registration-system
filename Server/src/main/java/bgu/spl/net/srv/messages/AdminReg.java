package bgu.spl.net.srv.messages;

import bgu.spl.net.impl.BGRSServer.ServerProtocol;
import bgu.spl.net.impl.BGRSServer.User;

import java.nio.ByteBuffer;

public class AdminReg extends Message{
    private String userName,password;

    public AdminReg(String userName,String password)
    {
        this.userName=userName;
        this.password=password;
        opCode=1;
    }
    //calling execute from the protocol after getting the message.
    public Message execute(ServerProtocol protocol)
    {
        //if no user is logged in
        if(protocol.getUser()==null) {
            User s = new User(true, userName, password);
            //we try to register the account of the admin
            success = database.RegisterAccount(s);

            if (success)
                return new ACKMessage(this);
        }
        return new ERRORMessage(opCode);
    }
    //we only need to output the ack message with the op code of the message.
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
