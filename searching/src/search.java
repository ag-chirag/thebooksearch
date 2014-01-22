
import stopwordsremoval.*;
import java.util.*;
import java.sql.*;
import java.io.FileReader;
import java.io.BufferedReader;
import org.tartarus.snowball.ext.*;
public class search {
	
  public Vector<String> tokens = new Vector();
  public static Connection connection = null;
  public static Statement stmt = null; 

   search(String query)
	{
	   System.out.println(query);
//		String query = new String();
		Stopwords s = new Stopwords();
		double score =0.0;
		double idf;
		double page_count=0;
		double numberOfPages=0;
		int page_no =0;
		Vector<Integer> page = new Vector(); 
		Vector<Integer>currentPage = new Vector();
//		Vector<String>book_titles  = new Vector();
		Vector<Double>inv_freq = new Vector();
		Vector<Double>pageScore = new Vector();
//		Vector<String>headingPage = new Vector();
//		Vector<Double>result = new Vector();
		Vector<Integer>head_doc_id = new Vector();
		Vector<Integer>head_length = new Vector();
		int frequency;
		englishStemmer stemmer = new englishStemmer();
//		 String book_title = new String();
//		    book_title = "Computer Networks";
		 
		 
//		Scanner input = new Scanner(System.in);
//		query = input.nextLine();
		tokens.clear();
		HistorytoDB hs = new HistorytoDB(query);
		String[] root = query.split("\\s+");
		for(int i=0;i<root.length;i++)
		{
			if(!s.m_Words.contains(root[i].toLowerCase()))
			{
				  stemmer.setCurrent(root[i]);
				  stemmer.stem();
				  root[i] = stemmer.getCurrent();
				tokens.addElement(root[i]);
			}
		}

						
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
			
			    for(int i=0;i<tokens.size();i++)
			    	System.out.println(tokens.elementAt(i));
			
			    stmt = connection.createStatement();
			  
			   String sql_numberOfPages = "SELECT sum(no_of_pages) as sum FROM books";
			   ResultSet rs_numberOfPages = stmt.executeQuery(sql_numberOfPages);
			   
			   while(rs_numberOfPages.next())
				 numberOfPages = rs_numberOfPages.getDouble(1);
			   
			for(int j=0;j<tokens.size();j++)
			{	
			
			 
		//	   for(int i=0;i<book_titles.size();i++)
			   {
				   
			   String sql_pageCount = "SELECT SUM(page_count) as sum FROM "+
					                  "revindex WHERE "+
					                  "term= " + "'" + tokens.elementAt(j)+"'"; 
			   ResultSet rs_pageCount = stmt.executeQuery(sql_pageCount);
			   while(rs_pageCount.next())
				    page_count = rs_pageCount.getDouble(1);
	//		   System.out.println(numberOfPages/page_count);
			   idf = Math.log(numberOfPages/page_count);
			   inv_freq.addElement(idf);
			   }
			  
			}
			
//			for(int i=0;i<inv_freq.size();i++)
//				System.out.println(inv_freq.elementAt(i));
		
			int books;
//			String book_title = new String();
//				String sql1 = "Select MAX(id) FROM books";
//				ResultSet rs4 =stmt.executeQuery(sql1);
//				 while(rs4.next())	
//				books = rs4.getInt("id");	
//		for(int l=1;l<=books;l++)
					
	//	{
//			String sql2 = "SELECT book_title FROM " + 
//						  "books WHERE "+
//						  "id =" + l;
//		    ResultSet rs5 = stmt.executeQuery(sql2);
//			book_title = rs5.getString("book_title");
		
			for(int j=0;j<tokens.size();j++)
			{	
				page.clear();
		    	String sql_pages = "SELECT doc_id FROM" +
			             " indexing  WHERE " + 
					     "term =" + "'" + tokens.elementAt(j) + "'";
		 
			   ResultSet rs = stmt.executeQuery(sql_pages);
	//		   idf = rs.getInt("idf");
			   while(rs.next())
			   {	   page_no = rs.getInt("doc_id");
			           page.addElement(page_no);
			   }
			   
			   
			   for(int i=0;i<page.size();i++)
			   {  
				   
				  if(!(currentPage.contains(page.elementAt(i)))) 
				  {	  
				   score = 0;
				   for(int k=j;k<tokens.size();k++)
				   {
					   frequency = 0; 
					   
					 
					   String sql_two = "SELECT frequency FROM" +
				             		" indexing WHERE " + 
				             		"term =" + "'" + tokens.elementAt(k) + "'" + " AND " + "doc_id=" + page.elementAt(i);
					   ResultSet rs_two = stmt.executeQuery(sql_two);
					   while(rs_two.next())
					   frequency = rs_two.getInt("frequency");
					   if(!rs_two.equals(null))
					   {  // System.out.print("For page number "+ page.elementAt(i) + " and for term "+ tokens.elementAt(k)+ " " +"Score= " + score + "+" + frequency + "*" + inv_freq.elementAt(k));
						   score = score + frequency*inv_freq.elementAt(k);
						  // System.out.println("=" + score);
					   }
				   	}
//				   pageScore.addElement(score);
                   currentPage.addElement(page.elementAt(i));
//                   book_titles.addElement(book_title);
                   String sql3 = "INSERT INTO results " + 
   			            "VALUES (" + score + ", " + page.elementAt(i) +")";
                   stmt.executeUpdate(sql3);
				  } 
			   }
			}
	//	}
	}
		catch(SQLException se)
		 {
			se.printStackTrace();
		  }
	    catch(Exception e)
	      {
	        e.printStackTrace();
	      }
				
//				for(int i=0;i<pageScore.size()-1;i++)
//				for(int j=i+1;j<pageScore.size();j++)
//				{
//						if(pageScore.elementAt(i)<pageScore.elementAt(j))
//					{
//							Collections.swap(pageScore, i, j);
//							Collections.swap(currentPage, i, j);
//							Collections.swap(book_titles, i, j);
//						}
//				}
		
