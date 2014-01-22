import java.util.*;

public class seperator
{
	int count = 1;
	seperator(String query)
	{
		System.out.println(query);
		String[] root = query.split("\\s+");
		for(int i=0;i<root.length;i++)
		{
			if(root[i].charAt(0)=='+')
			{
				new plus(query);
				break;
			}
			
			else if(root[i].charAt(0)=='-')
			{
				new minus(query);
				break;
			}
			
			
			else if(root[i].charAt(0)=='"')
	    	{
				if(root[i].charAt(root[i].length()-1)=='"')
			{		//do nothing
				if(root[i+1].toLowerCase().equals("and"))
				{
//					System.out.println("AND from seperator");
					new bool_func_and(query);
					break;
				}
				else if(root[i+1].toLowerCase().equals("not"))
				{
					new bool_func_not(query);
					break;
				}
			  }
	    	}
	    }
				
	}
		
}

