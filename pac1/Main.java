package pac1;

import java.io.IOException;
import java.util.List;
 
public class Main {

	public static void main(String[] args) throws IOException 
	{
		Parser parser = new Parser("C:\\Users\\Kacper97\\Desktop\\test.txt");
		List<Publication> test = parser.parse();
		for (Publication elem : test)
		{
			System.out.println(elem.getCategory()); 
			System.out.println(elem.getKey());
			for (Tuple<String, String> arg : elem.arguments)
			{
				System.out.println(arg);
			}
			System.out.println("\n\n");
		}
		
		
		PublicationHolder holder = new PublicationHolder(null, '*');
		holder.getPublicationList1(p -> p.getCategory()=="article");

	}
}
