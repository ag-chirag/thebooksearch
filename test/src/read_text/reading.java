package read_text;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import Stopwordsremoval.*; 
public class reading 
{
  public	Vector<String> f_array = new Vector();
  Stopwords s = new Stopwords();
  String[] special = {"`","~","!","@","#","$","%","^","&","*","(",")","-",".","?",";",":","'",",","[","]","{","}","|"};
  
 
  public void ReadFile(String path)
  {
	  BufferedReader br = null;
		  
		try {

			String sCurrentLine;
           
			br = new BufferedReader(new FileReader(path));
            
			while ((sCurrentLine = br.readLine()) != null) {
			  String [] tokens = sCurrentLine.split("\\s+");
			    for(int n=0;n<tokens.length;n++)
			    {
			    	if(!(s.m_Words.contains(tokens[n].toLowerCase())))
			    	{
			    		tokens[n]= seperator(tokens[n]);
			    		
			    		f_array.addElement(spcl_char_mul(tokens[n]).trim().toLowerCase());
			 //   	    split(f_array.elementAt(k));
			    	   
			    	}
			    }
			}
			
			for(int i=0;i<f_array.size();i++)
				System.out.println(f_array.elementAt(i));
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally
		{
			try 
			{
				if (br != null)br.close();
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
  }
 
  public String seperator(String token)
  {
	  String [] root = token.split("/");
	  if(root.length>1)
	  {
		  for(int i =0 ;i<root.length;i++)
		  f_array.addElement(root[i].toLowerCase());
		  return "the";
	  }
	  else
	  {
	  String [] root1 = token.split("-");
	  if(root1.length>1)
	  {
	  spcl_char_mul(root1[0]);
	  f_array.addElement(root1[0].toLowerCase());
	  return root1[1];
	  }
	  else
		  return token;
	  }
  }
  

  public String spcl_char_mul(String token)
  {
	  String temp = new String();
	 // temp = null;
	  char[] ch = token.toCharArray();
	  for(int i=0;i<token.length();i++)
	    if(Character.isLetter(ch[i]) || Character.isDigit(ch[i]))
			  temp = temp + ch[i];
		  
	  return temp;
  }
    
}
