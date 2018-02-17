package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import model.Account;
import model.Transaction;

public interface IDataServer extends Remote {
	void executeWithdraw(Transaction transaction) throws SQLException, RemoteException;

	void executeDeposit(Transaction transaction) throws SQLException, RemoteException;

	void executeNewAccount(Account account) throws SQLException, RemoteException;
}
