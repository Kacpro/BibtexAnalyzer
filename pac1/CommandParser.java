package pac1;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser 
{
	
	String filePath = new String();				
	List<String> requestedCategories = new LinkedList<>();
	List<Tuple<String, String>> requestedArguments = new LinkedList<>();
	List<Tuple<String, String>> requestedFirstNames = new LinkedList<>();
	List<Tuple<String, String>> requestedLastNames = new LinkedList<>();
	char borderChar;
	
	public Tuple<Tuple<Tuple<String, Character>, Tuple<List<String>, List<Tuple<String, String>>>>, Tuple<List<Tuple<String, String>>, List<Tuple<String, String>>>> parseCommand(String[] argv) throws InputMismatchException
	{
		if (argv == null)
		{
			throw new InputMismatchException("No file given");
		}
		filePath = argv[0];	
		
		List<String> metaSigns = new LinkedList<>();
		metaSigns.add("~s");
		metaSigns.add("~a");
		metaSigns.add("~c");
		String command = "";
		for (int i=1;i<argv.length;i++)
		{ 
			command += " " + argv[i];
		}
//		System.out.println(command);
		String[] splitCommand = command.split(" (?=~s|~a|~c|~l|~f)");
//		System.out.println(Arrays.toString(splitCommand));
		
		Pattern categoryPattern = Pattern.compile("\\s*\'(\\w\\w+)\'\\s*");
		Pattern signPattern = Pattern.compile("\\s*(\\S)\\s*");
		Pattern argumentPattern = Pattern.compile("\\s*(\\w\\w+)\\s*=\\s*\'(\\S.*?)\'\\s*");
		

		Pattern optionPattern = Pattern.compile("(~\\w)\\s+(.+)");
		
		for (String commandPart : splitCommand) 
		{
			if (!commandPart.equals(null) && !commandPart.equals(""))
			{
				Matcher matcher = optionPattern.matcher(commandPart);
				Matcher mainMatcher;
		//		System.out.println(commandPart);
				matcher.find();
		//		System.out.println(matcher.group(1));
				
				switch (matcher.group(1))
				{
				case "~s": 
					{
						mainMatcher = signPattern.matcher(matcher.group(2));
						if (mainMatcher.find())
						{
							borderChar = mainMatcher.group(1).toCharArray()[0];
						} 
						break;
					}
				case "~a":
					{ 
						mainMatcher = argumentPattern.matcher(matcher.group(2));
						while (mainMatcher.find())
						{
							requestedArguments.add(new Tuple<String, String>(mainMatcher.group(1), mainMatcher.group(2)));
						}
						break;
					}
				case "~c":
					{
						mainMatcher = categoryPattern.matcher(matcher.group(2));
						while (mainMatcher.find())
						{
							requestedCategories.add(mainMatcher.group(1));
						}
						break;
					}
				case "~f":
					{
						mainMatcher = argumentPattern.matcher(matcher.group(2));
						while (mainMatcher.find())
						{
							requestedFirstNames.add(new Tuple<String, String>(mainMatcher.group(1), mainMatcher.group(2)));
						}
						break;
					}
				case "~l":
				{
						mainMatcher = argumentPattern.matcher(matcher.group(2));
			//			System.out.println(matcher.group(2));
						while (mainMatcher.find())
						{	
			//				System.out.println(mainMatcher.group(2));
							requestedLastNames.add(new Tuple<String, String>(mainMatcher.group(1), mainMatcher.group(2)));
						}
						break;
					}	
				}  
			}
		}
		return new Tuple<Tuple<Tuple<String, Character>, Tuple<List<String>, List<Tuple<String, String>>>>, Tuple<List<Tuple<String, String>>, List<Tuple<String, String>>>>(new Tuple<Tuple<String, Character>, Tuple<List<String>, List<Tuple<String, String>>>>(new Tuple<String, Character>(filePath, borderChar), new Tuple<List<String>, List<Tuple<String, String>>>(requestedCategories, requestedArguments)), new Tuple<List<Tuple<String, String>>, List<Tuple<String, String>>>(requestedFirstNames, requestedLastNames));
	}
	
}
