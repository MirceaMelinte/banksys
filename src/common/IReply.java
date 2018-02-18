package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IReply extends Remote
{
   void replyMessage(String msg) throws RemoteException;
}