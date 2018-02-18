package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import model.Account;
import model.Transaction;

public interface ILogicServer extends Remote
{
   void validateWithdraw(Transaction transaction, IReply response) 
         throws SQLException, RemoteException;
   
   void validateDeposit(Transaction transaction, IReply response) 
         throws SQLException, RemoteException;

   void validateNewAccount(Account account, IReply response) 
         throws SQLException, RemoteException;
}
