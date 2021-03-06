package data;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import common.IDataServer;
import model.Account;
import model.Transaction;

public class DataServer extends UnicastRemoteObject 
   implements IDataServer {
	
	private static final long serialVersionUID = 1L;
	private static Connection connection;
	
	public DataServer() throws RemoteException {
		super();
		
		DataServer.connection = PersistenceConfig.establishConnection(connection);
	}
	
	public void begin()
	{
		try {
			LocateRegistry.createRegistry(1099);
			
			Naming.rebind("dataServer", this);
			
			System.out.println("Data server is running");
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean executeWithdraw(Transaction transaction) throws SQLException, RemoteException {
		try {
			PreparedStatement statement = 
					DataServer.connection.prepareStatement("UPDATE account SET balance = balance - ? "
															+ "WHERE acc_no = ? AND customer_name = ?");
			
			statement.setDouble(1, transaction.getAmount());
			statement.setInt(2, transaction.getAccount().getAccNo());
			statement.setString(3, transaction.getAccount().getCustName());
			statement.executeUpdate();

			statement.close();
			DataServer.connection.commit();
			
			this.insertTransaction(transaction);
			
			System.out.println("[SUCCESS] Successful withdraw execution on account: " 
									+ transaction.getAccount().getAccNo() 
									+ ", " + transaction.getAccount().getCustName());
			return true;
		}
		catch (SQLException e) {
			DataServer.connection.rollback();
			System.out.println("[FAIL] Failed withdraw execution on account: " 
					+ transaction.getAccount().getAccNo() 
					+ ", " + transaction.getAccount().getCustName());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean executeDeposit(Transaction transaction) throws SQLException, RemoteException {
		try {
			PreparedStatement statement = 
					DataServer.connection.prepareStatement("UPDATE account SET balance = balance + ? "
															+ "WHERE acc_no = ? AND customer_name = ?");
			
			statement.setDouble(1, transaction.getAmount());
			statement.setInt(2, transaction.getAccount().getAccNo());
			statement.setString(3, transaction.getAccount().getCustName());
			statement.executeUpdate();

			statement.close();
			DataServer.connection.commit();
			
			this.insertTransaction(transaction);

			System.out.println("[SUCCESS] Successful deposit execution on account: " 
					+ transaction.getAccount().getAccNo() 
					+ ", " + transaction.getAccount().getCustName());
			return true;
		}
		catch (SQLException e) {
			DataServer.connection.rollback();
			System.out.println("[FAIL] Failed deposit execution on account: " 
					+ transaction.getAccount().getAccNo() 
					+ ", " + transaction.getAccount().getCustName());
			e.printStackTrace();
		}
		return false;
	}
	
	private void insertTransaction(Transaction transaction) throws SQLException, RemoteException {
		PreparedStatement statement = 
				DataServer.connection.prepareStatement("INSERT INTO transaction (acc_no, type, amount) "
															+ "VALUES (?, ?, ?)");
		
		statement.setInt(1, transaction.getAccount().getAccNo());
		statement.setString(2, transaction.getType());
		statement.setDouble(3, transaction.getAmount());
		statement.execute();

		statement.close();
		DataServer.connection.commit();
	}

	@Override
	public boolean executeNewAccount(Account account) throws SQLException, RemoteException {
		try {
			PreparedStatement statement = 
					DataServer.connection.prepareStatement("INSERT INTO account (customer_name, balance) "
																+ "VALUES (?, ?)");
			
			statement.setString(1, account.getCustName());
			statement.setDouble(2, account.getBalance());
			statement.execute();

			DataServer.connection.commit();
			statement.close();

			System.out.println("[SUCCESS] Successful execution of new account creation. Name: " 
			      + account.getCustName());
			return true;
		}
		catch (SQLException e) {
			DataServer.connection.rollback();
			System.out.println("[FAIL] Failed execution of new account creation. Name: " + account.getCustName());
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) throws RemoteException
	{
		DataServer d = new DataServer();
      
		d.begin();
	}
}