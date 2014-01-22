import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class ChangeBookName {

	public  Connection connection = null;
	public  Statement stmt = null; 

	public void bookChange(String new_name,String old_name)
	{
		try 
		 {
			Class.forName("com.mysql.jdbc.Driver");
		 } 
		catch (ClassNotFoundException e) 
		 {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		 }
	 
		System.out.println("MySQL JDBC Driver Registered!");
		
		Connection connection = null;
		Statement stmt = null;
	   try 
		{
			connection = DriverManager
			.getConnection("jdbc:mysql://localhost:3306/project","root", "");
	 
		} 
		catch (SQLException e)
		{
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 
		if (connection != null)
		{
			System.out.println("You made it, take control your database now!");
		} 
		else
		{
			System.out.println("Failed to make connection!");
		}
		
		System.out.println(new_name);
		System.out.println(old_name);
		try {
			stmt = connection.createStatement();		
			String query  = "UPDATE books"+
			                " set book_title= '" + new_name + "'"+
			                " where book_title= '" + old_name + "'";
			stmt.executeUpdate(query);
			
			String query2  = "UPDATE revindex"+
	                " set book_title= '" + new_name + "'"+
	                " where book_title= '" + old_name + "'";
			stmt.executeUpdate(query2);
			
			String query3  = "UPDATE documents"+
	                " set book_title= '" + new_name + "'"+
	                " where book_title= '" + old_name + "'";
			stmt.executeUpdate(query3);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
}
