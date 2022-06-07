package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.srv.Server;

public class TPCMain {

    public static void main(String[] args) {
        //creating database instance
        Database serverDatabase = Database.getInstance();

        //getting the port the and factories
        Server.threadPerClient(
                Integer.parseInt(args[0]), //port
                () -> new ServerProtocol(), //protocol factory
                ()-> new ServerEncoderDecoder() //message encoder decoder factory
        ).serve();
    }

}
