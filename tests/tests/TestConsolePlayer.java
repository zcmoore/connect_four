package tests;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import connect.four.player.ConsolePlayer;

public class TestConsolePlayer
{
	ConsolePlayer player;
	
	@Before
	public void setUp() throws Exception
	{
		
	}
	
	@After
	public void tearDown() throws Exception
	{
		player = null;
	}
	
	private String randomString(int length)
	{
		StringBuilder string = new StringBuilder();
		Random random = new Random();
		
		while (length > 0)
		{
			length--;
			char character = (char) random.nextInt(256);
			string.append(character);
		}
		
		return string.toString();
	}
	
	@Test
	public void testConstructor()
	{
		player = new ConsolePlayer("");
		assertEquals("", player.getName());
		
		for (int playerIndex = 0; playerIndex < 300; playerIndex++)
		{
			for (int length = 1; length < 25; length++)
			{
				for (int trial = 0; trial < 100; trial++)
				{
					String name = randomString(length);
					player = new ConsolePlayer(name);
					assertEquals(name, player.getName());
				}
			}
		}
	}
	
	@Test
	public void testSetName()
	{
		player = new ConsolePlayer("");
		assertEquals("", player.getName());
		
		for (int playerIndex = 0; playerIndex < 300; playerIndex++)
		{
			for (int length = 1; length < 25; length++)
			{
				for (int trial = 0; trial < 100; trial++)
				{
					String name = randomString(length);
					player = new ConsolePlayer(name);
					assertEquals(name, player.getName());
				}
			}
		}
	}
	
}
