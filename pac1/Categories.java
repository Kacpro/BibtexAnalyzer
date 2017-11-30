package pac1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map; 

public class Categories  
{
	private static List<Tuple<String, Tuple<String[], String[]>>> categoryList;
	static
	{  
		categoryList = new LinkedList<>();
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("article", new Tuple<String[], String[]>(new String[] {"author", "title", "journal", "year"}, new String[] {"volume", "number", "pages", "month", "note", "key"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("book", new Tuple<String[], String[]>(new String[] {"author"}, new String[] {"volume", "series", "address", "edition", "month", "note", "key"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("book", new Tuple<String[], String[]>(new String[] {"editor", "title", "publisher", "year"}, new String[] {"volume", "series", "address", "edition", "month", "note", "key"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("inbook", new Tuple<String[], String[]>(new String[] {"author"}, new String[] {"volume"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("inbook", new Tuple<String[], String[]>(new String[] {"editor", "title", "chapter"}, new String[] {"volume"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("inbook", new Tuple<String[], String[]>(new String[] {"pages", "publisher", "year"}, new String[] {"volume"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("inbook", new Tuple<String[], String[]>(new String[] {"author"}, new String[] {"number", "series", "type", "address", "edition", "month", "note", "key"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("inbook", new Tuple<String[], String[]>(new String[] {"editor", "title", "chapter"}, new String[] {"number", "series", "type", "address", "edition", "month", "note", "key"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("inbook", new Tuple<String[], String[]>(new String[] {"pages", "publisher", "year"}, new String[] {"number", "series", "type", "address", "edition", "month", "note", "key"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("inproceedings", new Tuple<String[], String[]>(new String[] {"author", "title", "booktitle", "year"}, new String[] {"number", "series", "pages", "address", "month", "organization", "publisher", "note", "key"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("inproceedings", new Tuple<String[], String[]>(new String[] {"author", "title", "booktitle", "year"}, new String[] {"editor", "volume"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("conference", new Tuple<String[], String[]>(new String[] {"author", "title", "booktitle", "year"}, new String[] {"editor", "volume"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("conference", new Tuple<String[], String[]>(new String[] {"author", "title", "booktitle", "year"}, new String[] {"number", "series", "pages", "address", "month", "organization", "publisher", "note", "key"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("booklet", new Tuple<String[], String[]>(new String[] {"title"}, new String[] {"author", "howpublished", "address", "month", "year", "note", "key"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("incollection", new Tuple<String[], String[]>(new String[] {"author", "title", "booktitle", "publisher", "year"}, new String[] {"number", "series", "type", "chapter", "pages", "address", "edition", "month", "note", "key"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("incollection", new Tuple<String[], String[]>(new String[] {"author", "title", "booktitle", "publisher", "year"}, new String[] {"editor", "volume"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("manual", new Tuple<String[], String[]>(new String[] {"title"}, new String[] {"author", "organization", "address", "edition", "month", "year", "note", "key"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("mastersthesis", new Tuple<String[], String[]>(new String[] {"author", "title", "school", "year"}, new String[] {"type", "address", "month", "note", "key"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("phdthesis", new Tuple<String[], String[]>(new String[] {"author", "title", "school", "year"}, new String[] {"type", "address", "month", "note", "key"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("techreport", new Tuple<String[], String[]>(new String[] {"author", "title", "institution", "year"}, new String[] {"editor", "volume"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("techreport", new Tuple<String[], String[]>(new String[] {"author", "title", "institution", "year"}, new String[] {"number", "series", "address", "month", "organization", "publisher", "note", "key"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("misc", new Tuple<String[], String[]>(new String[] {}, new String[] {"author", "title", "howpublished", "month", "year", "note", "key"})));
		categoryList.add(new Tuple<String, Tuple<String[], String[]>>("unpublished", new Tuple<String[], String[]>(new String[] {"author", "title", "note"}, new String[] {"month", "year", "key"})));
		
		
		
	}
	
	
	
	public static Map<String, String> checkCategory(String category, Map<String, String> argumentMap)
	{ 
		category = category.toLowerCase(); 
		for (Tuple<String, Tuple<String[], String[]>> tuple : categoryList)
		{	
			if (tuple.left.equals(category))
			{

				Tuple<String[], String[]> categoryArguments = tuple.right;
				boolean buffer = true;
				for (String requiredArgument : categoryArguments.left)
				{
					if (!argumentMap.containsKey(requiredArgument)) 
					{
						buffer = false;
					}
				}
				if (buffer)
				{
					Map<String, String> result = new HashMap<String, String>();
					for (String givenArgument : argumentMap.keySet())
					{
						if (Arrays.asList(tuple.right.left).contains(givenArgument) || Arrays.asList(tuple.right.right).contains(givenArgument))
						{
							result.put(givenArgument, argumentMap.get(givenArgument));
						}
					}
					return result;
				}
			}
		}
		return null;
	}
}
