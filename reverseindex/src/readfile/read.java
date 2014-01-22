package readfile;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import read_text.*;
import java.sql.*;

import org.tartarus.snowball.ext.englishStemmer;
import java.io.FileReader;
import java.io.BufferedReader;
import org.tartarus.snowball.ext.*;
public class read {

	static Vector<String> main_list = new Vector();
	String page_no = new String();
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
	
public	void reading(String path_book, String book_title)
	{
    	int number_of_pages;
    	int page_count;
    	reading rd = new reading();
    	reading rd2 = new reading();
    	String path = new String();
  //      int id=1;
 //       double idf;

    	number_of_pages= new File(path_book).listFiles().length;
    	connect();
    	 englishStemmer stemmer = new englishStemmer();
    	 connect();
    		String term = new String();
//    		String term_1 = new String();
	    for(int i=1;i<=number_of_pages;i++)
	    {
	      	
	    	path = path_book + i + ".txt";
	    	rd.f_array.clear();
	    	rd.ReadFile(path);
	    	
	    	for(int j=0;j<rd.f_array.size();j++)
	    	{
	    		
	    		page_count = 0 ;
	    		term = rd.f_array.elementAt(j);
	    		page_no = null;
	//    		 term_1= term;
	    	   
	    		if(!main_list.contains(term))   
	    		{
	    		
	    			main_list.addElement(term);
	   // 			id++;
	    			page_count++;
	    			
	//    			Integer l = i;
	//    			page_no = l.toString();
	    			
	    			for(int k=i+1;k<number_of_pages;k++)
	    			{
	    				rd2.f_array.clear();
	    				path = "H:/book/" + k + ".txt";
	    				rd2.ReadFile(path);
	    				
	    				if(rd2.f_array.contains(term))
	    				{		
	    					page_count++;
//	    					l = k; 
//	    				    page_no = page_no + "," + l.toString();	
	    				}
	    				
	    			}
//	   			System.out.println(main_list.lastElement() + "     " + page_count +"  "+ page_no);
	    			
	    			try
	   			  {
	    			  
	   				  stmt = connection.createStatement();
//	   				  idf = Math.log(number_of_pages/page_count);
//	   				  System.out.println(idf + " for "+term + " with page count " + page_count );
	   		
	   				  String sql = "INSERT INTO revindex " +
	   				  			   "VALUES(" + "'" + term + "'," + page_count + ",'" + book_title + "')";
	   				 
	   				  stmt.executeUpdate(sql);
	   				  
//	   				 try { connection.close(); } catch (Exception e) {}
	   			  }
	   			  catch(SQLException se)
	   				 {
	   					se.printStackTrace();
	   				  }
	   			    catch(Exception e)
	   			      {
	   			        e.printStackTrace();
	   			      }
	    		}
	    		
	    	}
	    }
	
	}
	
}
