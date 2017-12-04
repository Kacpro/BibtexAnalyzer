package test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map; 

import org.junit.Test;

import pac1.Parser;

public class ParserTest 
{

	@Test
	public void testEvaluateSingleValue() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException, SecurityException 
	{
		Parser parser = new Parser(null);
		Class<?> _parser = Class.forName("pac1.Parser");
		Method m = _parser.getDeclaredMethod("evaluateSingleValue", String.class);
		m.setAccessible(true);
		
		assertEquals("Quoted value", "Kacper Janda", m.invoke(parser, "\"Kacper Janda\""));
		assertEquals("Braced value", "Kacper Janda", m.invoke(parser, "{Kacper Janda}"));
		assertEquals("Regular value", "1234", m.invoke(parser, "1234"));
		assertEquals("Empty string", "", m.invoke(parser, ""));
	}
	
	@Test
	public void evaluateConcatenatedValue() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException, SecurityException, NoSuchFieldException 
	{
		Parser parser = new Parser(null);
		Class<?> _parser = Class.forName("pac1.Parser");
		Method m = _parser.getDeclaredMethod("evaluateConcatenatedValue", String.class);
		Field map = _parser.getDeclaredField("constantsMap");
		m.setAccessible(true);
		map.setAccessible(true);
		
		Map<String, String> buf = new HashMap<String, String>();
		buf.put("key", "Value");
		map.set(parser, buf);
		
		assertEquals("Two strings", "KacperJanda", m.invoke(parser, "\"Kacper\" # \"Janda\""));
		assertEquals("Constant", "TestValue", m.invoke(parser, "\"Test\" # key"));
		assertEquals("Constant - case insensitive", "TestValue", m.invoke(parser, "\"Test\" # KEY"));
		assertEquals("Two constants", "ValueValue", m.invoke(parser, "key # key"));
		assertEquals("Three values", "Test1Test2Value", m.invoke(parser, "\"Test1\" # \"Test2\" # key"));
	}
	
	
	
	
	

}
