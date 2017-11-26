package pac1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Categories 
{
	private static Map<String, Tuple<String[], String[]>> categoryList;
	static
	{
		categoryList = new HashMap<>();
		categoryList.put("article", new Tuple(new String[] {"author", "title", "journal", "year"}, new String[] {"volume", "number", "pages", "month", "note", "key"}));
	}
	
	
	
	public static Boolean checkCategory(String category, Set<String> arguments)
	{
		category = category.toLowerCase();
		Tuple<String[], String[]> categoryArguments = categoryList.get(category);
		
		for (String requiredArgument : categoryArguments.left)
		{
			if (!arguments.contains(requiredArgument)) 
			{
				return false;
			}
		}
		return true;
	}
	
	
	public static String[] getRequiredArguments(String category)
	{
		return categoryList.get(category.toLowerCase()).left;
	}
	
	public static String[] getOptionalArguments(String category)
	{
		return categoryList.get(category.toLowerCase()).right;
	}
	
	public static String print()
	{
		String result = "";
		for (String category : categoryList.keySet())
		{
			result += print(category);
		}
		return result;
	}
	
	public static String print(String category)
	{
		return category + "\nRequired arguments: " + Arrays.toString(categoryList.get(category).left) + "\nOptional arguments: "+Arrays.toString(categoryList.get(category).right)+"\n\n";
	}
	
}
