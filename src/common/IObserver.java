package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

import model.Transaction;

public interface IObserver extends Remote
{
   void update(Transaction transaction) throws RemoteException;
}
