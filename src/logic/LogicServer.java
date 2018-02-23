package logic;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Account;
import model.Transaction;

import common.IDataServer;
import common.ILogicServer;
import common.IObservable;
import common.IObserver;

public class LogicServer extends UnicastRemoteObject 
   implements ILogicServer, IObservable
{
   private static final long serialVersionUID = 1L;
   private IDataServer dataServer;
   private ArrayList<IObserver> observers;
   
   public LogicServer() throws RemoteException
   {
      super();
      observers = new ArrayList<>();
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
   public String validateWithdraw(Transaction transaction) 
         throws SQLException, RemoteException
   {
         if(dataServer.executeWithdraw(transaction))
         {
            this.notifyObservers(transaction);
            
            return "[SUCCESS] Withdrawal from the account: "
                  + transaction.getAccount().getAccNo() + " was succesful";  
         }
         else
         {
            return "[ERROR] Withdrawal from the account: "
                  + transaction.getAccount().getAccNo() + " was unsuccesful";
         }
   }

   @Override
   public String validateDeposit(Transaction transaction) throws SQLException,
         RemoteException
   {
      if(dataServer.executeDeposit(transaction))
      {
         this.notifyObservers(transaction);
         
         return "[SUCCESS] Deposit from the account: "
               + transaction.getAccount().getAccNo() + " was succesful";
      }
      else
      {
         return "[ERROR] Deposit from the account: "
               + transaction.getAccount().getAccNo() + " was unsuccesful";
      }
   }

   @Override
   public String validateNewAccount(Account account) throws SQLException,
         RemoteException
   {
      if(dataServer.executeNewAccount(account))
      {
         return "[SUCCESS] Creating a new account for customer: "
               + account.getCustName() + " was succesful";
      }
      else
      {
         return "[ERROR] Creating a new account for customer: "
               + account.getCustName() + " was unsuccesful";
      }
   }

   @Override
   public void attach(IObserver observer) throws RemoteException
   {
      observers.add(observer);
   }

   @Override
   public void detach(IObserver observer) throws RemoteException
   {
      observers.remove(observer);
   }
   
   private void notifyObservers(Transaction transaction)
   {
      for (IObserver observer : observers)
      {
         try
         {
            observer.update(transaction);
         }
         catch (RemoteException e)
         {
            e.printStackTrace();
         }
      }
   }
   
   public static void main(String[] args) throws RemoteException
   {
      LogicServer l = new LogicServer();
      l.begin();
   }
}
