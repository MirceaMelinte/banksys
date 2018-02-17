package common;

import java.rmi.Remote;

import model.Transaction;

public interface ILogicServer extends Remote
{
   void createWithdraw(Transaction transaction);
}
