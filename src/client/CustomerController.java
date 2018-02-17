package client;

import java.rmi.Naming;

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
      } catch( Exception ex ) {
         ex.printStackTrace();
      }
   }
   
   public void withdraw(int accNo, String customerName, double amount)
   {
      Account a = new Account(accNo, customerName);
      Transaction t = new Transaction("withdraw", a, amount);
      
      logicServer.createWithdraw(t);
   }
}
