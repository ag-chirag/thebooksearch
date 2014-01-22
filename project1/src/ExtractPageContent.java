import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
 
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
 
public class ExtractPageContent {
     
    /**
     * Parses a PDF to a plain text file.
     * @param pdf the original PDF
     * @param txt the resulting text
     * @throws IOException
     */
  private void parsePdf(String pdf, String txt,int j) throws IOException {
        PdfReader reader1 = new PdfReader(pdf);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader1);
        PrintWriter out = new PrintWriter(new FileOutputStream(txt));
        TextExtractionStrategy strategy;
        
            if(parser==null)
            	System.out.println("null");
            strategy = parser.processContent(j, new SimpleTextExtractionStrategy());
            out.println(strategy.getResultantText());
        
        out.flush();
        out.close();
        reader1.close();
    }
 
    /**
     * Main method.
     * @param    args    no arguments needed
     * @return 
     * @throws IOException
     */
 //   public ExtractPageContent(){}
    public  static void main (String [] args) throws IOException{
    	
    	
    	String bookPath  = args[0];
    	int start = Integer.parseInt(args[1]);
		int last = Integer.parseInt(args[2]);
    	
    	String dir[] = bookPath.split("\\/+");
    	String directory = dir[dir.length-1].substring(0,dir[dir.length-1].length()-4);
    	new File("C:\\"+directory).mkdir();
   // 	System.out.println(directory);
    	String var = "C:/"+directory+"/";
  //      int j = 0;  
       /** The original PDF that will be parsed. */
       PdfReader reader;
//		bookPath="H:/mini_project/SE Project Summary.pdf";
//		System.out.println(bookPath);
 //    bookPath =  "H:/SEProjectSummary.pdf";
       System.out.println("start:" + start);
       System.out.println("last:" + last);
		 reader = new PdfReader(bookPath);
		 for(int j=1;j<last; j++){
		       /** The resulting text file. */
		       String pageno = String.valueOf(j);
		       final String RESULT = var +pageno + ".txt"; 
		       
		      new ExtractPageContent().parsePdf(bookPath, RESULT,j + start-1);
	//	      new ExtractPageContent(bookPath,start,last).parsePdf(bookPath, RESULT,j + start);
	//	       parsePdf(bookPath, RESULT,j + start);
		 }
	
          
    }
}