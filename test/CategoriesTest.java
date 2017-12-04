package test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import pac1.Categories;

public class CategoriesTest 
{
	@Test
	public void test() 
	{
		Map<String, String> map = new HashMap<>();
		map.put("aaa", "123");
		
		assertNull("No such category", Categories.checkCategory("book", map));
		assertNull("Null", Categories.checkCategory("book", null));
		
		Map<String, String> map2 = new HashMap<>();
		map2.put("author", "Mickiewicz");
		map2.put("title","Pan Tadeusz");
		map2.put("note",  "nudy");
		map2.put("month",  "jan");
		map2.put("year",  "1834");
		map2.put("key",  "AM");
		
		assertEquals("Perfect match", map2, Categories.checkCategory("unpublished", map2));
		
		Map<String, String> map3 = new HashMap<>();
		map2.put("aaa", "aaa");
		map3.put("author", "Mickiewicz");
		map3.put("title","Pan Tadeusz");
		map3.put("note",  "nudy");
		map3.put("month",  "jan");
		map3.put("year",  "1834");
		map3.put("key",  "AM");
		
		assertEquals("Ignored argument", map3, Categories.checkCategory("unpublished", map2));
		
		Map<String, String> map4 = new HashMap<>();
		map4.put("author", "Mickiewicz");
		map4.put("title","Pan Tadeusz");
		map4.put("note",  "nudy");
		assertEquals("No optional arguments", map4, Categories.checkCategory("unpublished", map4));
	}

}
