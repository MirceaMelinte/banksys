package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Account;
import model.Transaction;

public class DataServer implements IDataServer {
	
	private static Connection connection;
	
	public DataServer() {
		DataServer.connection = PersistenceConfig.establishConnection(connection);
	}
	
	public void executeTest() throws SQLException { // test method
		PreparedStatement statement = 
				DataServer.connection.prepareStatement("SELECT * FROM account");
		
		ResultSet rs = statement.executeQuery();
		
		while(rs.next())
			System.out.println(rs.getString(2));
		
		rs.close();
		statement.close();
	}
	
	@Override
	public void executeWithdraw(Transaction transaction) throws SQLException {
		PreparedStatement statement = 
				DataServer.connection.prepareStatement("UPDATE account SET balance = balance - ? "
														+ "WHERE acc_no = ? AND customer_name = ?");
		
		statement.setDouble(1, transaction.getAccount().getBalance());
		statement.setInt(2, transaction.getAccount().getAccNo());
		statement.setString(3, transaction.getAccount().getCustName());
		statement.executeQuery();

		statement.close();
		
		this.insertTransaction(transaction);
	}

	@Override
	public void executeDeposit(Transaction transaction) throws SQLException {
		PreparedStatement statement = 
				DataServer.connection.prepareStatement("UPDATE account SET balance = balance + ? "
														+ "WHERE acc_no = ? AND customer_name = ?");
		
		statement.setDouble(1, transaction.getAccount().getBalance());
		statement.setInt(2, transaction.getAccount().getAccNo());
		statement.setString(3, transaction.getAccount().getCustName());
		statement.executeQuery();

		statement.close();
		
		this.insertTransaction(transaction);
	}
	
	private void insertTransaction(Transaction transaction) throws SQLException {
		PreparedStatement statement = 
				DataServer.connection.prepareStatement("INSERT INTO transaction (acc_no, type, amount) "
															+ "VALUES (?, ?, ?)");
		
		statement.setInt(1, transaction.getAccount().getAccNo());
		statement.setString(2, transaction.getType());
		statement.setDouble(3, transaction.getAmount());
		statement.executeQuery();

		statement.close();
	}

	@Override
	public void executeNewAccount(Account account) throws SQLException {
		PreparedStatement statement = 
				DataServer.connection.prepareStatement("INSERT INTO account (customer_name, balance) "
															+ "VALUES (?, ?)");
		
		statement.setString(1, account.getCustName());
		statement.setDouble(2, account.getBalance());
		statement.executeQuery();

		statement.close();
	}
}