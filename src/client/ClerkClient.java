package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

import model.Account;
import model.Transaction;
import common.ILogicServer;
import common.IReply;

public class ClerkClient extends UnicastRemoteObject implements IReply
{
   private static final long serialVersionUID = 1L;
   private ILogicServer logicServer;
   
   public ClerkClient() throws RemoteException
   {
      super();
   }
   
   public void begin()
   {
      try {
         String ip = "localhost";
         String URL = "rmi://" + ip + "/" + "logicServer";
         
         logicServer = (ILogicServer) Naming.lookup( URL );
         
         System.out.println("Clerk Client connection established");
      } catch( Exception ex ) {
         ex.printStackTrace();
      }
   }
   
   public void withdraw(int accNo, String customerName, double amount) 
         throws RemoteException, SQLException
   {
      Account a = new Account(accNo, customerName);
      Transaction t = new Transaction("withdraw", a, amount);
      
      logicServer.validateWithdraw(t, this);
   }
   
   public void deposit(int accNo, String customerName, double amount) 
         throws RemoteException, SQLException
   {
      Account a = new Account(accNo, customerName);
      Transaction t = new Transaction("deposit", a, amount);
      
      logicServer.validateDeposit(t, this);
   }  
   
   @Override
   public void replyMessage(String msg) throws RemoteException
   {
      System.out.println(msg);
   }
}
