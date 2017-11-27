package pac1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
		
		Pattern publicationPattern = Pattern.compile("@(?![Pp][Rr][Ee][Aa][Mm][Bb][Ll][Ee]|[Ss][Tt][Rr][Ii][Nn][Gg]|[Cc][Oo][Mm][Mm][Ee][Nn][Tt])(\\w\\S*)\\s*[\\{\\(]([a-zA-Z]\\S*)\\s*,\\s*((\\s*\\w+\\s*=\\s*[\\S]+?,(?=\\n)\\s*)*(\\s*\\w+\\s*=\\s*[\\s\\S]+?)?)[,]?\\s*\\n+\\s*\\}");
		Pattern argumentPattern = Pattern.compile("\\s*(\\w\\S*)\\s*=\\s*([\\s\\S]+?)\\s*");
		Matcher m = publicationPattern.matcher(fileTextString);
		while(m.find()) 
		{
			String category = m.group(1);
			String key = m.group(2);
			String[] arguments = m.group(3).split(",(?=[^=\"\\{\\}]*=)");
			Map<String, String> argumentMap = new HashMap<>();
			for (String argument : arguments)
			{
				
				Matcher m2 = argumentPattern.matcher(argument);
				m2.matches();	
				argumentMap.put(m2.group(1), evaluateValue(m2.group(2)));	
			}
			if ((argumentMap =Categories.checkCategory(m.group(1), argumentMap)) != null)
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
		Pattern quotedValue = Pattern.compile("\"([\\s\\S]*?)\"");
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



