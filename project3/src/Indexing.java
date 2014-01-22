import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
 
public class Indexing {
 
  public static void main(String[] argv) {
 
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		System.out.println("Where is your MySQL JDBC Driver?");
		e.printStackTrace();
		return;
	}
 
	System.out.println("MySQL JDBC Driver Registered!");
	Connection connection = null;
	Statement stmt = null;
 
	try {
		connection = DriverManager
		.getConnection("jdbc:mysql://localhost:3306/test","root", "");
 
	} catch (SQLException e) {
		System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return;
	}
 
	if (connection != null) {
		System.out.println("You made it, take control your database now!");
	} else {
		System.out.println("Failed to make connection!");
	}
	
	try
	{
	// Connection conn = null;
	// Statement stmt = null;
	 System.out.println("Creating table in given database...");
     stmt = connection.createStatement();
    
     String sql = "CREATE TABLE REG " +
                 "(id INTEGER not NULL, " +
                 " first VARCHAR(255), " + 
                 " last VARCHAR(255), " + 
                 " age INTEGER, " + 
                 " PRIMARY KEY ( id ))"; 

     stmt.executeUpdate(sql);
     System.out.println("Created table in given database...");
	} catch (SQLException se)
	   {
		//Handle errors for JDBC
          se.printStackTrace();
	   }
    catch(Exception e)
     {
      //Handle errors for Class.forName
      e.printStackTrace();
     }
	
	try{
    System.out.println("Inserting records into the table...");
    stmt = connection.createStatement();
    
    String sql = "INSERT INTO REG " +
                 "VALUES (100, 'Chirag', 'Ali', 18)";
    stmt.executeUpdate(sql);
    sql = "INSERT INTO REG " +
                 "VALUES (101, 'Mahnaz', 'Fatma', 25)";
    stmt.executeUpdate(sql);
    sql = "INSERT INTO REG " +
                 "VALUES (102, 'Zaid', 'Khan', 30)";
    stmt.executeUpdate(sql);
    sql = "INSERT INTO REG " +
                 "VALUES(103, 'Sumit', 'Mittal', 28)";
    stmt.executeUpdate(sql);
    System.out.println("Inserted records into the table...");

 }catch(SQLException se){
    //Handle errors for JDBC
    se.printStackTrace();
 }catch(Exception e){
    //Handle errors for Class.forName
    e.printStackTrace();
 }
	
 }
}