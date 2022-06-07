package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.srv.messages.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;

public class ServerEncoderDecoder  implements MessageEncoderDecoder<Message> {

    private byte[] bytes = new byte[1 << 10]; //start with 1k
    //the length of the message we got.
    private int len = 0;
    //number of zeroes we saw after the op code.
    private int numOfZeroes = 0;
    //arrays the represnt the byte sizes of the message accoring to the indexes
    //the count start from index one - each index represents the op code.
    //if there is a -1, then the message has custom strings in it.
    //the zeroes array for each index represents the op code.
    // if the value is -1 it means the message is determined by number of bytes.
    private final int[] byteSizes = {-1, -1, -1, -1, 2, 4, 4, 4, -1, 4, 4, 2};
    private final int[] zeroes = {-1, 2, 2, 2, -1, -1, -1, -1, 1, -1, -1, -1};
    private short opCode;
    private  boolean done=true;

    private boolean needToAdd=false;
    private boolean gotOpCode = false;
    //list of the string values we will get from the bytes.
    private LinkedList<String> list = new LinkedList<>();
    private short course=-1;
    int start = 2;

    @Override
    public Message decodeNextByte(byte nextByte) {
        pushByte(nextByte);
        len++;
        //check if we got the op code after  2 bites.
        if (len == 2) {

            opCode = bytesToShort(bytes);
            gotOpCode = true;

        }
        //if we got the opcode
        if (len > 2 || gotOpCode) {
            //if we got a message without custom string and its a mesage with information of 2 bites we  stop reading.
            //after the 2 bytes and return the message.
            if (byteSizes[opCode] != -1 && byteSizes[opCode] == len &&opCode!=4 &&opCode!=11) {
                course=bytesToShort(Arrays.copyOfRange(bytes,start,len));
                return popMessage();
                //if we got a meesage without information needed we return it.
            }else if(byteSizes[opCode] != -1 && byteSizes[opCode] == len &&opCode==4 || opCode==11)
            {
                return popMessage();
            }
            //if we got a zero and its a message with custom strings,
            //we increase the zeroes counter by one.
            else if (nextByte == '\0' && byteSizes[opCode]==-1) {
                numOfZeroes++;
                //if we got all the zeroes the message can have accoring to its op code.
                //we return it.
                if (zeroes[opCode] == numOfZeroes) {
                    list.addLast(new String(bytes, start, len, StandardCharsets.UTF_8));
                    start = len;
                    return popMessage();
                    //if we still need more information from the message,
                    //we add the info to to list and will return null.
                } else if (zeroes[opCode] != -1 && numOfZeroes<zeroes[opCode] ) {
                    list.addLast(new String(bytes, start, len, StandardCharsets.UTF_8));
                    start = len;
                }

            }

        }
        return null;
    }


    //translate bytes to short.
    public short bytesToShort(byte[] bytess) {
        short num;
        num=(short)((bytess[0] & 0xff)<< 8);
        num+=(short)(bytess[1] & 0xff );
        return num;
    }


    private Message popMessage() {
        Message m =null;
        //returning the right message according to the op code.
        //if the op code has information we need to pass we insert it in the message and reutrn it.
        switch (opCode)
        {
            case 1:
                m= new AdminReg(list.get(0),list.get(1));
                break;
            case 2:
                m=new StudentReg(list.get(0),list.get(1));
                break;
            case 3:
                m=new Login(list.get(0),list.get(1));
                break;
            case 4:
                m=new Logout();
                break;
            case 5:
                m=new CourseReg(course);
                break;
            case 6:
                m=new KDAMCheck(course);
                break;
            case 7:
                m=new CourseStat(course+"");
                break;
            case 8:
                m= new StudentStat(list.get(0));
                break;
            case 9:
                m=new IsRegistered(course+"");
                break;
            case 10:
                m=new Unregister(course+"");
                break;
            case 11:
                m=new MyCourses();
                break;
        }

        //we return all the values back to normal so we will be able to read a new message later.
        len = 0;
        numOfZeroes=0;
        start=2;
        list=new LinkedList<>();
        opCode=-1;
        gotOpCode=false;
      bytes = new byte[1 << 10]; //start with 1k

        return m;
    }
    //adding the byte to the array of bytes.
    //if its too long we update the array to be bigger.
    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }

        bytes[len] = nextByte;

    }

    //output the message.
    @Override
    public byte[] encode(Message message) {
        return message.output();
    }
}
