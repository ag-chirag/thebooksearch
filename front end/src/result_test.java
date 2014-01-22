import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;


public class result_test{
  
	Vector<JLabel> label_vector = new Vector();
	
   result_test()
	{
          
        giveResults gr = new giveResults();
  		for(int i=0;i<gr.results_pages.size();i++)
  		{
  			label_vector.addElement(new JLabel(gr.results_pages.elementAt(i)+ " " + gr.results_books.elementAt(i)));
  		}
  		
  		
	}
   
   
   
   
   public void paint(Graphics g)
   {
	   int x=10,y=10;
	   for(int i=0;i<label_vector.size();i++)
	   {
		   label_vector.elementAt(i).setLocation(x, y);
		   y=y+30;
	   }
   }

}
