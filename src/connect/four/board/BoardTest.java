package connect.four.board;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import connect.four.player.Player;

public class BoardTest
{
	Board test1, test2, test3, test4;
	
	@Before
	public void setUp() throws Exception
	{
		test1 = new Board(5, 7);
		test2 = new Board(7, 5);
		test3 = new Board(3, 8);
		test4 = new Board(8, 3);		
	}
	
	@After
	public void tearDown() throws Exception
	{
		test1.dispose();
		test2.dispose();
		test3.dispose();
		test4.dispose();		
	}
	
	@Test
	public void testBoardIntInt()
	{		
		test2.moveCount = 2;
		test3.moveCount = 5;
		test4.moveCount = 8;
		assertEquals(5, test1.boardContents.length);
		assertEquals(0, test1.moveCount);
		assertEquals(7, test2.boardContents.length);
		assertEquals(2, test2.moveCount);
		assertEquals(3, test3.boardContents.length);
		assertEquals(5, test3.moveCount);
		assertEquals(8, test4.boardContents.length);
		assertEquals(8, test4.moveCount);		
	}
	
	@Test
	public void testBoardReadableBoard()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testWhoPlayed()
	{
		fail("Not yet implemented");		
		
	}
	
	@Test
	public void testGetWidth()
	{
		assertEquals(5, test1.getWidth());
		assertEquals(7, test2.getWidth());
		assertEquals(3, test3.getWidth());
		assertEquals(8, test4.getWidth());
	}
	
	@Test
	public void testGetHeight()
	{
		assertEquals(7, test1.getHeight());
		assertEquals(5, test2.getHeight());
		assertEquals(8, test3.getHeight());
		assertEquals(3, test4.getHeight());
	}
	
	@Test
	public void testPlay()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetColumnHeight()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testClear()
	{
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetMoveCount()
	{
		test2.moveCount = 125;
		test3.moveCount = 9;
		test4.moveCount = 22;
		assertEquals(0, test1.getMoveCount());
		assertEquals(125, test2.getMoveCount());
		assertEquals(9, test3.getMoveCount());
		assertEquals(22, test4.getMoveCount());
	}	
}
