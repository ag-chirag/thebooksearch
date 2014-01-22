
//package reverseindexing;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import read_text.*;


public class revindex
{	
	revindex re = new revindex();
		public static void main(String[] argv) 
		{
			String path = new String();
			int number_of_pages; 
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
				System.out.println("Creating reverse index database");
				stmt = connection.createStatement();
				
				String sql = "CREATE TABLE revindex " +
						"(id INTEGER not NULL, " +
						"word VARCHAR(20), " +
						"page_no VARCHAR(20000), "+
						"nop INT(4)," +
						"book_name VARCHAR(30),"+
						"PRIMARY KEY (id))";
				stmt.executeUpdate(sql);
				System.out.println("Created database for Reverse Indexing");
			}
			catch(SQLException se)
			 {
				se.printStackTrace();
			  }
		    catch(Exception e)
		      {
		        e.printStackTrace();
		      }
			number_of_pages= new File("D:/College/College Stuff/book").listFiles().length;
			 reading rd = new reading();

			 int m;
			 int n = 0;
			  int i = 1;
			int y=1;
			String[] page_no = null;
			int[] nop = {1};
			
			  
			 for(int j=1;j<=number_of_pages;j++)
			{
			
			  path = "D:/College/College Stuff/book/"+j +".txt";
			  rd.ReadFile(path);
			  
			  String[] t = rd.f_array;
			  int l = t.length;
			  for(int f=0; f<l; f++)
				  t[f]=t[f].toLowerCase();
			  for(int w=0; w<l; w++)
			  {
			  if(i==1)
			  try
			  {
				  stmt = connection.createStatement();
					
				  page_no[j] = "j";
				  nop[j] = 1;
				  String sql = "INSERT INTO revindex(id,word,page_no,nop)" +
				  			   "VALUES("+i+","+t[n]+","+page_no[j]+","+nop[j]+")";
				  stmt.executeUpdate(sql);
				  i++;
				  n++;
			  }
			  catch(SQLException se)
				 {
					se.printStackTrace();
				  }
			    catch(Exception e)
			      {
			        e.printStackTrace();
			      }
			  else
			  {
				  m=n;
				  try
				  {
					  stmt=connection.createStatement();
					  for(int k=0; k<m; k++)
					  {
						  if(t[k]==t[n] && y==j)
						  {
							  n++;
							  if(n==l)
							  {
								  y++;
								  break;
							  }
						  }
						  else if(t[k]==t[n])
						  {
							  String sql1 = "SELECT nop FROM revindex" +
							  				"WHERE word="+t[k]+" ";
							  stmt.executeUpdate(sql1);
							  nop[k]++;
							  
							  
							  String sql2 = "UPDATE revindex" +
							  				"SET nop="+nop[k]+"" +
							  				"WHERE word="+t[k]+"";
							  stmt.executeUpdate(sql2);
							  
							  String sql3 = "SELECT page_no FROM revindex" +
					  					"WHERE word="+t[k]+" ";
							  stmt.executeUpdate(sql3);
							  page_no[k] = page_no[k] + " ,j";
							  
							  String sql4 = "UPDATE revindex" +
						  				"SET page_no="+page_no[k]+"" +
						  				"WHERE word="+t[k]+"";
							  stmt.executeUpdate(sql4);
							  n++;
							  
						  }
						  else
						  {
							  page_no[j] = "j";
							  nop[j] = 1;
							  
							  String sql5 = "INSERT INTO revindex(id,word,page_no,nop)" +
							  				"VALUES("+i+","+t[k]+","+page_no[j]+","+nop[j]+")";
							  stmt.executeUpdate(sql5);
							  i++;
							  n++;
						  }
					  }
					  
					  
				  }catch(SQLException se)
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