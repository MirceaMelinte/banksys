package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObservable extends Remote
{
   void attach(IObserver observer) throws RemoteException;
   void detach(IObserver observer) throws RemoteException;
}
