package bgu.spl.net.srv.messages;

import bgu.spl.net.impl.BGRSServer.ServerProtocol;
import bgu.spl.net.impl.BGRSServer.User;

import java.nio.ByteBuffer;

public class Login extends Message{
    private String userName,password;
    public Login(String userName,String password)
    {
        opCode=3;
        this.userName=userName;
        this.password=password;
    }

    @Override
    public Message execute(ServerProtocol protocol) {
        //if there is no user logged in right now, continue.
        if (protocol.getUser() == null) {
            //we try to login, if we could login in the database,
            //we update the protocol there is a logged in user and return ack.
            //else return error.
            User s = database.login(userName,password);
            success=s!=null;
            if(success)
            {
                protocol.setUser(s);
                return new ACKMessage(this);

            }
        }

        return new ERRORMessage(opCode);
    }

    @Override
    public byte[] output() {
        //we return the 4 bytes of the op messages.
        byte[] opNum=shortToBytes((short) 12);
        byte[] opNUmberMessage=shortToBytes(opCode);
        ByteBuffer buffer= ByteBuffer.wrap(new byte[opNum.length+opNUmberMessage.length]);
        buffer.put(opNum);
        buffer.put(opNUmberMessage);
        return buffer.array();
    }
}
