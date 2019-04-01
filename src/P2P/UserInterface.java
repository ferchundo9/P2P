package P2P;

// 1. Used to create Client Remote Objects by server: Server will get this info to create/registerClient and put into a Linked List

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface UserInterface extends Remote {

void setX(double x) throws RemoteException;           // from prompt
double getX() throws RemoteException;

void setY(double y) throws RemoteException;           // from prompt
double getY() throws RemoteException;

int getAge() throws RemoteException;

String getName() throws RemoteException;

void setID(int user_id) throws RemoteException;            // assign unique ID, done by server
int getID() throws RemoteException;

void setCB(UserCallBackIF callBackObject) throws RemoteException;           // adds callback object to user remote object
UserCallBackIF getCB() throws RemoteException;           // returns users CB object

LinkedList<UserInterface> getNearMe() throws RemoteException;       // returns the ref to listed neighbors
void sendLinkedListToMe(LinkedList<UserInterface> near_me) throws RemoteException;          // displays a message on creators screen


}
