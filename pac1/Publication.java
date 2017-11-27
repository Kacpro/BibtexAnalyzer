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
			if(tuple.left.equals(argument.toLowerCase()))
			{
				return tuple.right;
			}
		}
		return null;
	}
	
	
	
	public Publication(String category, String key, Map<String, String> arguments)
	{
		this.category = category;
		this.key = key;

		for (String argumentName : arguments.keySet())
		{
			this.arguments.add(new Tuple<String, String>(argumentName, arguments.get(argumentName)));
		}
	}
	
	
}
