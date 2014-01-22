import java.io.File;
import readfile.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



public class revindex {

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
	
	public revindex(String path_book,String book_title)
	{
		read read = new read();
		connect();	
		try
		{
			System.out.println("Creating reverse index database");
			stmt = connection.createStatement();
		}
		catch(SQLException se)
		 {
			se.printStackTrace();
		  }

		read.reading(path_book,book_title);

	}
}
