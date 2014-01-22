import java.awt.*;
import java.sql.*;
import java.text.DateFormat;
import java.lang.reflect.InvocationTargetException;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;


public class history extends JFrame
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
//	JButton search = new JButton("Search");
	JButton rem_sel_item = new JButton("Remove Selected Items");
	JButton clear_hist = new JButton("Clear Viewing History");
//	JTextField jt = new JTextField(30);
    history()
	{
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					hist();
				}
			});
	}
	private void hist()
	{
		//JFrame jf = new JFrame("History");
	//	setLayout(new FlowLayout());
		setSize(300, 400);
		  JPanel history_panel = new JPanel();
		     history_panel.setLayout(new BoxLayout(history_panel, BoxLayout.PAGE_AXIS));
		     history_panel.add(jlb);
		
		     history_panel.add(rem_sel_item);
		rem_sel_item.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				for(int i=0;i<cb.size();i++)
			    {
//					System.out.println("HI from history");
			       if(cb.elementAt(i).isSelected())
			       {
			    	   query = r.elementAt(i);
//			    	   System.out.println(query);
			    	   h.remove(query);
			    	   r.remove(i);
			    	   cb.remove(i);
			       }
			    }
			}
		});
		history_panel.add(clear_hist);
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
		
//		System.out.println(cb.size());
		for(int j=0;j<cb.size();j++)
		{
			history_panel.add(cb.elementAt(j));
			cb.elementAt(j).addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					//cb.clear();
				}
			});
		}
	
		
		JScrollPane pane = new JScrollPane(history_panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
           add(pane);
//        jf.setContentPane(pane);
       	setVisible(true);
	}
	
				
	public void paint(Graphics g)
		{
			jlb.setLocation(10, 20);
			int x=10;
			int y=50;
//			jt.setLocation(x, y);
//			y=80;
//			search.setLocation(x, y);
//			x=30;
//			y=20;
			clear_hist.setLocation(x, y);
			rem_sel_item.setLocation(x,y+30);
			x=50;
			y=100;
			for(int i=0;i<cb.size();i++)
			{
			  y=y+20;
			  cb.elementAt(i).setLocation(x, y);
			}
			
		}
}