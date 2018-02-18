package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;

import model.Account;
import common.ILogicServer;

public class AdministratorClient
{
   private ILogicServer logicServer;
   
   public AdministratorClient()
   {
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
      
      logicServer.validateNewAccount(a);
   }
   
   public static void main(String[] args) throws RemoteException, SQLException
   {
      AdministratorClient c = new AdministratorClient();
      
      c.begin();
      
      c.createAccount("Bo Brunsgaard");
   }
}
