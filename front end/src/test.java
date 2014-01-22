import javax.swing.*;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.SimpleBookmark;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;

import java.io.FileReader;
import java.io.BufferedReader;
import org.tartarus.snowball.ext.*;
import java.awt.*;
import java.awt.event.*;

public class test 
{

	JLabel jl;
	
	newBook nb = new newBook();
//	PdfReaderContentParser p = new PdfReaderContentParser();
//	PdfReader reader = new PdfReader();
	
	JButton submit1 = new JButton("Submit");
	JTextField file= new JTextField(20);
	JLabel jl1 = new JLabel("From page no   : ");
	JTextField jtf= new JTextField(3);
	JLabel jl2 = new JLabel("to");
	JRadioButton  no = new JRadioButton("No");
	JRadioButton  yes = new JRadioButton("Yes");
	JLabel jl3 = new JLabel ("Is your PDF book marked?");
	JTextField jtf1= new JTextField(3);
	
	public void initi()
	{ 
		JFrame jf = new JFrame("Upload Book");
		jf.setLayout(new FlowLayout());
		jf.setSize(300, 400);
		
		jl = new JLabel("File :");
		jf.add(jl);
		jf.add(file);
		
		jf.add(jl1);
		jf.add(jtf);
			// don't know where the pages will be saved.
		jf.add(jl2);
		jf.add(jtf1);
		jf.add(jl3);
		
		jf.add(yes);
		
		jf.add(no);

			
		ButtonGroup bg=new ButtonGroup();
		bg.add(yes);
		bg.add(no);
	
		jf.add(submit1);
		
		jf.setVisible(true);
	}
	
	public void paint(Graphics g)
	{
		jl.setLocation(350, 15);
		file.setLocation(400,15);
		jl1.setLocation(400, 50);
		jtf.setLocation(500,50);
		jl2.setLocation(550, 50);
		jtf1.setLocation(580, 50);
		jl3.setLocation(400, 100);
		yes.setLocation(550,95);
		no.setLocation(600,95);
		submit1.setLocation(490, 130);
	}
	

	
//	public static void main(String args[])
//	{
//		upload u = new upload();
//		u.init();
//		u.setVisible(true);
//		u.setSize(1000, 720);
//	}
	
	test()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				initi();
			}
		});
		
	}
}
