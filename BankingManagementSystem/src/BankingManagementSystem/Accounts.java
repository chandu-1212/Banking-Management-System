package BankingManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Accounts {
	
	private Connection connection;
	private Scanner scanner;
	

	public Accounts(Connection connection, Scanner scanner) {
		this.connection =connection;
		this.scanner=scanner;
	}

	public long open_account(String email) {
		if(!account_exit(email)) {
			String open_account_query = "INSERT INTO account1(accNo, fullName, email, balance, securityPin) VALUES(?, ?, ?, ?, ?) ";
			scanner.nextLine();
			System.out.print("Enter full Name: ");
			String fullName=scanner.nextLine();
			System.out.print("Enter Initial Account:");
			double balance =scanner.nextDouble();
			scanner.nextLine();
			System.out.print("Enter Security Pin: ");
			String security_pin=scanner.nextLine();
			try {
				long account_number =generateAccountNumber();
				PreparedStatement ps = connection.prepareStatement(open_account_query);
				ps.setLong(1,account_number);
				ps.setString(2,fullName);
				ps.setString(3, email);
				ps.setDouble(4, balance);
				ps.setString(5, security_pin);
				int affectedRows= ps.executeUpdate();
				if(affectedRows>0) {
					return account_number;
				}else {
					throw new RuntimeException("Account creation failed");
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	private long generateAccountNumber() {
		try {
			Statement stmt = connection.createStatement();
			
			ResultSet rs= stmt.executeQuery("SELECT accNo from account1 ORDER BY accNo DESC LIMIT 1");
			if(rs.next()) {
				long last_account_number=rs.getLong("accNo");
				return last_account_number+1;
			}else {
				return 10000100;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 10000100;
	}

	public boolean account_exit(String email) {
		String query ="SELECT accNo from account1 WHERE email =?";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs= ps.executeQuery();
			if(rs.next()) {
				return true;
			}else {
				return false;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

		
	public long getAccount_number(String email) {
		String query ="SELECT accNo from account1 WHERE email =?";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs= ps.executeQuery();
			if(rs.next()) {
				return rs.getLong("accNo");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Account Number Doesn't Exist!");
	}

}
