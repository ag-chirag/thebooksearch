
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class DeleteBookName {

	public  Connection connection = null;
	public  Statement stmt = null; 

	public void deleteBook(String book_title)
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
		
		System.out.println(book_title);
		try {
			stmt = connection.createStatement();		
			String query  = "DELETE from documents"+
			                " where book_title= '" + book_title + "'";
			stmt.executeUpdate(query);
			
			String query2  = "DELETE from revindex"+
	                " where book_title= '" + book_title + "'";
			stmt.executeUpdate(query2);
			
			String query3  ="DELETE from books"+
	                " where book_title= '" + book_title + "'";
			stmt.executeUpdate(query3);

			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
}
