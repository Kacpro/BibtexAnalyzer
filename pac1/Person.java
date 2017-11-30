package pac1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person
{
	private String firstName;
	private String lastName; 
	private String von;
	private String jr; 
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName() 
	{
		return lastName; 
	}
	
	public String getVon()
	{
		return von;
	}
	
	public String getJr()
	{
		return jr;
	}
	
	
	
	public Person(String name)
	{
		parseName(name);
	}
	
	
	
	private void parseName(String name)
	{
	
		Pattern buf = Pattern.compile("[a-zA-Z0-9\\s.]*");
		Matcher m = buf.matcher(name);
		String buffer = "";
		while (m.find())
		{
			buffer += m.group();
		}
		name = buffer;
		
		Pattern nameStructure = Pattern.compile("\\s*(([A-Z][\\S]*\\s+)*)(?=\\s*\\w+)((([A-Za-z0-9]*\\s+)*([a-z0-9][A-Za-z0-9])?)\\s(?=\\s*\\w+))?\\s*(.+)\\s*");
		Matcher matcher = nameStructure.matcher(name);
		
		if (matcher.matches())
		{
			firstName = matcher.group(1).replaceAll("\\s*$","");
			lastName = matcher.group(7);
			von = matcher.group(4);
		}
	//	System.out.println(firstName+ " " + lastName + " " + von);
		
	}
	
}
