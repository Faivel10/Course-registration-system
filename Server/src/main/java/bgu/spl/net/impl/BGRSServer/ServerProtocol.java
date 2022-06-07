package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.srv.messages.Message;

public class ServerProtocol implements MessagingProtocol<Message> {
    private boolean shouldTerminate;
    //getting the instance of the database.
    private static final Database database=Database.getInstance();
    private User user=null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    //we call the execute method of the message
    @Override
    public Message process(Message msg) {
        return msg.execute(this);
    }



    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
    public void setShouldTerminate(boolean set)
    {shouldTerminate=set;}
}
