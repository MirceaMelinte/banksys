package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

import model.Account;
import model.Transaction;
import common.ILogicServer;
import common.IReply;

public class CustomerClient extends UnicastRemoteObject implements IReply
{
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private ILogicServer logicServer;
   
   public CustomerClient() throws RemoteException
   {
      super();
   }
   
   public void begin()
   {
      try {
         String ip = "localhost";
         String URL = "rmi://" + ip + "/" + "logicServer";
         
         logicServer = (ILogicServer) Naming.lookup( URL );
         
         System.out.println("Customer Client connection established");
      } catch( Exception ex ) {
         ex.printStackTrace();
      }
   }
   
   public void withdraw(int accNo, String customerName, double amount) throws RemoteException, SQLException
   {
      Account a = new Account(accNo, customerName);
      Transaction t = new Transaction("withdraw", a, amount);
      
      logicServer.validateWithdraw(t, this);
   }
   
   public static void main(String[] args) throws RemoteException, SQLException
   {
      CustomerClient c = new CustomerClient();
      
      c.begin();
      
      c.withdraw(2, "Bar Barsen", 2);
   }

   @Override
   public void replyMessage(String msg) throws RemoteException
   {
      System.out.println(msg);
   }
}
