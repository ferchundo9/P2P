package P2P;



// 4. CB class behaviour implementation, used by Client to create and pass CB obj. where needed

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserCallBackImpl extends UnicastRemoteObject implements UserCallBackIF {

UserCallBackImpl () throws RemoteException {
}

@Override
public void sendMessageToMe(String message) throws RemoteException {

        System.out.print("\r" + message + "\n> ");

}


}
