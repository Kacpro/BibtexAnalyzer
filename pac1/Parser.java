package pac1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser 
{

	public void parse(String file) throws IOException
	{
		String s = "";
		String line = "";
		
		FileReader reader = new FileReader(file);
		BufferedReader bufReader = new BufferedReader(reader);
		while ((line = bufReader.readLine()) != null)
			s+= line + "\n";
		
		
		
		String publicationPattern = "@(?![Pp]reamle|[Ss]tring|[Cc]oment)(\\w\\S*)"
				+ "[\\s]*\\{([a-zA-Z]\\S*)\\s*,\\s*"
				+ "((\\w\\S*\\s*=\\s*.*?\\s*,\\s*)*"
				+ "\\w\\S*\\s*=\\s*.*(?<!,)\\s*)[,]?\\s*"
				+ "\\}"
				;
		String argumentPattern = "\\s*(\\w\\S*)\\s*=\\s*(.+)\\s*";
		
		Pattern p = Pattern.compile(publicationPattern);
		Matcher m = p.matcher(s);
		
		Pattern p2 = Pattern.compile(argumentPattern);
		while(m.find())
		{
//			System.out.println(m.group(3));
			String category = m.group(1);
			String key = m.group(2);
			String[] arguments = m.group(3).split(",");

			Map<String, String> argumentMap = new HashMap<>();
			for (String argument : arguments)
			{
				Matcher m2 = p2.matcher(argument);
				m2.matches();																		// ewaluacja wartoœci
				argumentMap.put(m2.group(1), m2.group(2));
			}
			if (Categories.checkCategory(m.group(1), argumentMap.keySet()))
			{
				 
			}
			
		}
		
		
		
		
		
		
		
		
	}
	
}
