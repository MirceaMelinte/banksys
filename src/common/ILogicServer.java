package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import model.Account;
import model.Transaction;

public interface ILogicServer extends Remote, IObservable
{
   String validateWithdraw(Transaction transaction) 
         throws SQLException, RemoteException;
   
   String validateDeposit(Transaction transaction) 
         throws SQLException, RemoteException;

   String validateNewAccount(Account account) 
         throws SQLException, RemoteException;
}
