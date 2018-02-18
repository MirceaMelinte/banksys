package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

import model.Account;
import common.ILogicServer;
import common.IReply;

public class AdministratorClient extends UnicastRemoteObject implements IReply
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
         
         System.out.println("Administrator Client connection established");
      } catch( Exception ex ) {
         ex.printStackTrace();
      }
   }
   
   public void createAccount(String customerName) 
         throws RemoteException, SQLException
   {
      Account a = new Account(customerName, 0);
      
      logicServer.validateNewAccount(a, this);
   }
   
   public static void main(String[] args) throws RemoteException, SQLException
   {
      AdministratorClient c = new AdministratorClient();
      
      c.begin();
      
      c.createAccount("Bo Brunsgaard");
   }

   @Override
   public void replyMessage(String msg) throws RemoteException
   {
      System.out.println(msg);
   }
}
