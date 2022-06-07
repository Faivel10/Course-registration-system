package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.srv.Server;

public class ReactorMain {

    public static void main(String[] args) {
        //Initialize the database
        Database serverDatabase = Database.getInstance();

        //setting the port, number of threads and factories
        Server.reactor(
                Integer.parseInt(args[1]),
                Integer.parseInt(args[0]), //port
                () ->  new ServerProtocol(), //protocol factory
                ()-> new ServerEncoderDecoder() //message encoder decoder factory
        ).serve();

    }
}
