package client.view;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Scanner;

import client.ClerkClient;

public class ClerkView {
	private synchronized static void run() throws RemoteException, SQLException {
		Scanner sc = new Scanner(System.in);
		ClerkClient clerk = new ClerkClient();
		clerk.begin();

		for (;;) {
			System.out.println("[INPUT] Please enter the transaction type (withdraw/deposit): ['exit' to quit]");
			String type = sc.nextLine();
			
			if (type.equals("exit")) break;
			
			System.out.print("[INPUT] Please enter the customer name: ");
			String name = sc.nextLine();
			
			System.out.println();
			System.out.print("[INPUT] Please enter the customer account number: ");
			int accountNumber = sc.nextInt();
			sc.nextLine();
			
			System.out.println();
			if (type.equals("withdraw")) {
				System.out.println("[INPUT] Please enter the amount you wish to withdraw: ");
				double amount = sc.nextDouble();

				clerk.withdraw(accountNumber, name, amount);
			}
			else if (type.equals("deposit")) {
				System.out.println("[INPUT] Please enter the amount you wish to deposit: ");
				double amount = sc.nextDouble();

				clerk.deposit(accountNumber, name, amount);
			}
			
			sc.nextLine(); // reset the scanner
			System.out.println();
		}
		
		System.out.println("[CLIENT] Good bye!");
		sc.close();
		System.exit(0);
	}
	
	public static void main(String[] args) {
		try {
			ClerkView.run();
		} catch (RemoteException e) { } catch (SQLException e) { }
	}
}