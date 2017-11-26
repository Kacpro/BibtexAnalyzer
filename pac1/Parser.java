package pac1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser 
{
	
	private String file;
	private Map<String, String> constantsMap = new HashMap<>();
	
	public Parser(String file)
	{
		this.file = file;
	}

	public void parse() throws IOException
	{
		String s = "";
		String line = "";
		
		FileReader reader = new FileReader(file);
		BufferedReader bufReader = new BufferedReader(reader);
		while ((line = bufReader.readLine()) != null)
			s+= line + "\n";
		
		bufReader.close();
		
		
		loadConstants(s);
		
		
		String publicationPattern = "@(?![Pp]reamle|[Ss]tring|[Cc]oment)(\\w\\S*)"
				+ "\\s*[\\{\\(]([a-zA-Z]\\S*)\\s*,\\s*"
				+ "((\\w\\S*\\s*=\\s*.*?\\s*,\\s*)*"
				+ "\\w\\S*\\s*=\\s*.*(?<!,)\\s*)[,]?\\s*"
				+ "[\\}\\)]"
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
				m2.matches();
				argumentMap.put(m2.group(1), m2.group(2));
				
				
				if (Arrays.asList(Categories.getRequiredArguments(category)).contains(m2.group(1)) || Arrays.asList(Categories.getOptionalArguments(category)).contains(m2.group(1)))
				{
					evaluateValue(m2.group(2));
				}
				
				
			}
			if (Categories.checkCategory(m.group(1), argumentMap.keySet()))
			{
				 
			}	
		}
		
		
	}
	
	private String evaluateValue(String value)
	{
		
		Pattern regularValue = Pattern.compile("([^\"\\{].*?[^\"\\}])");
		Pattern quotedValue = Pattern.compile("\"(.*?)\"");
		Pattern bracedValue = Pattern.compile("\\{([^\\{\\}]*?)\\}");
		 
		String currentValue = value;
		 
		
		
		Matcher matcher = bracedValue.matcher(value);
		while(matcher.find())
		{
//			System.out.println(currentValue);
			currentValue = matcher.replaceFirst(matcher.group(1));
			matcher = bracedValue.matcher(currentValue);
//			System.out.println(currentValue);
		}
		
		matcher = regularValue.matcher(currentValue);
		if (matcher.matches())
		{
			return matcher.group(1);
		}
		
//		System.out.println(currentValue);
		matcher = quotedValue.matcher(currentValue);
//		System.out.println(matcher.matches());
		if (matcher.matches())
		{
			return matcher.group(1);
		}
		
		return "";
	}
	
	private void loadConstants(String fileTextString)
	{
		Pattern stringConstant =  Pattern.compile("@[Ss][Tt][Rr][Ii][Nn][Gg]\\s*?[\\{\\(]\\s*?([a-zA-Z]\\w*?)\\s*?=\\s*?(.*)?\\s*?[\\}\\)]");
		Matcher matcher = stringConstant.matcher(fileTextString);
		while (matcher.find())
		{
			constantsMap.put(matcher.group(1).toLowerCase(), evaluateValue(matcher.group(2)));
		} 
		System.out.println(constantsMap); 
	}
	
	
	
}



