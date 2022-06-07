package bgu.spl.net.srv.messages;

import bgu.spl.net.impl.BGRSServer.Course;
import bgu.spl.net.impl.BGRSServer.ServerProtocol;

import java.nio.ByteBuffer;
import java.util.LinkedList;

public class MyCourses extends Message{
    private String answer;
    public MyCourses()
    {
        opCode=11;
    }
    @Override
    public Message execute(ServerProtocol protocol) {
        //if there is a logged in user and its not admin continue.
        //we get the messages of the user.
        if (protocol.getUser() != null) {
            LinkedList<Course> linkedList = database.UserCourses(protocol.getUser());
            success = linkedList != null;
            if (success) {
                //returning the list of the courses.
                //sorted thanks to the database function.
                answer = outputList(linkedList);

                return new ACKMessage(this);
            }
        }
        return new ERRORMessage(opCode);
    }

    private String outputList(LinkedList<Course> linkedList) {
        String output="[";
        if(linkedList.isEmpty())
            return "[]";
    else {
            for (Course c : linkedList) {
                output =output+c.getNumCourse()+",";
            }
            output=output.substring(0,output.length()-1)+"]";
        }
    return output;
    }

    @Override
    public byte[] output() {
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
