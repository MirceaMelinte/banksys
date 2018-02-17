package client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;

import model.Account;
import model.Transaction;
import common.ILogicServer;

public class CustomerController
{
   private ILogicServer logicServer;
   
   public CustomerController()
   {
   }
   
   public void begin()
   {
      try {
         String ip = "localhost";
         String URL = "rmi://" + ip + "/" + "logicServer";
         
         logicServer = (ILogicServer) Naming.lookup( URL );
         
         System.out.println("Client connection established");
      } catch( Exception ex ) {
         ex.printStackTrace();
      }
   }
   
   public void withdraw(int accNo, String customerName, double amount) throws RemoteException, SQLException
   {
      Account a = new Account(accNo, customerName);
      Transaction t = new Transaction("withdraw", a, amount);
      
      logicServer.createWithdraw(t);
   }
   
   public static void main(String[] args) throws RemoteException, SQLException
   {
      CustomerController c = new CustomerController();
      
      c.begin();
      
      c.withdraw(2, "Bar Barsen", 2);
   }
}
