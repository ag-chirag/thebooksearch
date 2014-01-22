

import stopwordsremoval.*;
import read_text.*;
import java.io.FileReader;
import java.io.BufferedReader;
import org.tartarus.snowball.ext.*;
import java.util.*;
import java.sql.*;
import java.io.FileReader;
import java.io.BufferedReader;

public class bool_func_and
{
		
	static reading r= new reading();
	public  static Vector<String> tokens = new Vector();
	public static Connection connection = null;
	public static Statement stmt = null; 
	
	bool_func_and(String query)
	{
//		String query = new String();
		Stopwords s = new Stopwords();
		double score =0.0;
		double idf;
		double page_count=0;
		double numberOfPages=0;
		int page_no =0;
		Vector<String> token_needed=new Vector();
		Vector<Integer> page = new Vector();
		Vector<Integer>currentPage = new Vector();
		Vector<Double>inv_freq = new Vector();
		Vector<Double>pageScore = new Vector();
		Vector<Integer>head_doc_id = new Vector();
		Vector<Integer>head_length = new Vector();
		Vector<Double>inv_freq_tokens = new Vector();
		int frequency;
		englishStemmer stemmer = new englishStemmer();
        String[] roots;
	        HistorytoDB hs = new HistorytoDB(query);
		String[] root = query.split("and");
//		System.out.println(root[0] + " " + root[1]);
		for(int i=0;i<root.length;i++)
		{
//			System.out.println("root: " + root[i]);
			
			  roots = (root[i].trim()).split("\\s+");
//			  System.out.println(roots[0] ); 
//			  System.out.println(roots[1] ) ;
			
				for(int j=0;j<roots.length;j++)
				{
					stemmer.setCurrent(roots[j]);
		  			stemmer.stem();
					roots[j]=stemmer.getCurrent();
					roots[j]=r.spcl_char_mul(roots[j]);
//					 System.out.println(roots[j] ) ;
					if(!s.m_Words.contains(roots[j].toLowerCase()))
					{    
					  tokens.addElement(roots[j]);
//					  System.out.println(tokens.elementAt(0));
				    }
		   }
		}
		
		for(int i=0;i<tokens.size();i++)
			System.out.println(tokens.elementAt(i));
		
		
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
			   
			   String sql_numberOfPages = "SELECT sum(no_of_pages) as sum FROM books";
			   ResultSet rs_numberOfPages = stmt.executeQuery(sql_numberOfPages);
			   
			   while(rs_numberOfPages.next())
				 numberOfPages = rs_numberOfPages.getDouble(1);
			   
			for(int i=0;i<tokens.size();i++)		 
			  {
					String sql_pageCount = "SELECT SUM(page_count) as sum FROM "+
							               "revindex WHERE "+
						                   "term= " + "'" + tokens.elementAt(i) + "'"; 
			   	  	ResultSet rs_pageCount = stmt.executeQuery(sql_pageCount);
			   	  	while(rs_pageCount.next())
				  		page_count = rs_pageCount.getDouble(1);
			   	  	idf = Math.log(numberOfPages/page_count);
			   	  	inv_freq_tokens.addElement(idf);  
			   	}
			
	
               String sql_query="";
               
               for(int j=0;j<tokens.size()-1;j++)
               {
            	   if(j==0)
            		   sql_query =sql_query + "SELECT distinct doc_id FROM indexing WHERE term = "+"'" + tokens.elementAt(j) + "'" +
            			   	   "AND doc_id IN ";
            	   else
            	   sql_query =sql_query + "(SELECT  doc_id FROM indexing WHERE term = "+"'" + tokens.elementAt(j) + "'" +
            			   	   "AND doc_id IN ";
               }
               
               int z=tokens.size();
               sql_query = sql_query + "(SELECT  doc_id FROM indexing WHERE term = "+"'" + tokens.elementAt(z-1) + "'";
                
               
               for(int j=0;j<tokens.size()-1;j++)
                 sql_query = sql_query + ")";
               
                  System.out.println(sql_query);
				   ResultSet rs_query = stmt.executeQuery(sql_query);
				   while(rs_query.next())
					   {
					     page_no = rs_query.getInt(1);
					     page.addElement(page_no);
					   }
				   
				   for(int i=0;i<page.size();i++)
				   {
					   score=0;
					   frequency=0;
					   for(int j=0;j<tokens.size();j++)
					   {
					    String sql_score = "SELECT frequency FROM indexing WHERE term = " + "'" + tokens.elementAt(j) + "'"+
					                      " AND doc_id = " + page.elementAt(i);
					    ResultSet rs_score = stmt.executeQuery(sql_score);
					    while(rs_score.next())
					    	frequency = rs_score.getInt(1);
					    score = score + frequency * inv_freq_tokens.elementAt(j);
					   }
					   
						String sql3 = "INSERT INTO results " + 
		            	 		      "VALUES (" + score + ", " + page.elementAt(i) +")";
         			 	stmt.executeUpdate(sql3);
					}
				   		
	
		}
		catch(SQLException se)
	 	{
			se.printStackTrace();
	 	}
	 	catch(Exception e)
	 	{
	        	e.printStackTrace();
	 	}
				
   

		

	    	String sql4 = "Select d.page_no, d.book_title, r.score FROM results r,documents d " +
                      	      "WHERE d.doc_id = r.doc_id" +
                       	      " ORDER BY score DESC";
	    	try
		{				   
	    		ResultSet rs_three =stmt.executeQuery(sql4);
	    		while(rs_three.next())
	    		{	
				System.out.println(rs_three.getInt(1));
	    			//System.out.println(" " + rs_three.getDouble("score"));  
	    		}
		}
		catch(SQLException e)
		 {
			e.printStackTrace();
		 }
	    

	}
}
