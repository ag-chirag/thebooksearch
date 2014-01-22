import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Documents {

	
	public static Connection connection = null;
	public static Statement stmt = null;
	public	Documents(String path_book,String book_title)
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

	  
	try {
		stmt = connection.createStatement();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
//	String book_title = "Computer Networks";
	int number_of_pages= new File(path_book).listFiles().length;
	try{
	for(int i=1;i<= number_of_pages;i++)
	{
		
			String sql = "INSERT INTO documents(book_title,page_no) "+
		                 "Values("+ "'" + book_title + "'," + i + ")";
		    stmt.executeUpdate(sql);
	}
	}catch(SQLException se){se.printStackTrace();}
}
		
}
