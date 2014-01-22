import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Delete {
	
	public static	Connection connection = null;
	public static  Statement stmt = null;
	public static void connect()
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

	  }
	
	public static void main(String args[])
	{
		String book_title = new String();
		Scanner input = new Scanner(System.in);
		book_title = input.nextLine();
		connect();
		try {
			stmt = connection.createStatement();
		
			String delete = "DELETE FROM documents WHERE book_title=" + "'" + book_title + "'";
			stmt.executeUpdate(delete);
			
			String delete_revindex  = "DELETE FROM revindex WHERE book_title=" + "'" + book_title + "'";
			stmt.executeUpdate(delete_revindex);
			
			String delete_books  = "DELETE FROM books WHERE book_title=" + "'" + book_title + "'";
			stmt.executeUpdate(delete_books);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	}
