import stopwordsremoval.*;
import java.util.*;
import java.sql.*;
import java.io.FileReader;
import java.io.BufferedReader;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import org.tartarus.snowball.ext.*;

public class histfunc
{
	
	public  Connection connection = null;
	public  Statement stmt = null; 

	public void remove(String s)
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
		
		String[] roots = s.split("\\s+");
		String rem = roots[2];
		String date = roots[0];
		String time = roots[1];
		
		try 
		{
			System.out.println("hi from hisfunc");
			System.out.println(rem);
			System.out.println(date);
			System.out.println(time);
			stmt = connection.createStatement();
			String query = "DELETE FROM history WHERE query = " + "'" + rem + "'"
						   +" AND date='" + date +"'" 
						   +" AND time='" + time +"'" ;
			stmt.executeUpdate(query);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
}
