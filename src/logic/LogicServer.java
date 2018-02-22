package logic;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

import model.Account;
import model.Transaction;
import common.IDataServer;
import common.ILogicServer;
import common.IReply;

public class LogicServer extends UnicastRemoteObject 
   implements ILogicServer
{
   private static final long serialVersionUID = 1L;
   private IDataServer dataServer;
   
   public LogicServer() throws RemoteException
   {
      super();
   }
   
   public void begin() throws RemoteException
   {
      try {       
         LocateRegistry.getRegistry(1099); // Registry is created on dataServer, here we just get it
         
         Naming.rebind("logicServer", this);
         
         String ip = "localhost";
         String URL = "rmi://" + ip + "/" + "dataServer";
         
         dataServer = (IDataServer) Naming.lookup( URL );
         
         System.out.println("Logic server is running");
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   @Override
   public void validateWithdraw(Transaction transaction, IReply response) 
         throws SQLException, RemoteException
   {
         if(dataServer.executeWithdraw(transaction))
         {
            response.replyMessage("[SUCCESS] Withdrawal from the account: "
                  + transaction.getAccount().getAccNo() + " was succesful");
         }
         else
         {
            response.replyMessage("[ERROR] Withdrawal from the account: "
                  + transaction.getAccount().getAccNo() + " was unsuccesful");
         }
   }

   @Override
   public void validateDeposit(Transaction transaction, IReply response) throws SQLException,
         RemoteException
   {
      if(dataServer.executeDeposit(transaction))
      {
         response.replyMessage("[SUCCESS] Deposit from the account: "
               + transaction.getAccount().getAccNo() + " was succesful");
      }
      else
      {
         response.replyMessage("[ERROR] Deposit from the account: "
               + transaction.getAccount().getAccNo() + " was unsuccesful");
      }
   }

   @Override
   public void validateNewAccount(Account account, IReply response) throws SQLException,
         RemoteException
   {
      if(dataServer.executeNewAccount(account))
      {
         response.replyMessage("[SUCCESS] Creating a new account for customer: "
               + account.getCustName() + " was succesful");
      }
      else
      {
         response.replyMessage("[ERROR] Creating a new account for customer: "
               + account.getCustName() + " was unsuccesful");
      }
   }

   public static void main(String[] args) throws RemoteException
   {
      LogicServer l = new LogicServer();
      
      l.begin();
   }
}
