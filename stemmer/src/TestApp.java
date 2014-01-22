import java.io.FileReader;
import java.io.BufferedReader;
import org.tartarus.snowball.ext.*;

public class TestApp {
    
    public static void main(String [] args) throws Throwable {
	
	    englishStemmer stemmer = new englishStemmer();
        String path = new String();
    path =  "F:/book/1.txt";
    BufferedReader reader;
//	reader = new BufferedReader (new FileReader(path));


	String sCurrentLine = new String();
	//while ((sCurrentLine = reader.readLine()) != null) 
	{
		
		String [] tokens = sCurrentLine.split("\\s+");
	//	for(int i =0 ;i<tokens.length;i++)
		{   
			stemmer.setCurrent("www.google.com");
			stemmer.stem();
			System.out.println(stemmer.getCurrent());
		}
	}
//	reader.close();
   }
    
}