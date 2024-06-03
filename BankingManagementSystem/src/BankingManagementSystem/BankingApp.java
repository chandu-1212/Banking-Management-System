package BankingManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {
	private static final String url="jdbc:mysql://localhost:3306/Banking_System"; 
	private static final String username="root"; 
	private static final String password="root123";

	public static void main(String[] args) throws ClassNotFoundException,SQLException {
		 try {
			 Class.forName("com.mysql.cj.jdbc.Driver");
		 }catch(ClassNotFoundException e) {
			 System.out.println(e.getMessage());
		 }
		 
		 try {
			 Connection connection =DriverManager.getConnection(url,username,password);
			 Scanner scanner=new Scanner(System.in);
			 User user =new User(connection, scanner);
			 Accounts accounts =new Accounts(connection, scanner);
			 AccountManager accountManager =new AccountManager(connection, scanner);
			 
			 String email;
			 long account_number;
			 
			 while(true) {
				 System.out.println("----Welcome To Banking System----");
				 System.out.println();
				 System.out.println("1. Register");
				 System.out.println("2. Login");
				 System.out.println("3. Exit");
				 System.out.println("Enter the choice: ");
				 int ch1 = scanner.nextInt();
				 switch(ch1) {
				 case 1:
					 user.register();
					 break;
					 
				 case 2:
					 email = user.login();
					 if(email!=null) {
						 System.out.println();
						 System.out.println("User Logged In!");
						 if(!accounts.account_exit(email)) {
							 System.out.println();
							 System.out.println("1. Open a new Bank Account");
							 System.out.println("2. Exit");
							 if(scanner.nextInt()==1) {
								 account_number = accounts.open_account(email);
								 System.out.println("Account Create Successfully");
								 System.out.println("Your Account Number is: " +account_number);
							}else {
								break;
							}
						 }
						 account_number  = accounts.getAccount_number(email);
						 int ch2 = 0;
						 while(ch2 !=5) {
							 System.out.println();
							 System.out.println("1. Debit Money");
							 System.out.println("2. Credit Money");
							 System.out.println("3.Transfer Money");
							 System.out.println("4. Check Balance");
							 System.out.println("5. Log Out");
							 
							 System.out.println("Enter the choice: ");
							 ch2 = scanner.nextInt();
							 switch(ch2) {
							 case 1:
								 accountManager.debit_money(account_number);
								 break;
							 case 2:
								 accountManager.credit_money(account_number);
								 break;
							 case 3:
								 accountManager.transfer_money(account_number);
								 break;
							 case 4:
								 accountManager.getBalance(account_number);
								 break;
							 case 5:
								 break;
							 default:
									 System.out.println("Enter Valid choice! ");
							 }	 
						 }
					 }else {
						 System.out.println("Incorrect Email or Password!");
					 }
				 case 3:
					 System.out.println("Thankyou for using banking system!!");
					 System.out.println("Exiting System");
					 return;
					 
				default:
					System.out.println("Enter Valid choice");
					break;
				 }
	 
			 }
			 
		 }catch(SQLException e) {
			 System.out.println(e.getMessage());
		 }
	}

}
