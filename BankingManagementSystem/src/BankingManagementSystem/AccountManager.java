package BankingManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
	
	private Connection connection;
	private Scanner scanner;

	public AccountManager(Connection connection, Scanner scanner) {
		this.connection =connection;
		this.scanner=scanner;
	}

	public void debit_money(long account_number) throws SQLException {
		scanner.nextLine();
		System.out.print("Enter Amount: ");
		double amount =scanner.nextDouble();
		scanner.nextLine();
		System.out.print("Enter Security Pin: ");
		String security_pin=scanner.nextLine();
		
		try {
			connection.setAutoCommit(false);
			if(account_number !=0) {
				PreparedStatement ps= connection.prepareStatement("select * from account1 where accNO = ? and securityPin =?");
				ps.setLong(1, account_number);
				ps.setString(2,security_pin);
				
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					double current_balance = rs.getDouble("balance");
					if(amount<= current_balance) {
					String debit_query ="UPDATE account1 SET balance = balance - ? WHERE accNO =?";
					PreparedStatement ps1=connection.prepareStatement(debit_query);
					ps1.setDouble(1, amount);
					ps1.setLong(2, account_number);
					int affectedRows =ps1.executeUpdate();
					if(affectedRows>0) {
						System.out.println("Rs."+amount+ "debited Successfully");
						connection.commit();
						connection.setAutoCommit(true);
						return;
					}else {
						System.out.println("Transaction Failed!");
						connection.rollback();
						connection.setAutoCommit(true);
					}
				}else {
					System.out.println("Insufficient Balance!!!");
				}
			}else {
				System.out.println("Invalid pin!!");
			}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		connection.setAutoCommit(true);
		
	}

	public void credit_money(long account_number) throws SQLException {
		scanner.nextLine();
		System.out.print("Enter Amount: ");
		double amount =scanner.nextDouble();
		scanner.nextLine();
		System.out.print("Enter Security Pin: ");
		String security_pin=scanner.nextLine();
		
		try {
			connection.setAutoCommit(false);
			if(account_number !=0) {
				PreparedStatement ps= connection.prepareStatement("select * from account1 where accNO = ? and  securityPin =?");
				ps.setLong(1, account_number);
				ps.setString(2,security_pin);
				
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					String credit_query ="UPDATE account1 SET balance = balance + ? WHERE accNO =?";
					PreparedStatement ps1=connection.prepareStatement(credit_query);
					ps1.setDouble(1, amount);
					ps1.setLong(2, account_number);
					int affectedRows =ps1.executeUpdate();
					if(affectedRows>0) {
						System.out.println("Rs."+amount+ "credited Successfully");
						connection.commit();
						connection.setAutoCommit(true);
						return;
					}else {
						System.out.println("Transaction Failed!!!");
						connection.rollback();
						connection.setAutoCommit(true);
					}
				}else {
					System.out.println("Invalid Security Pin!!!");
				}
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		connection.setAutoCommit(true);
		
	}

	public void transfer_money(long account_number) throws SQLException {
		
		scanner.nextLine();
		System.out.print("Enter Receiver Amount Number: ");
		long receiver_account_number =scanner.nextLong();
		System.out.print("Enter sender Amount Number: ");
		long sender_account_number =scanner.nextLong();
		System.out.print("Enter Amount: ");
		double amount =scanner.nextDouble();
		scanner.nextLine();
		System.out.print("Enter Security Pin: ");
		String security_pin=scanner.nextLine();
		
		try {
			connection.setAutoCommit(false);
			if(sender_account_number !=0 && receiver_account_number!=0) {
				PreparedStatement ps= connection.prepareStatement("select * from account1 where accNO = ? and securityPin = ?");
				ps.setLong(1, account_number);
				ps.setString(2,security_pin);
				
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					double current_balance=rs.getDouble("balance");
					if(amount<=current_balance) {
						String debit_query ="UPDATE account1 SET balance = balance - ? WHERE accNO =?";
						String credit_query ="UPDATE account1 SET balance = balance + ? WHERE accNO =?";
						PreparedStatement creditPS=connection.prepareStatement(credit_query);
						PreparedStatement debitPS=connection.prepareStatement(debit_query);
						creditPS.setDouble(1, amount);
						creditPS.setLong(2, receiver_account_number);
						debitPS.setDouble(1, amount);
						debitPS.setLong(2, sender_account_number);
						int affectedRows1 =debitPS.executeUpdate();
						int affectedRows2 =creditPS.executeUpdate();
						if(affectedRows1>0 && affectedRows2>0) {
							System.out.println("Transaction Successfull!!!");
							System.out.println("Rs."+amount+ "Transaction Successfully");
							connection.commit();
							connection.setAutoCommit(true);
							return;
						}else {
							System.out.println("Transaction Failed!!!");
							connection.rollback();
							connection.setAutoCommit(true);
						}
					}else {
					System.out.println("Insufficient Balance!!!");
				}
				}else {
					System.out.println("Invalid Security Pin!!!");
				}
			}else {
				System.out.println("Invalid account number!!!");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		connection.setAutoCommit(true);
	}

	public void getBalance(long account_number) {
		scanner.nextLine();
		System.out.print("Enter Security Pin: ");
		String security_pin=scanner.nextLine();
		
		try {
			PreparedStatement ps= connection.prepareStatement("select balance from account1 where accNO = ? and securityPin =?");
			ps.setLong(1, account_number);
			ps.setString(2,security_pin);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				double balance = rs.getDouble("balance");
				System.out.println("Balance: "+balance);
		}else {
			System.out.println("Invalid Pin!!");

		}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

}
