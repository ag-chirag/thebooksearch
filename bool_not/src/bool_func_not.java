import stopwordsremoval.*;
import read_text.*;

import java.io.FileReader;
import java.io.BufferedReader;
import org.tartarus.snowball.ext.*;

import java.util.*;
import java.sql.*;
import java.io.FileReader;
import java.io.BufferedReader;

public class bool_func_not
{
static reading r= new reading();
public static Vector<String> tokens = new Vector();
public static Vector<String> tokens_nd = new Vector();
public static Vector<String> tokens_nn = new Vector();
public static Connection connection = null;
public static Statement stmt = null; 

bool_func_not(String query)
{
//String query = new String();
Stopwords s = new Stopwords();
double score =0.0;
double idf;
String temp1 = null;
String temp2=null;
double page_count=0;
double numberOfPages=0;
int page_no =0;
int count = 1;
reading r = new reading();
Vector<Integer> page = new Vector();
Vector<Integer> page1 = new Vector();
Vector<Integer> page2 = new Vector();
Vector<Integer>currentPage = new Vector();
Vector<Double>inv_freq = new Vector();
Vector<Double>inv_freq_tokens = new Vector();
int frequency;
int flag=0;
Vector<Integer>head_doc_id = new Vector();
Vector<Integer>head_length = new Vector();
englishStemmer stemmer = new englishStemmer();
//String book_title = new String();
//book_title = "Computer Networks";
String[]  root1 = null;
String[] root2 = null;
//Scanner input = new Scanner(System.in);
 //       query = input.nextLine();
        HistorytoDB hs = new HistorytoDB(query);
    for(int i=0;i<query.length();i++)
    {
    if(query.charAt(i)=='"' && count==2)
    {
    temp1 = query.substring(0,i+1).trim();
    temp2 = query.substring(i+1).trim();
    break;
    }
    else if(query.charAt(i)=='"')
    count++;
    }
    //System.out.println(temp1+ "-" +temp2);
    root1 = temp1.split("\\s+");
    for(int i=0;i<root1.length;i++)
    {
    root1[i] = r.spcl_char_mul(root1[i]);
    stemmer.setCurrent(root1[i]);
    stemmer.stem();
   root1[i] = stemmer.getCurrent();
   //System.out.println(root1[i]);
   if(!s.m_Words.contains(root1[i].toLowerCase()))
{    
  tokens_nd.addElement(root1[i]);
//	   System.out.println(tokens.elementAt(0));
    }
    }
    System.out.println();
    root2 = temp2.split("\\s+");
    for(int i=0;i<root2.length;i++)
    {
    root2[i] = r.spcl_char_mul(root2[i]);
    stemmer.setCurrent(root2[i]);
    stemmer.stem();
    root2[i] = stemmer.getCurrent();
    if(!s.m_Words.contains(root2[i].toLowerCase()))
{    
  tokens_nn.addElement(root2[i]);
//	   System.out.println(tokens.elementAt(0));
    }
    //System.out.println(root2[i]);
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
  stmt = connection.createStatement();
   
   String sql_numberOfPages = "SELECT sum(no_of_pages) as sum FROM books";
   ResultSet rs_numberOfPages = stmt.executeQuery(sql_numberOfPages);
   
   while(rs_numberOfPages.next())
numberOfPages = rs_numberOfPages.getDouble(1);

String sql_query_nd="";
             
             for(int j=0;j<tokens_nd.size()-1;j++)
             {
             if(j==0)
             sql_query_nd =sql_query_nd + "SELECT distinct doc_id FROM indexing WHERE term = "+"'" + tokens_nd.elementAt(j) + "'" +
                "AND doc_id IN ";
             else
             sql_query_nd =sql_query_nd + "(SELECT  doc_id FROM indexing WHERE term = "+"'" + tokens_nd.elementAt(j) + "'" +
                "AND doc_id IN ";
             }
             
             int z=tokens_nd.size();
             sql_query_nd = sql_query_nd + "(SELECT  doc_id FROM indexing WHERE term = "+"'" + tokens_nd.elementAt(z-1) + "'";
              
             
             for(int j=0;j<tokens_nd.size()-1;j++)
               sql_query_nd = sql_query_nd + ")";
             
             //System.out.println(sql_query_nd);
             //ResultSet rs_query_nd = stmt.executeQuery(sql_query_nd);
             //while(rs_query_nd.next())
             //{
  //   page_no = rs_query_nd.getInt(1);
   //  page1.addElement(page_no);
             //}

String sql_query_nn="";
             
        for(int j=0;j<tokens_nn.size()-1;j++)
        {
      if(j==0)
         sql_query_nn =sql_query_nn + "SELECT distinct doc_id FROM indexing WHERE term = "+"'" + tokens_nn.elementAt(j) + "'" +
             "AND doc_id IN ";
        else
           sql_query_nn =sql_query_nn + "(SELECT  doc_id FROM indexing WHERE term = "+"'" + tokens_nn.elementAt(j) + "'" +
             "AND doc_id IN ";
    }
             
    int w=tokens_nn.size();
    sql_query_nn = sql_query_nn + "(SELECT  doc_id FROM indexing WHERE term = "+"'" + tokens_nn.elementAt(w-1) + "'";         
             
    for(int j=0;j<tokens_nn.size()-1;j++)
        sql_query_nn = sql_query_nn + ")";
    
    String sql_query = sql_query_nd + " AND doc_id NOT IN (" +
    sql_query_nn + ")";
             
    System.out.println(sql_query);
    ResultSet rs_query = stmt.executeQuery(sql_query);
while(rs_query.next())
    {
		page_no = rs_query.getInt(1);
		page.addElement(page_no);	    
}	
//for(int i=0;i<page1.size();i++)
//{
//	 flag=0;
//	 for(int j=0;j<page2.size();j++)
//	 {
//	 if(page1.elementAt(i)==page2.elementAt(j))
//	 {
//	 flag=1;
//	 break;
//	 }
//	 }
//	 if(flag==0)
//	 page.addElement(i);
//}


for(int j=0;j<tokens_nd.size();j++)
{	
String sql_pageCount = "SELECT SUM(page_count) as sum FROM "+
                   "revindex WHERE "+
                   "term= " + "'" + tokens_nd.elementAt(j)+"'"; 
   ResultSet rs_pageCount = stmt.executeQuery(sql_pageCount);
   while(rs_pageCount.next())
    page_count = rs_pageCount.getDouble(1);
   System.out.println(numberOfPages/page_count);
   idf = Math.log(numberOfPages/page_count);
   inv_freq.addElement(idf);	   
}

	
 for(int i=0;i<page.size();i++)
   {  
	 score = 0;
	 for(int k=0;k<tokens_nd.size();k++)
	 {
	   frequency = 0; 
   
	   String sql_two = "SELECT frequency FROM" +
			   " indexing WHERE " + 
			   "term =" + "'" + tokens_nd.elementAt(k) + "'" + " AND " + "doc_id=" + page.elementAt(i);
	   ResultSet rs_two = stmt.executeQuery(sql_two);
	   while(rs_two.next())
		   frequency = rs_two.getInt(1);
	   if(!rs_two.equals(null))
	   {  
		   score = score + frequency*inv_freq.elementAt(k);
  
	   }
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
    
    String sql8 = "UPDATE results SET score =" + score + "WHERE doc_id=" + headingPage;
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
//	 for(int i=0;i<currentPage.size();i++)	
//	 System.out.println(currentPage.elementAt(i)+ "   "+pageScore.elementAt(i) );
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