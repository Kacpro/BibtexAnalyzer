package test;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import pac1.Person;

public class PersonTest 
{

	@Test
	public void testParseName() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException 
	{
		Person person = new Person("");
		Class<?> _person = Class.forName("pac1.Person");
		Method m = _person.getDeclaredMethod("parseName", String.class);
		m.setAccessible(true); 
		
		m.invoke(person, "AA BB");
		assertEquals("Name = AA", "AA", person.getFirstName());
	
		m.invoke(person, "AA BB");
		assertEquals("Last Name = BB", "BB", person.getLastName());
		
		m.invoke(person, "BB");
		assertEquals("Only Last Name = BB", "BB", person.getLastName());
		
		m.invoke(person, "AA cc BB");
		assertEquals("Von = cc", "cc", person.getVon());
		
	}

}
