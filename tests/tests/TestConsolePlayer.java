package tests;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import connect.four.player.ConsolePlayer;

/**
 * Test class for ConsolePlayer.
 * 
 * Testing is done based on randomly generated test cases, testing roughly
 * 750.000 test cases in each instance, and for multiple categories of input.
 * 
 * This test class only covers system interactions, and does not test user
 * interaction using System.in.
 * 
 * @author Moore, Zachary
 * 
 */
public class TestConsolePlayer
{
	ConsolePlayer player;
	
	/**
	 * Each test case is responsible for initializing the player object based on
	 * the individual testing requirements.
	 */
	@Before
	public void setUp() throws Exception
	{
		player = null;
	}
	
	/**
	 * Resets player (to null) after each test case.
	 */
	@After
	public void tearDown() throws Exception
	{
		player = null;
	}
	
	/**
	 * Helper function. Generates a random string of the given length, using all
	 * characters available in ASCII as a draw pool. Thus, null and new line
	 * characters are possible.
	 * 
	 * @param length
	 *            How long the string should be
	 * @return A random string of the specified length
	 */
	private String randomString(int length)
	{
		StringBuilder string = new StringBuilder();
		Random random = new Random();
		
		// Add *length* number of random characters to the string
		while (length > 0)
		{
			length--;
			// Generate a random character in the ASCII range (0-255)
			char character = (char) random.nextInt(256);
			string.append(character);
		}
		
		return string.toString();
	}
	
	/**
	 * Construct 750.000 + 1 ConsolePlayer objects using the
	 * ConsolePlayer(String) constructor and check its functionality.
	 * 
	 * The breakdown is: 100 trials per length of string; 25 different string
	 * lengths per player; 300 players
	 */
	@Test
	public void testConstructor()
	{
		player = new ConsolePlayer("");
		assertEquals("", player.getName());
		
		// Test 300 different players
		for (int playerIndex = 0; playerIndex < 300; playerIndex++)
		{
			// Test 25 different word lengths
			for (int length = 1; length < 26; length++)
			{
				// Test 100 different cases
				for (int trial = 0; trial < 100; trial++)
				{
					String name = randomString(length);
					player = new ConsolePlayer(name);
					assertEquals(name, player.getName());
				}
			}
		}
	}
	
	/**
	 * Run 750.000 random test cases on {@link ConsolePlayer#setName(String)}
	 */
	@Test
	public void testSetName()
	{
		// Test 300 different players
		for (int playerIndex = 0; playerIndex < 300; playerIndex++)
		{
			player = new ConsolePlayer("");
			
			// Test 25 different word lengths
			for (int length = 1; length < 26; length++)
			{
				// Test 100 different cases
				for (int trial = 0; trial < 100; trial++)
				{
					String name = randomString(length);
					player.setName(name);
					assertEquals(name, player.getName());
				}
			}
		}
	}
	
	// TODO: LOW_PRIORITY: Test console play
	// TODO: LOW_PRIORITY: Test console victory conditions
	
}
