package bgu.spl.net.srv.messages;

import bgu.spl.net.impl.BGRSServer.Database;
import bgu.spl.net.impl.BGRSServer.ServerProtocol;
/**
 *THis class is used so all the message classes have to be its children
 * and have the execute method - resposible to do the task
 * and the output message - responsible for the output to the client.
 *
 */
public abstract class Message {
    protected short opCode;
    //success helps know if the message was made in the database successfully.
    protected boolean success=false;
    protected Database database=Database.getInstance();

    public abstract Message execute(ServerProtocol protocol);

    public abstract byte[] output();

    public byte[] shortToBytes(short num) {
        byte[] bytesarray = new byte[2];
        bytesarray[0] = (byte) ((num >> 8) & 0xFF);
        bytesarray[1] = (byte) (num & 0xFF);
        return bytesarray;
    }

    public String toString()
    {
        return "I'm answer :" +opCode;
    }

}
