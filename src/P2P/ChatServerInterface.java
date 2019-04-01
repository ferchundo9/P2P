package P2P;

// 2. FactoryIF implemented by Server used by Client, to connect to server obj and use server methods

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServerInterface extends Remote {

UserInterface register(String name, double x, double y, int age) throws RemoteException;         // server creates a user obj, adds user to list, assign unique ID
boolean deRegister(UserInterface userObj) throws RemoteException;         // removes client from the list, returns true when done
void execute(UserInterface userObj, String command) throws RemoteException;      // clients sends the command to the server, server needs to know from whom this message comes
void getLocation(int user_id) throws RemoteException;          // sends location in format "(x, y)"
void changeLocationBy(UserInterface userObj, double dx, double dy) throws RemoteException;          // sends new location in format "(x, y)"
void list(UserInterface userObj, double radius) throws RemoteException;         // returns the list of users within radius
void addUserCallBack(UserInterface userObj, UserCallBackIF userCallBackObj) throws RemoteException;          // adds users cb obj to User obj
void sendCommands(UserInterface userObj) throws RemoteException;      // sends commands via CB

}
