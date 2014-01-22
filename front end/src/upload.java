import javax.swing.*;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.SimpleBookmark;

import java.io.FileReader;
import java.io.BufferedReader;
import org.tartarus.snowball.ext.*;
import java.awt.*;
import java.awt.event.*;

public class upload
{
	JLabel jl;
	
	JButton ok = new JButton("OK");
	
	newBook nb = new newBook();
	
	JButton submit1 = new JButton("Submit");
	JTextField file= new JTextField(20);
	JLabel jl1 = new JLabel("From page no   : ");
	JTextField jtf= new JTextField(3);
	JLabel jl2 = new JLabel("to");
	JRadioButton  no = new JRadioButton("No");
	JRadioButton  yes = new JRadioButton("Yes");
	JLabel jl3 = new JLabel ("Is your PDF book marked?");
	JTextField jtf1= new JTextField(3); 
	
	
	public void initu()
	{
		JFrame jf = new JFrame("Upload Book Name");
		
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
		
		submit1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				if(jl.getText()==null)
				{
					JOptionPane.showMessageDialog(null,"Range Of page Number has To be Given");
				}
				else
				{
				//	newBook nb = new newBook();
				//	nb.addNewBook(file.getText().toString(), jtf.getText().toString(), jtf1.getText().toString());
					
					if(!yes.isSelected() && !no.isSelected())
					{
						JOptionPane.showMessageDialog(null,"BookMakrks field has to filled");
					}
					else if(yes.isSelected())
					{
						String[] root = file.getText().split("\\/+");
						int l = root.length;
						String sm = new String(root[l-1]);
						bookmarks b = new bookmarks(file.getText().toString(),sm);
					}
				}
				
			}
		});
		
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
	
	upload()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				initu();
			}
		});
		
	}
}
