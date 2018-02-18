package client.view;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Scanner;

import client.AdministratorClient;

public class AdministratorView {
	private synchronized static void run() throws RemoteException, SQLException {
		Scanner sc = new Scanner(System.in);
		AdministratorClient admin = new AdministratorClient();
		admin.begin();

		for (;;) {
			System.out.println("[INPUT] Please enter the name of the customer to create an account: ['exit' to quit]");
			String name = sc.nextLine();
			
			if (name.equals("exit")) break;
			
			admin.createAccount(name);
			System.out.println();
		}
		
		System.out.println("[CLIENT] Good bye!");
		sc.close();
		System.exit(0);
	}
	
	public static void main(String[] args) {
		try {
			AdministratorView.run();
		} catch (RemoteException e) { } catch (SQLException e) { }
	}
}