package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import model.Account;
import model.Transaction;

public interface IDataServer extends Remote {
	boolean executeWithdraw(Transaction transaction) throws SQLException, RemoteException;

	boolean executeDeposit(Transaction transaction) throws SQLException, RemoteException;

	boolean executeNewAccount(Account account) throws SQLException, RemoteException;
}
