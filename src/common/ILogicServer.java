package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import model.Transaction;

public interface ILogicServer extends Remote
{
   void createWithdraw(Transaction transaction) throws SQLException, RemoteException;
}
