package P2P;

// 8. Server driver, owns server implementation object, binds it to name.

import java.rmi.Naming;

public class server {

public static void main(String[] args) {

        try{

                Naming.rebind("ChatServer", new ChatServerImpl());
                System.out.println("Server is Ready, Listening for clients..");

        }
        catch (Exception e) {
                System.out.println ("Server failed: " + e);
        }
}
}
