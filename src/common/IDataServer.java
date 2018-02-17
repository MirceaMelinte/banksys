package common;

import java.rmi.Remote;

import model.Transaction;

public interface IDataServer extends Remote
{
   void executeWithdraw(Transaction transaction);
}
