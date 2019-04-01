package P2P;

// 6. Client main class, passes information from screen to server, connects to chatserver and owns callback objects

import java.rmi.Naming;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class client {

// Get args
private static Map<String, String> arguments = new HashMap<>();

private static void populateArguments(String args[]){


        // Collect args
        String type = "";
        boolean flag_x = false;

        for (String s:args) {

                if (s.charAt(0) == '-' && (s.charAt(1) == 'S' || s.charAt(1) == 'N' || s.charAt(1) == 'L' || s.charAt(1) == 'A'))
                        type = s;
                else{
                        if (type.equals(""))
                                throw new RuntimeException("Invalid Arguments, Format: java client -SH localhost -N name -L x y -A age ");

                        if (!type.equalsIgnoreCase("-L")) {
                                arguments.put(type, s);
                                type = ""; // flag after put in the arguments to hashmap
                        }

                        else {
                                if (flag_x) {
                                        arguments.put("y", s); // if x added then add y
                                }
                                else{
                                        arguments.put("x", s);
                                        flag_x = true; // add x and flag_x to add y next time.

                                }

                        }

                }
        }
        //  arguments.forEach((key, value)->System.out.println(key + ":" + value));




}


public static void main(String args[]){
        try{

                // ****
                populateArguments(args);

                // Server Interface object
                ChatServerInterface chatserver = (ChatServerInterface) Naming.lookup("//" + arguments.get("-SH") + "/ChatServer");

                // System.out.println("\nConnecting to: " + "//" + arguments.get("-SH") + "/ChatServer");

                UserInterface user = chatserver.register(   arguments.get("-N"),
                                                            Double.parseDouble(arguments.get("x")),
                                                            Double.parseDouble(arguments.get("y")),
                                                            Integer.parseInt(arguments.get("-A")) ); // User obj of server

                UserCallBackIF callbackobj = new UserCallBackImpl(); // CB object of this
                chatserver.addUserCallBack(user, callbackobj);
                // ****


                Scanner scanner = new Scanner(System.in);
                String message;

                while(true) {
                        try{
                                // System.out.print("> ");
                                message = scanner.nextLine();

                                // 1. send user-id msg || p2p part
                                if (message.startsWith("send")) {

                                        String [] str = message.split(" ", 3);

                                        int to = Integer.parseInt(str[1]);

                                        String msg = str[2];
                                        msg = user.getName() + " says: " + msg;

                                        // Assuming very few clients, linear search
                                        boolean sent = false;

                                        for(UserInterface u:user.getNearMe()) {
                                                if (u.getID() == to) {
                                                        u.getCB().sendMessageToMe(msg); // get CB obj of the matched user and send message to him
                                                        System.out.println("You have successfully sent a message to: " + u.getName());
                                                        System.out.print("> ");
                                                        sent = true;
                                                }
                                        }

                                        if (!sent) {
                                                System.out.print("Please try again; No user with user-id: " + to + "\n> ");
                                        }

                                }

                                // 2. quit
                                else if (message.equalsIgnoreCase("quit")) {
                                        boolean done = chatserver.deRegister(user);

                                        if (done) {
                                                System.out.println("Exiting..!!");
                                                break;
                                        }
                                }

                                // 3. server-client part
                                else chatserver.execute(user, message); // (from, command)


                        }
                        catch (Exception e) {
                                System.out.println("Stack Trace: " + e);
                                System.out.println("Hint: Check the command, make sure you didn't miss any argument, or used a character instead of numeric value..");
                                chatserver.sendCommands(user);
                        }

                }

                System.out.println("Thanks for using this service, bye!!");
                System.exit(0);
        }
        catch (Exception e) {
                System.out.println("Client Exception: " + e);
                System.out.println("Major error, exiting..");
                System.exit(0);
        }
}
}
