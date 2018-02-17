package logic;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import model.Transaction;
import common.IDataServer;
import common.ILogicServer;

public class LogicServer extends UnicastRemoteObject 
   implements ILogicServer
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
   
   public void begin()
   {
      try {
         LocateRegistry.createRegistry( 1099 );
         
         Naming.rebind("logicServer", this);
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

   @Override
   public void createWithdraw(Transaction transaction)
   {
      // TODO Auto-generated method stub
      
   }

   
}
