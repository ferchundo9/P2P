package P2P;



// 3. Implemented by Client CB class used to create CB objects by the Client, passed to server to callback.

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserCallBackIF extends Remote {

void sendMessageToMe(String message) throws RemoteException;         // displays a message on creators screen


}
