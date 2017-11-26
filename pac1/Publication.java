package pac1;

import java.util.LinkedList;
import java.util.List;

public class Publication 
{
	private String category = null;
	private List<Tuple<String, String>> arguments= new LinkedList<>(); 
	String key = null;
	
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
			if(tuple.left == argument.toLowerCase())
			{
				return tuple.right;
			}
		}
		return null;
	}
	
	public String toString()
	{
		return "";
	}
	
	public Publication(String category, String key, List<Tuple<String, String>> arguments)
	{
		this.category = category;
		this.key = key;
		this.arguments = arguments;
	}
	
	
}
