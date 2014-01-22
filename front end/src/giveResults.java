import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.mysql.jdbc.ResultSet;


public class giveResults {
	public  Connection connection = null;
	public  Statement stmt = null; 
	public  Vector<Integer> results_pages = new Vector();
	public  Vector<String> results_books = new Vector();
  giveResults()
  {
	  results_pages.clear();
	  results_books.clear();
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
		String query = "SELECT page_no,book_title FROM documents d,results r"+
						" WHERE r.doc_id=d.doc_id"+
						" ORDER BY r.score DESC";
		java.sql.ResultSet rs = stmt.executeQuery(query);
		
		
		while(rs.next())
		{
			results_pages.addElement(rs.getInt(1));
			results_books.addElement(rs.getString(2));
		}
		
		String sql5 = "TRUNCATE TABLE results";
    	try
	{
		stmt.executeQuery(sql5);
	}
	catch (SQLException e)
	{
		e.printStackTrace();
	}
		
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	
  }
  
  
}
