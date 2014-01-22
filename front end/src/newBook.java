import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class newBook
{
	int id=0;
	public  Connection connection = null;
	public  Statement stmt = null; 

	public void addNewBook(String s,String st,String la)
	{
		
//		System.out.println("s:"+s);
//		System.out.println("st:"+st);
//		System.out.println("la:"+la);
		int start = Integer.parseInt(st);
		int last = Integer.parseInt(la);
		String[] root = s.split("\\/+");
		int l = root.length;
		String sm = new String(root[l-1].substring(0, root[l-1].length()-4));
		System.out.println("sm:"+sm);
		int no_of_pages = last-start;
		
		
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
		
		
		try
		{
			stmt = connection.createStatement();
			
		//	String query2 = "SELECT MAX(id) FROM books";
		//	ResultSet rs = stmt.executeQuery(query2);
	//		while(rs.next())
	//		{
	//			id = rs.getInt(1);
	//		}
			id++;
			String query1 = "INSERT INTO books(book_title,no_of_pages) "+
							" VALUES('" + sm + "'," + no_of_pages + ")";
			stmt.execute(query1);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		  ExtractPageContent epc= new ExtractPageContent();
		
		JOptionPane.showMessageDialog(null,"A Message Will be Displayed When the Book is Uploaded");

		String[] arguments = new String[] {s,st,la};
		  try {
			ExtractPageContent.main(arguments);
//			ExtractPageContent e = new ExtractPageContent(s,start,last);
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		String directory = "C:/"+sm+"/";
		Documents d = new Documents(directory,sm);
		Indexing i = new Indexing(directory,sm);
		revindex r = new revindex(directory,sm);
		
		JOptionPane.showMessageDialog(null,"Book Uploaded");
		
	}
}