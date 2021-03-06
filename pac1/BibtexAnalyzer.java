package pac1;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class BibtexAnalyzer 
{
	private PublicationHolder publicationHolder;
	private List<String> categories;
	private List<Tuple<String, String>> arguments = new LinkedList<>();
	private List<Tuple<String, String>> firstNames = new LinkedList<>();
	private List<Tuple<String, String>> lastNames = new LinkedList<>();
	private String file;
	private char borderChar = '*';
	
	
	public BibtexAnalyzer(String[] commandLine)
	{
		CommandParser cmdParser = new CommandParser();
		try
		{
			Tuple<Tuple<Tuple<String, Character>, Tuple<List<String>, List<Tuple<String, String>>>>, Tuple<List<Tuple<String, String>>, List<Tuple<String, String>>>> cmd = cmdParser.parseCommand(commandLine);
			file = cmd.left.left.left;
			borderChar = cmd.left.left.right;
			categories = cmd.left.right.left;
			arguments = cmd.left.right.right;
			firstNames = cmd.right.left;
			lastNames = cmd.right.right;
		}
		catch(InputMismatchException e)
		{
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		
		
		Parser parser = new Parser(this.file);
		try 
		{
			publicationHolder = new PublicationHolder(parser.parse(), borderChar);
		} 
		catch (InputMismatchException e) 
		{
			System.out.println(e.getMessage());
			System.exit(-1);
		} 
		catch (IOException e) 
		{
			System.out.println("File opening error");
			System.exit(-1);
		}
		printCmdArguments();
	}
	
	private void printCmdArguments()
	{
//		System.out.println(publicationHolder);
		boolean flag = true;
		for (String categorie : categories) 
		{
			System.out.println(getByCategory(categorie));
			flag = false;
		}
		for (Tuple<String, String> tuple : arguments)
		{
			System.out.println(getByArgument(tuple.left, tuple.right));
			flag = false;
		}
		for (Tuple<String, String> tuple : lastNames) 
		{ 
			System.out.println(getByLastName(tuple.left, tuple.right));
			flag = false; 
		}
		for (Tuple<String, String> tuple : firstNames) 
		{
			System.out.println(getByFirstName(tuple.left, tuple.right));
			flag = false;
		}
		if (flag && publicationHolder!=null)
		{
			System.out.println(publicationHolder);
		}
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
		return publicationHolder.get(p -> p.getCategory().equalsIgnoreCase(category));
	}
	
	
	PublicationHolder getByArgument(String name, String value)
	{
		return publicationHolder.get(p ->  p.getArgumentValue(name).equalsIgnoreCase(value));
	}
	
	
	PublicationHolder getByLastName(String name, String value)
	{
		return publicationHolder.get(p -> { List<Person> buf = p.getNameDetails(name); for(Person per : buf) { if (per.getLastName().equalsIgnoreCase(value)) return true;}return false;});
	} 
	
	PublicationHolder getByFirstName(String name, String value)
	{
		return publicationHolder.get(p -> { List<Person> buf = p.getNameDetails(name); for(Person per : buf) {if (per.getFirstName().equalsIgnoreCase(value)) return true;}return false;});
	}
	
	public String toString()
	{
		return publicationHolder.toString();
	}
	
}
