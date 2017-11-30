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
	private char borderChar = '*';
	
	
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
	
	
	
	PublicationHolder getByLamda(Function<Publication, Boolean> method)
	{
		return publicationHolder.get(method);
	}
	
	
	PublicationHolder getByCategory(String category)
	{
		return publicationHolder.get(p -> p.getCategory().equals(category));
	}
	
	
	PublicationHolder getByArgument(String name, String value)
	{
		return publicationHolder.get(p -> p.getArgumentValue(name).equals(value));
	}
	
	
	PublicationHolder getByLastName(String name, String value)
	{
		return publicationHolder.get(p -> { List<Person> buf = p.getNameDetails(name); for(Person per : buf) { if (per.getLastName().equals(value)) return true;}return false;});
	}
	
	public String toString()
	{
		return publicationHolder.toString();
	}
	
}
