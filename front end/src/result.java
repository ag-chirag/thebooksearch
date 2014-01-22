import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;


public class result extends JFrame{
  
	Vector<JLabel> label_vector = new Vector();
	
   result()
	{
          
	//      setLayout(new FlowLayout());
	     JPanel result_panel = new JPanel();
	     result_panel.setLayout(new BoxLayout(result_panel, BoxLayout.PAGE_AXIS));
	//       result_panel.setLayout(new FlowLayout());
	 //      getContentPane().add( result_panel );
	     setSize(1370,720);
		giveResults gr = new giveResults();
  		for(int i=0;i<gr.results_pages.size();i++)
  		{
  			label_vector.addElement(new JLabel(gr.results_pages.elementAt(i)+ " " + gr.results_books.elementAt(i)));
  		}
  		
  		
  		JScrollPane scrollPane = new JScrollPane();
  		for(int i=0;i<label_vector.size();i++)
  			{
  					result_panel.add(label_vector.elementAt(i));
  				//scrollPane.getViewport().add( label_vector.elementAt(i) );
  			}
  		
  		
  		
  		 JScrollPane pane = new JScrollPane(result_panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
  			add(pane);
  //		result_panel.add( scrollPane, BorderLayout.CENTER );
  //		add(result_panel);
  			paint2(pane.getGraphics());
  		setVisible(true);
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
   public void paint2(Graphics g){
	   
	   int x=10,y=10;
	   for(int i=0;i<label_vector.size();i++)
	   {
		   label_vector.elementAt(i).setLocation(x, y);
		   y=y+30;
	   }
   }
}
