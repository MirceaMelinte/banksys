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

public class LogicServer extends UnicastRemoteObject implements ILogicServer
{
   /**
    * 
    */
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
   public void validateWithdraw(Transaction transaction) throws SQLException,
      RemoteException
   {
         dataServer.executeWithdraw(transaction);
   }
   
   public static void main(String[] args) throws RemoteException
   {
      LogicServer l = new LogicServer();
      
      l.begin();
   }

   @Override
   public void validateDeposit(Transaction transaction) throws SQLException,
         RemoteException
   {
      dataServer.executeDeposit(transaction);
      
   }

   @Override
   public void validateNewAccount(Account account) throws SQLException,
         RemoteException
   {
      dataServer.executeNewAccount(account);
   }

   
}
