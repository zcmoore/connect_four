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
	
}
