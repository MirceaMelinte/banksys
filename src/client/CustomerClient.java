package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;

import model.Account;
import model.Transaction;
import common.ILogicServer;

public class CustomerClient
{
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
      String reply = logicServer.validateWithdraw(t);
      
      System.out.println(reply);
   }
}
