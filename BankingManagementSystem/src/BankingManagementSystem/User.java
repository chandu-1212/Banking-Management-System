package BankingManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
	private Connection connection;
	private Scanner scanner;

	public User(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;
	}

	public void register() {
		scanner.nextLine();
		System.out.println("Full Name:");
		String full_name = scanner.nextLine();
		System.out.println("Email: ");
		String email = scanner.nextLine();
		System.out.println("Password: ");
		String password =scanner.nextLine();
		
		if(user_exit(email)) {
			System.out.println("User already Exists");
			return;
		}
		String register_query="INSERT INTO user(fullName, email, password) VALUES(?, ?, ?)";
		try{
			PreparedStatement ps=connection.prepareStatement(register_query);
			ps.setString(1, full_name);
			ps.setString(2, email);
			ps.setString(3, password);
			int affectedRows = ps.executeUpdate();
			if(affectedRows>0) {
				System.out.println("Registration Successfull!!");
			}else {
				System.out.println("Registration Failed");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	public boolean user_exit(String email) {
		String query ="SELECT * FROM user WHERE email = ?";
		try {
			PreparedStatement ps= connection.prepareStatement(query);
			ps.setString(1,email);
			ResultSet rs = ps.executeQuery();
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

	public String login() {
		scanner.nextLine();
		System.out.print("Email: ");
		String email =scanner.nextLine();
		System.out.print("Password: ");
		String password = scanner.nextLine();
		String Login_query ="SELECT * FROM user WHERE email = ? AND password =?";
		try {
			PreparedStatement ps = connection.prepareStatement(Login_query);
			ps.setString(1, email);
			ps.setString(2,password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return email;
			}else {
				return null;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
		
	}

}

















