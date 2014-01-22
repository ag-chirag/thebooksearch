package read_text;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import stopwordsremoval.*; 
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
            ;
           
			br = new BufferedReader(new FileReader(path));

			while ((sCurrentLine = br.readLine()) != null) {
			  String [] tokens = sCurrentLine.split("\\s+");
			    for(int n=0;n<tokens.length;n++)
			    {
			    	if(!(s.m_Words.contains(tokens[n].toLowerCase())))
			    	{
			    		f_array.addElement(spcl_char(tokens[n]).trim().toLowerCase());
			 //   	    split(f_array.elementAt(k));
			    	   
			    	}
			    }
			}
			
			
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
  public String spcl_char(String token)
  {
	
	String last =  token.substring(token.length() - 1);
    for(int i=0;i<special.length;i++)
    {
    	if(last.equals(special[i]))
    	token =	token.substring(0, token.length()-1);
    }
    
	return token;  
       
   }
    
}
