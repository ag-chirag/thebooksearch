import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
 

 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfOutline;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.SimpleBookmark;
import com.itextpdf.text.pdf.hyphenation.TernaryTree.Iterator;
 
public class ConcatenateBookmarks {
    
    public static void manipulatePdf(String src)
        throws IOException, DocumentException {
    	
        Document document = new Document();
        
        document.open();
       
        PdfReader reader;
        int page_offset = 0;
        int n;
        
        ArrayList<HashMap<String, Object>> bookmarks = new ArrayList<HashMap<String, Object>>();
        List<HashMap<String, Object>> tmp;
         HashMap<String, Object> m;
      
         reader = new PdfReader(src);
          
           tmp = SimpleBookmark.getBookmark(reader);
          SimpleBookmark.shiftPageNumbers(tmp, page_offset, null);
         bookmarks.addAll(tmp);
          
           n = reader.getNumberOfPages();
          page_offset += n;
              
            Set<String> str = null;
        for(int i=0;i<bookmarks.size();i++)
            str =   bookmarks.get(i).keySet();
       
        String str1 = new String();
           
        for(int i=0;i<bookmarks.size();i++)
       {   str1 =	bookmarks.get(i).get("Title").toString();
           System.out.println(str1);
           ArrayList kids = (ArrayList) bookmarks.get(i).get("Kids");
           if(!(kids==null))
        	   for(int j=0;j<kids.size();j++)
        	   {
        		   str1 = kids.get(i).toString();
        		   System.out.println(str1);
        	   }
        }
        
        System.out.println("The First element of the set is: "+
                str.toString());
      
 
        reader.close();
        document.close();
        
    }
 
    /**
     * Main method.
     * @param    args    no arguments needed
     * @throws DocumentException 
     * @throws IOException
     * @throws SQLException
     */
    public static void main(String[] args)
        throws IOException, DocumentException, SQLException {
    	manipulatePdf("H:/mini project/Kurose_Networks_6th_Edition.pdf");
    }
}