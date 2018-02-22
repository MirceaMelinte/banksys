package client.view;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Scanner;

import client.CustomerClient;

public class CustomerView {
	
	private static void run() throws RemoteException, SQLException {
		Scanner sc = new Scanner(System.in);
		CustomerClient customer = new CustomerClient();
		
		System.out.print("[INPUT] Please enter your name: ");
		String name = sc.nextLine();
		
		System.out.println();
		System.out.print("[INPUT] Please enter your account number: ");
		int accountNumber = sc.nextInt();
		sc.nextLine();
		
		customer.begin();
		
		for (;;) {
			System.out.println();
			System.out.println("[INPUT] Please enter the amount you wish to withdraw: (-1 to exit)");
			double amount = sc.nextDouble();
			
			if (amount == -1) break;
			
			customer.withdraw(accountNumber, name, amount);
		}
		
		System.out.println("[CLIENT] Good bye!");
		sc.close();
		System.exit(0);
	}
	
	public static void main(String[] args) {
		try {
			CustomerView.run();
		} catch (RemoteException e) { } catch (SQLException e) { }
	}
}