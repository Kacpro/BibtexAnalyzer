package pac1;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Publication  
{
	private String category = null; 
	public List<Tuple<String, String>> arguments= new LinkedList<>();  //public do testów
	private String key = null;
	
	
	
	public String getCategory()
	{
		return category;
	}
	
	
	
	public String getKey()
	{
		return key; 
	}
	
	
	
	public boolean containsArgument(String argument)
	{
		for (Tuple<String, String> tuple : arguments)
		{
			if(tuple.left == argument.toLowerCase())
			{
				return true;
			}
		}
		return false;
	}
	
	
	 
	public String getArgumentValue(String argument)
	{
		for (Tuple<String, String> tuple : arguments)
		{
			if(tuple.left.equalsIgnoreCase(argument))
			{
				return tuple.right;
			} 
		}
		return "";
	}
	
	
	
	public Publication(String category, String key, Map<String, String> arguments)
	{
		this.category = category;
		this.key = key;

		for (String argumentName : arguments.keySet())
		{
			this.arguments.add(new Tuple<String, String>(argumentName, arguments.get(argumentName)));
		}
		getNameDetails("author");
	}
	
	
	public List<Person> getNameDetails(String category)
	{
		String[] names = {};
		for (Tuple<String, String> tuple : arguments)
		{
			if (tuple.left.equals(category))
			{
				names = tuple.right.split("\\s[aA][nN][dD]\\s");
				break;
			}
		}
	//	System.out.println(Arrays.toString(names));
		
		List<Person> nameDetails = new LinkedList<Person>();
		
		for (String name : names)
		{
			nameDetails.add(new Person(name));
		}
		 
		
		return nameDetails;
	}
	
	public String print(char borderChar)
	{
		String line = "";
		for(int i=0;i<100;i++) line += borderChar;
		String result = "";
		result += line + "\n" + borderChar + "  " + equalString(category + "(" + key +")", 96) + borderChar +"\n" + line + "\n";
		for (Tuple<String, String> tuple : arguments)
		{
			result += borderChar + "  " + 	equalString(tuple.left, 15) + borderChar + "  ";  
			if (tuple.left.equals("author") || tuple.left.equals("editor"))
			{
				boolean firstFlag = true;
				for (Person person : getNameDetails(tuple.left))
				{
					if (firstFlag)
					{
						result += equalString(person.getFirstName() + " " + person.getLastName(), 78) + borderChar + "\n";
						firstFlag = false;
					}
					else
					{
						result += borderChar + "  " + equalString(" ", 15) + borderChar + "  " + equalString(person.getFirstName() + " " + person.getLastName(), 78) + borderChar + "\n";
					}
				}
			}
			else
			{
				result += equalString(tuple.right, 78) + borderChar + "\n";
			}
			result += line + "\n";
		}
		return result;
	}
	
	private String equalString(String category, int length)
	{
		String result = category;
		for (int i=category.length();i<length;i++)
		{
			result += " ";
		}
		return result;
	}
	
}