		 int headingPage;
		 int length;
		
		 for(int i=0;i<tokens.size();i++)
		 { 
			 String sql6 = "SELECT length,doc_id FROM titles WHERE heading like '%" + tokens.elementAt(i) +"%'";
		   
		 try {
			ResultSet rs_four = stmt.executeQuery(sql6);
			while(rs_four.next())
			{
				head_doc_id.addElement(rs_four.getInt("doc_id"));
				head_length.addElement(rs_four.getInt("length"));
			}
			   for(int j=0;j<head_doc_id.size();j++)
			    {
				    headingPage= head_doc_id.elementAt(j);
				    length=head_length.elementAt(j);
				    if(currentPage.contains(headingPage))
				    {
				    	String sql7 = "SELECT score FROM results WHERE doc_id = "+ headingPage;
				    	ResultSet rs_five = stmt.executeQuery(sql7);
				    	while(rs_five.next())	
				    		score = rs_five.getDouble("score") + 1/(double)length;
				    
				    	String sql8 = "UPDATE results SET score = " + score + " WHERE doc_id=" + headingPage;
				    	stmt.executeUpdate(sql8);    	
				    }
				    else{
				    	score = (1/(double)(length));
				    	String sql9 = "INSERT INTO results "+
				                      "VALUES ("+ score + ",'" + headingPage + "')";
				    	
				    	stmt.executeUpdate(sql9); 
				    	currentPage.addElement(headingPage);
				    }
		    	}
		    } catch (SQLException e) {
			  e.printStackTrace();
		     }
		 }     
		
		 
		
				System.out.println("results!!!!!!!!");
//		for(int i=0;i<currentPage.size();i++)		
//			System.out.println(currentPage.elementAt(i)+ "   "+pageScore.elementAt(i) );
	    String sql4 = "Select d.page_no, d.book_title, r.score FROM results r,documents d " +
                      "WHERE d.doc_id = r.doc_id " +
                       "ORDER BY score DESC";
	    try {
	    	ResultSet rs_three =stmt.executeQuery(sql4);
	    	while(rs_three.next())
	    	{	System.out.println(rs_three.getInt(1));
	    	//System.out.println(" " + rs_three.getDouble("score"));  
	    	
	    	}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	    
	
	}
}