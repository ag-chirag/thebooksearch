import java.awt.*;
import java.sql.*;
import java.text.DateFormat;
import java.lang.reflect.InvocationTargetException;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;


public class history
{
	JLabel jlb = new JLabel("History");
	histfunc h = new histfunc();
	Vector<JCheckBox> cb = new Vector();
	Vector<JCheckBox> cbsearch = new Vector();
	
	Vector<String> rsearch = new Vector();
	
	public static	Connection connection = null;
	public static  Statement stmt = null;
	String query = new String();
	Date d = null;
	Time t = null;
	String getsearch=null;
	Vector<String> r = new Vector();
	Vector<Date> date = new Vector();
	Vector<Time> time = new Vector();
	JButton search = new JButton("Search");
	JButton rem_sel_item = new JButton("Remove Selected Items");
	JButton clear_hist = new JButton("Clear Viewing History");
	JTextField jt = new JTextField(30);
	public void history()
	{
		try
		{
			
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					hist();
				}
			});
			
		}catch(Exception e)
		{
			System.out.println("can't create because of "+e);
		}

	}
	private void hist()
	{
		JFrame jf = new JFrame("History");
		jf.setLayout(new FlowLayout());
	
		jf.add(jlb);
		
		jf.add(rem_sel_item);
		rem_sel_item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				for(int i=0;i<cb.size();i++)
			    {
			       if(cb.elementAt(i).isSelected())
			       {
			    	   query = r.elementAt(i);
			    	   h.remove(query);
			    	   r.remove(i);
			    	   cb.remove(i);
			       }
			    }
			}
		});
		jf.add(clear_hist);
		clear_hist.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				for(int i=0;i<cb.size();i++)
			    {
					query = r.elementAt(i);
					h.remove(query);
					r.clear();
					cb.clear();
			    }
			}
		});
		
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
			
			String sql = "SELECT query,date,time FROM history";
			
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				
				d=rs.getDate(2);
				query = d.toString() + " ";
				date.addElement(d);
				t=rs.getTime(3);
				time.addElement(t);
				query += t.toString() + " ";
				query += rs.getString(1);
				
				r.addElement(query);
		
				cb.addElement(new JCheckBox(query));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		for(int j=0;j<cb.size();j++)
		{
			jf.add(cb.elementAt(j));
			cb.elementAt(j).addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					cb.clear();
				}
			});
		}
	}
		/*public void actionPerformed(ActionEvent ae)
		{

			r.clear();
			cb.clear();
			rsearch.clear();
			cbsearch.clear();
			
			
			*/	
				
	public void paint(Graphics g)
		{
			jlb.setLocation(10, 20);
			int x=10;
			int y=50;
			jt.setLocation(x, y);
			y=80;
			search.setLocation(x, y);
			x=30;
			y=20;
			clear_hist.setLocation(x, y);
			y=40;
			rem_sel_item.setLocation(x, y);
			x=50;
			y=20;
			for(int i=0;i<cb.size();i++)
			{
			  y=y+20;
			  cb.elementAt(i).setLocation(x, y);
			}
			
		}
}