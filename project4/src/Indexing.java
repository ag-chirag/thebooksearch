import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Vector;
import read_text.*;
import java.io.*;
import java.util.Collections;
import java.util.Date;
import java.io.FileReader;
import java.io.BufferedReader;
import org.tartarus.snowball.ext.*;

public class Indexing
{
	public static	Connection connection = null;
	public static  Statement stmt = null;
	public static Vector<String> list = new Vector();
//	public static int id =1;
	
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
	
	
	public static int check_vector(String root)
	{
		int flag=0;
		if(list.contains(root))
		    flag = 1;
		if(flag==1)
			return 1;
		else
			return 0;
		
	}
	
	public static void update(String root,int doc_id)
	{  
	   int freq = 0;
//	   connect();
	   
	    try
	      {	
	       
		   stmt = connection.createStatement(); 
		   
		   String sql = "SELECT frequency FROM indexing " + 
			            "WHERE term = " + "'" + root + "'" + " AND doc_id =" + doc_id ;
			            
		   ResultSet rs = stmt.executeQuery(sql);
		   while(rs.next())
			 freq =  rs.getInt("frequency");
		     freq++;
		  
		     String sql1 = "UPDATE indexing " + 
				        "SET frequency=" + freq +
				        " WHERE term = " + "'" + root + "'" + " AND " + "doc_id=" + doc_id;
		 stmt.executeUpdate(sql1);
	  }
	   catch (SQLException se)
	   {
		//Handle errors for JDBC
         se.printStackTrace();
	   }
      catch(Exception e)
      {
       //Handle errors for Class.forName
        e.printStackTrace();
      }
//	    try { connection.close(); } catch (Exception e) {}
   }
	
	
	 public static void add_word(String root,int doc_id)
	 {
		 list.addElement(root);
//		 connect();
		
		 try
		{
		 stmt = connection.createStatement(); 
		   
		   String sql = "INSERT INTO indexing " + 
			            "VALUES ("+ "'" + root +"'" + ",1," + doc_id + ")";
		   
		   stmt.executeUpdate(sql);
		
		}
		 catch (SQLException se)
		   {
			//Handle errors for JDBC
	         se.printStackTrace();
		   }
	      catch(Exception e)
	      {
	       //Handle errors for Class.forName
	        e.printStackTrace();
	      }
	//	 try { connection.close(); } catch (Exception e) {}
	 }
	
	public Indexing(String path_book,String book_title) 
	{
		String path = new String();
		int number_of_pages; 
		String root = new String();
		
		connect();

		number_of_pages= new File(path_book).listFiles().length;
		 reading rd = new reading();
		 int j ;
		 int doc_id=0;
		 englishStemmer stemmer = new englishStemmer();
		for( j=1;j<=number_of_pages;j++)
		{
			try{
			stmt = connection.createStatement();
		   String sql_page = "SELECT doc_id FROM documents"+
						  " WHERE page_no=" + j + " AND book_title= " + "'" + book_title + "'";
		
			ResultSet rs =  stmt.executeQuery(sql_page);
			while(rs.next())
				doc_id = rs.getInt("doc_id");
		}catch(SQLException e){e.printStackTrace();}
		
		  path = path_book + j +".txt";
		  rd.f_array.removeAllElements();
		  rd.ReadFile(path);
//		  Collections.sort(rd.f_array);
		  
		  for(int i=0;i<rd.f_array.size();i++)
		  {   
			  root = rd.f_array.elementAt(i);
//				System.out.println(stemmer.getCurrent());
		     if(check_vector(root)==1)
		    	 update(root,doc_id);
		     else
		    	 add_word(root,doc_id);
		  }
		  list.removeAllElements();
		}
		
	} 

}
