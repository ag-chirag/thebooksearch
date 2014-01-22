import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.Vector;

public class frontpage extends JFrame
{
	private static JPanel jp1 = new JPanel();
	private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();
	private JPanel jp4 = new JPanel();
	private JPanel jp5 = new JPanel();
	private JPanel jp6 = new JPanel();
	private JPanel jp7 = new JPanel();
	private JPanel jp8 = new JPanel();
	private JPanel jp9 = new JPanel();
	
	static JButton se = new JButton("Search");
	static JButton hi = new JButton("History");
	static JButton up = new JButton("Upload");
	static JButton dl = new JButton("Delete");
	static JButton cbn = new JButton("Change Book Name");
	
	static JFrame jf = new JFrame();                                                       
 	static JLabel l2 = new JLabel("The Book Search");
	
	static JTextField jtup = new JTextField(30);
	Font fup = new Font("TimesRoman", Font.BOLD, 20);
	static JLabel jlup = new JLabel("Enter name the of book to upload");
	static JButton btup = new JButton("ENTER");
	
	static JRadioButton ge = new JRadioButton("General");
	static JRadioButton as = new JRadioButton("Asterik");
	static JRadioButton boo = new JRadioButton("Boolean");
	static JRadioButton bos = new JRadioButton("Boosted");
	static JRadioButton wi = new JRadioButton("Wildcard");
	
	static ButtonGroup jbg = new ButtonGroup();
	
	static JTextField jt = new JTextField(30);
	
	public frontpage()
	{
		additems();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void additems()
	{
		jp1.add(up);
		jp1.add(dl);
		jp1.add(cbn);
		jp1.add(hi);
		
		Font f = new Font("TimesRoman", Font.BOLD,40);
		l2.setFont(f);
		jp1.add(l2);
		
		
		Font f1 = new Font("TimesRoman", Font.PLAIN,20);
		jt.setFont(f1);
		jp1.add(jt);
		jp1.add(se);
		
		jp1.add(ge);
		jp1.add(as);
		jp1.add(boo);
		jp1.add(bos);
		jp1.add(wi);
		
		
		
		jbg.add(ge);
		jbg.add(as);
		jbg.add(boo);
		jbg.add(bos);
		jbg.add(wi);
		
		
		
		up.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				upload u = new upload();
			}
		});
		dl.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				delete d = new delete();
			}
		});
		cbn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				bookChange bc = new bookChange();
			}
		});
		hi.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				history h = new history();
			}
		});
		
		se.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				
			
           if(jt.getText().isEmpty())
        	   JOptionPane.showMessageDialog(null,"Need To Give A Query");
				else	
				{	if(ge.isSelected())
					   new search(jt.getText());
					else if(as.isSelected())
						new Starred(jt.getText());
					else if(boo.isSelected())
						new seperator(jt.getText());
					else if(bos.isSelected())
						new Boosting(jt.getText());
					else
						new Wildcard(jt.getText());
				}
          //         new  result_test();
           			new  result();
           		
			}
		});
		
//		jf.setContentPane(contentPane);
//		jf.setLocationByPlatform(true);
		ge.setSelected(true);
		jf.setVisible(true);
		
	}
	
	
	
	
	public void paint(Graphics g)
	{
		up.setLocation(965,15);
		dl.setLocation(1045, 15);
		hi.setLocation(1120, 15);
		cbn.setLocation(1200, 15);
		
		l2.setLocation(570, 180);
		jt.setLocation(480, 250);
		se.setLocation(920, 250);
		
		ge.setLocation(500,300);
		as.setLocation(600, 300);
		boo.setLocation(700,300);
		bos.setLocation(800,300);
		wi.setLocation(900,300);
	
	}
	
	public static void main(String args[])
	{
	  frontpage fp = new frontpage();
	  fp.setSize(1370,720);
		fp.setVisible(true);
		fp.add(jp1);
	}
	
}
