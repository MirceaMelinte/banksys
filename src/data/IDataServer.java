package data;

import java.sql.SQLException;

import model.Account;
import model.Transaction;

public interface IDataServer {
	void executeWithdraw(Transaction transaction) throws SQLException;

	void executeDeposit(Transaction transaction) throws SQLException;

	void executeNewAccount(Account account) throws SQLException;
}
