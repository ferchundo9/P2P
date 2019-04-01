package P2P;

// 7. Server main class, gets information from client create User objects, maintains a linkedlist of conected users.

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServerInterface {

private LinkedList<UserInterface> users;
private String commands = "Commands: \nget location\nlist <radius>\ngo <distance in meters +ve = North> <distance in meters +ve = East>\nsend <user-id> <msg>\n<quit>";

ChatServerImpl () throws RemoteException {
        users = new LinkedList<>();
}

@Override
public synchronized UserInterface register(String name, double x, double y, int age) throws RemoteException {
        UserInterface newUser = new UserImpl(name, x, y, age);
        newUser.setID(users.size() + 1);
        users.add(newUser);
        System.out.println("User joined: " + name);
        return newUser;
}

@Override
public synchronized boolean deRegister(UserInterface userObj) throws RemoteException {
        users.remove(userObj);
        System.out.println("User left: " + userObj.getName());
        return true;
}

@Override
public void execute(UserInterface user, String command) throws RemoteException {

        // get location, prints via CB
        if (command.equalsIgnoreCase("get location")) {
                this.getLocation(user.getID()); // Location in format "(x, y)"
        }

        // go dx dy, prints via CB
        else if (command.startsWith("go")) {

                String [] str = command.split(" ");
                double dx = Double.parseDouble(str[1]);
                double dy = Double.parseDouble(str[2]);
                this.changeLocationBy(user, dx, dy); // new Location in format "(x, y)"
        }

        // list radius, gets list via CB
        else if (command.startsWith("list")) {
                String [] str = command.split(" ");
                double radius = Double.parseDouble(str[1]);
                this.list(user, radius);

                if (user.getNearMe().size() != 0) {
                        for (UserInterface u:user.getNearMe()) {
                                user.getCB().sendMessageToMe("Name: " + u.getName() + " User ID: " + u.getID() + " Age: " + u.getAge());
                        }
                }
                else {
                        user.getCB().sendMessageToMe("No other user in the radius: " + radius +"m");

                }
        }

        // Invalid command
        else {
                user.getCB().sendMessageToMe("Invalid command: " + command);
                user.getCB().sendMessageToMe(commands);
        }

}

@Override
public void getLocation(int user_id) throws RemoteException {
        for(UserInterface u: users) {

                if(user_id == u.getID()) {
                        u.getCB().sendMessageToMe("(" + u.getX() + ", " + u.getY()+ ")");
                }
        }
}

@Override
public void changeLocationBy(UserInterface userObj, double dx, double dy) throws RemoteException {
        for (UserInterface u:users) {

                if(u.getID() == userObj.getID()) {
                        double newX = u.getX() + dx;
                        double newY = u.getY() + dy;
                        u.setX(newX);
                        u.setY(newY);
                        u.getCB().sendMessageToMe("(" + newX + ", " + newY + ")");

                }
        }
}

@Override
public void list(UserInterface userObj, double radius) throws RemoteException {

        LinkedList<UserInterface> neighbors = new LinkedList<>();

        for (UserInterface u:users) {

                if (u.getID() != userObj.getID()) {

                        double distance = Math.sqrt(Math.pow((u.getX() - userObj.getX()), 2) + Math.pow((u.getY() - userObj.getY()), 2));
                        if (distance <= radius) {
                                neighbors.add(u);
                        }

                }
        }
        userObj.sendLinkedListToMe(neighbors);
}

@Override
public void addUserCallBack(UserInterface userObj, UserCallBackIF userCallBackObj) throws RemoteException {

        for (UserInterface u:users) {
                if(u.getID() == userObj.getID()) {
                        // register user callback obj with the user remote object
                        u.setCB(userCallBackObj);
                        // send commands to the client
                        userCallBackObj.sendMessageToMe(commands);
                        userCallBackObj.sendMessageToMe("Please note your ID: " + u.getID());
                }
        }

}

@Override
public void sendCommands(UserInterface userObj) throws RemoteException {

        userObj.getCB().sendMessageToMe(commands);

}
}
