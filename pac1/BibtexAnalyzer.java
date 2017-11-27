package pac1;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.function.Function;

public class BibtexAnalyzer 
{
	private PublicationHolder publicationHolder;
	private String[] cateogries;
	private String[] authors;
	private char borderChar;
	
	
	public BibtexAnalyzer(String file) throws InputMismatchException, IOException
	{
		Parser parser = new Parser(file);
		publicationHolder = new PublicationHolder(parser.parse(), borderChar);
	}
	
	
	
	public Publication getPublication(String key)
	{
		for (Publication publication : publicationHolder.getPublicationList())
		{
			if (publication.getKey().equals(key))
			{
				return publication;
			}
		}
		return null;
	}
	
	
	
	List<Publication> getByLamda(Function<Publication, Boolean> method)
	{
		return publicationHolder.getPublicationList(method);
	}
	
	
	List<Publication> getByCategory(String category)
	{
		return publicationHolder.getPublicationList(p -> p.getCategory() == category);
	}
	
	
	List<Publication> getByArgument(String name, String value)
	{
		return publicationHolder.getPublicationList(p -> p.getArgumentValue(name) == value);
	}
	
}
