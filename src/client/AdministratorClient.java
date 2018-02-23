package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.time.LocalDateTime;

import model.Account;
import model.Transaction;
import common.ILogicServer;
import common.IObserver;

public class AdministratorClient extends UnicastRemoteObject implements IObserver
{
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private ILogicServer logicServer;
   
   public AdministratorClient() throws RemoteException
   {
      super();
   }
   
   public void begin()
   {
      try {
         String ip = "localhost";
         String URL = "rmi://" + ip + "/" + "logicServer";
         
         logicServer = (ILogicServer) Naming.lookup( URL );
        
         logicServer.attach(this);
         
         System.out.println("Administrator Client connection established");
      } catch( Exception ex ) {
         ex.printStackTrace();
      }
   }
   
   public void createAccount(String customerName) 
         throws RemoteException, SQLException
   {
      Account a = new Account(customerName, 0);
      String reply = logicServer.validateNewAccount(a);
      
      System.out.println(reply);
   }

   @Override
   public void update(Transaction transaction) throws RemoteException
   {
      System.out.println("Transaction: " + transaction.getType() 
            + ", Account no: " + transaction.getAccount().getAccNo()
            + ", Customer name: " + transaction.getAccount().getCustName()
            + ", Time: " + LocalDateTime.now());
      transaction.toString();
   }
}
