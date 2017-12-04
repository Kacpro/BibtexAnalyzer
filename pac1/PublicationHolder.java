package pac1;

import java.util.LinkedList; 
import java.util.List;
import java.util.function.Function;;

public class PublicationHolder
{ 
	private List<Publication> publicationList = new LinkedList<>();
	
	private char borderChar;
	 
	
	
	public PublicationHolder(List<Publication> publicationList, char borderChar)
	{
		this.publicationList = publicationList;
		this.borderChar = borderChar;
	}
	
	
	
	public List<Publication> getPublicationList()
	{
		return publicationList;
	}
	
	
	
	public PublicationHolder get(Function<Publication, Boolean> method)
	{
		List<Publication> result = new LinkedList<Publication>();  
		for (Publication publication : publicationList)
		{
			if (method.apply(publication)) 
			{
				result.add(publication);
			}
		}
		return new PublicationHolder(result, borderChar);
	}
	
	
	
	public String toString()
	{
		String result = "";
		for (Publication publication : publicationList)
		{
			result += publication.print(borderChar) + "\n\n\n";
		}
		return result;
	}
	
}
