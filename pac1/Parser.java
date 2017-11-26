package pac1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser 
{
	
	private String file;
	private Map<String, String> constantsMap = new HashMap<>();
	private List<Publication> publicationList = new LinkedList<>();
	
	
	
	public Parser(String file)
	{
		this.file = file;
	}

	
	
	public List<Publication> parse() throws IOException, InputMismatchException
	{
		String fileTextString = fileReader(file);
		
		loadConstants(fileTextString);
		
		
		String publicationPattern = "@(?![Pp]reamle|[Ss]tring|[Cc]oment)(\\w\\S*)"
				+ "\\s*[\\{\\(]([a-zA-Z]\\S*)\\s*,\\s*"
				+ "((\\w\\S*\\s*=\\s*.*?\\s*,\\s*)*"
				+ "\\w\\S*\\s*=\\s*.*(?<!,)\\s*)[,]?\\s*"
				+ "[\\}\\)]"
				;
		String argumentPattern = "\\s*(\\w\\S*)\\s*=\\s*(.+)\\s*";
		
		Pattern p = Pattern.compile(publicationPattern);
		Matcher m = p.matcher(fileTextString);
		
		Pattern p2 = Pattern.compile(argumentPattern);
		while(m.find())
		{
			String category = m.group(1);
			String key = m.group(2);
			String[] arguments = m.group(3).split(",");

			Map<String, String> argumentMap = new HashMap<>();
			for (String argument : arguments)
			{
				Matcher m2 = p2.matcher(argument);
				m2.matches();	
				if (Arrays.asList(Categories.getRequiredArguments(category)).contains(m2.group(1)) || Arrays.asList(Categories.getOptionalArguments(category)).contains(m2.group(1)))
				{
					argumentMap.put(m2.group(1), evaluateValue(m2.group(2)));
				}		
			}
			if (Categories.checkCategory(m.group(1), argumentMap.keySet()))
			{
				 publicationList.add(new Publication(category, key, argumentMap));
			}
			else
			{
				throw new InputMismatchException();
			}
		}
		return publicationList;
	}
	
	
	
	private String evaluateValue(String value)
	{
		if (constantsMap.containsKey(value.toLowerCase()))
		{
			return constantsMap.get(value.toLowerCase());
		}
		
		String result;
		
		if ((result = evaluateConcatenatedValue(value)) != null)
		{
			return result;
		}
		
		if ((result = evaluateSingleValue(value)) != null)
		{
			return result;
		}
		
		return null;
	}
	
	
	
	private String evaluateSingleValue(String value)
	{
		Pattern regularValue = Pattern.compile("^(?!\"|\\{)(.*?)(?<!\"\\})$");  
		Pattern quotedValue = Pattern.compile("\"(.*?)\"");
		Pattern bracedValue = Pattern.compile("\\{([^\\{\\}]*?)\\}");
		 
		String currentValue = value;
		 
		Matcher matcher = bracedValue.matcher(value);
		while(matcher.find())
		{
			currentValue = matcher.replaceFirst(matcher.group(1));
			matcher = bracedValue.matcher(currentValue);
		}
		
		matcher = regularValue.matcher(currentValue);
		if (matcher.matches())
		{
			return matcher.group(1);
		}
		
		matcher = quotedValue.matcher(currentValue);
		if (matcher.matches())
		{
			return matcher.group(1);
		}
		return null;
	}
	
	
	
	private String evaluateConcatenatedValue(String value)
	{
		Pattern concatValue = Pattern.compile("[\"]?(.+?)[\"]?\\s*#\\s*[\"]?([^\"]+)[\"]?\\s*");
		Matcher matcher = concatValue.matcher(value);
		if (matcher.find())
		{
			return evaluateValue(matcher.group(1)) + evaluateValue(matcher.group(2));
		}
		return null;
	}
	
	
	
	private String fileReader(String file) throws IOException
	{
		String line = "";
		String result = "";
		FileReader reader = new FileReader(file);
		BufferedReader bufReader = new BufferedReader(reader);
		while ((line = bufReader.readLine()) != null)
		{
			result += line + "\n";
		}
		bufReader.close();
		return result;
	}
	
	
	
	private void loadConstants(String fileTextString)
	{
		Pattern stringConstant =  Pattern.compile("@[Ss][Tt][Rr][Ii][Nn][Gg]\\s*?[\\{\\(]\\s*?([a-zA-Z]\\w*?)\\s*=\\s*(.*)?\\s*?[\\}\\)]");
		Matcher matcher = stringConstant.matcher(fileTextString);
		while (matcher.find())
		{
			constantsMap.put(matcher.group(1).toLowerCase(), evaluateValue(matcher.group(2)));
		} 
	}	
}



