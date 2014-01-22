import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.tartarus.snowball.ext.englishStemmer;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.SimpleBookmark;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import org.tartarus.snowball.ext.*;


public class bookmarks {
	
	public static	Connection connection = null;
	public static  Statement stmt = null;
	public static String book_title = new String();
	 public static englishStemmer  stemmer = new englishStemmer();  
	 public static int start_page = 27;
  public bookmarks(String path_book, String book_title ){
	  
	 
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
  
	  
  PdfReader reader;
try {
	System.out.println(path_book);
	System.out.println(book_title);
	reader = new PdfReader(path_book);
	
	stmt = connection.createStatement(); 
	String drop_constraint = "ALTER TABLE titles "+
							 "Drop Foreign Key fk_titles_delete";
	stmt.executeUpdate(drop_constraint);
	
	
	 List list = SimpleBookmark.getBookmark(reader);
	    for (Iterator i = list.iterator(); i.hasNext();) {
	      showBookmark((Map) i.next());
	    }
	  
	String add_constraint = "ALTER TABLE titles "+
							"ADD CONSTRAINT fk_titles_delete "+
							"FOREIGN  KEY (doc_id) "+
							"REFERENCES documents(doc_id) "+
							"on delete cascade";
	stmt.executeUpdate(add_constraint);
	System.out.println("constraint added");
	    
} catch (IOException e) {
	e.printStackTrace();
}
catch(SQLException e){
	e.printStackTrace();
}
  }   
   

  private static void showBookmark(Map bookmark) {
    System.out.println(bookmark.get("Title") );
    String heading = (bookmark.get("Title")).toString().toLowerCase();
    String page = ((bookmark.get("Page")).toString());
    int length = heading.split(" ").length;
    int page_no =Integer.parseInt(page.substring(0,page.indexOf(" ")));
    int doc_id = 0;
    if(heading.equals("index"))
        return;
    
    try
    {
    	

    	String sql_doc_id = "Select doc_id FROM documents WHERE page_no= "+ (page_no-start_page) + " AND book_title=" +"'"+ book_title +"'";
    	ResultSet rs = stmt.executeQuery(sql_doc_id);
    	
    	while(rs.next())
    		doc_id = rs.getInt(1);
    }catch (SQLException se){se.printStackTrace();}
    
    try
    {	
     
	   stmt = connection.createStatement(); 
	   stemmer.setCurrent(heading);
		  stemmer.stem();
		  heading = stemmer.getCurrent();
	   String sql = "INSERT INTO titles " + 
		            "VALUES( " + "'" + heading + "'," + length +"," + doc_id + ")";

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
    
    ArrayList kids = (ArrayList) bookmark.get("Kids");
    if (kids == null)
      return;
    for (Iterator i = kids.iterator(); i.hasNext();) {
      showBookmark((Map) i.next());
    }
  }
}