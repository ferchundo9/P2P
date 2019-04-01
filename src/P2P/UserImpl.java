package P2P;


// 5. Class to create user objects, used by server.

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;


public class UserImpl extends UnicastRemoteObject implements UserInterface {

private double x, y;
private int age;
private String name;
private int user_id;
private UserCallBackIF callBackObject;
private LinkedList<UserInterface> near_me;


UserImpl(String name, double x, double y, int age) throws RemoteException {

        this.name = name;
        this.age = age;
        this.x = x;
        this.y = y;
        near_me = new LinkedList<>();

}
@Override
public void setX(double x) throws RemoteException {
        this.x = x;

}

@Override
public double getX() throws RemoteException {
        return this.x;
}

@Override
public int getAge() throws RemoteException {
        return this.age;
}

@Override
public void setY(double y) throws RemoteException {
        this.y = y;

}

@Override
public double getY() throws RemoteException {
        return this.y;
}


@Override
public String getName() throws RemoteException {
        return this.name;
}

@Override
public void setID(int user_id) throws RemoteException {
        this.user_id = user_id;

}

@Override
public int getID() throws RemoteException {
        return this.user_id;

}


@Override
public void setCB(UserCallBackIF callBackObject) throws RemoteException {
        this.callBackObject = callBackObject;
}

@Override
public UserCallBackIF getCB() throws RemoteException {
        return this.callBackObject;
}

@Override
public void sendLinkedListToMe(LinkedList<UserInterface> near_me) throws RemoteException {
        this.near_me = near_me;

}

@Override
public LinkedList<UserInterface> getNearMe() throws RemoteException {      // returns the ref to listed neighbors
        return near_me;
}

}
