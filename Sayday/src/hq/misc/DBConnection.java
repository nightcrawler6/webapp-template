package hq.misc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static final String USER = "root";
	private static final String PASSWORD = "zubur1";
	private static final String CONNECTION = "jdbc:mysql://localhost:3306/catalina";
	
	public static Connection myConnection = getInstance();

	public DBConnection(){
		
	}
	
	public static Connection getInstance(){
		if(myConnection == null){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				myConnection = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
			} catch (SQLException e) {
				System.out.println("Could not establish connection with DB, is SQL service on?");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e){
				e.printStackTrace();
			}
		}	
		return myConnection;
	}
}
