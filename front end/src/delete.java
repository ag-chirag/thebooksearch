import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.*;
/*
<applet code="bookApplet" width=300 height = 400>
</applet>
*/
public class delete 
{   

JLabel jl;
DeleteBookName dbn = new DeleteBookName();
public  Connection connection = null;
public  Statement stmt = null; 
public Vector<String> book_names = new Vector();
Vector<JRadioButton> ch = new Vector();
public ButtonGroup bg = new ButtonGroup();
JButton button = new JButton("Delete");
JLabel label1 = new JLabel("Select a book:");
int count=0;
String book_name = new String();

//static JPanel jp4;

public delete()
{
//this.jp4 = pnl;
	try 
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				makeFrame();
			}
		});
	}catch (Exception e)
	{	
		e.printStackTrace();
	} 
}

	public void makeFrame()
	{
		JFrame jfz = new JFrame("Delete Book");
		jfz.setLayout(new FlowLayout());
		jfz.setSize(300, 400);
		jfz.add(label1);


//setLayout(new FlowLayout());

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
			String sql_query = "SELECT book_title FROM books";
			ResultSet rs = stmt.executeQuery(sql_query);

			while(rs.next())
			{
				book_name = rs.getString(1);
				System.out.println(book_name);
				book_names.addElement(book_name);
				ch.addElement(new JRadioButton(book_name));
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		for(int j=0;j<ch.size();j++)
		{
			jfz.add(ch.elementAt(j));
			bg.add(ch.elementAt(j));
			ch.elementAt(j).addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
						count++;
				}
			});
		}
		jfz.add(button);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{

				try
				{	

					{
						if(count==0)
						{
							JOptionPane.showMessageDialog(null,"A book has to be selected");
						}
						else
	{
							// System.out.println("hi");
 	  for(int i=0;i<ch.size();i++)
 		  if(ch.elementAt(i).isSelected())
 		  {
 			  	book_name = book_names.elementAt(i);
 			  	dbn.deleteBook(book_name);
 			  	break;
 		  }

 	  JOptionPane.showMessageDialog(null,"Book deleted successfully");
	}
	}
				}
					catch(NullPointerException e)
					{
						//showStatus("Need to give a New Book Name");
						e.printStackTrace();
	}


			}
		});
		jfz.setVisible(true);
	}



	public void paint(Graphics g)
	{
		label1.setLocation(10, 20);
		int x=10;
		int y=20;
		for(int i=0;i<ch.size();i++)
		{
			y=y+20;
			ch.elementAt(i).setLocation(x, y);
		}
		y=y+40;
			button.setLocation(x, y);
	}
	}


