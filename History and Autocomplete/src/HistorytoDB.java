import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;


public class HistorytoDB {

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
	

	
	HistorytoDB(String query)
	{
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		String[] root = dateFormat.format(date).split("\\s+");
	  connect();
	  try {
		stmt = connection.createStatement();
		String sql_put_history = "INSERT INTO history(query,date,time)"+
								" VALUES('" + query +"', '" + root[0]+"', '"+root[1] + "')" ;
		stmt.executeUpdate(sql_put_history);
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	  
	}
}
