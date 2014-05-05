package connect.four.board;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import connect.four.player.ComputerPlayer;
import connect.four.player.ConsolePlayer;
import connect.four.player.Player;
import connect.four.board.Board;


public class BoardTest
{
	Board test1, test2, test3, test4, test5;
	ConsolePlayer p1, p2;
	ComputerPlayer comPlayer;
	@Before
	public void setUp() throws Exception
	{
		test1 = new Board(5, 7);
		test2 = new Board(7, 6);
		test3 = new Board(3, 8);
		test4 = new Board(8, 3);	
		test5 = new Board(6, 7);
		p1 = new ConsolePlayer("Player1");
		p2 = new ConsolePlayer("Player2");
		comPlayer = new ComputerPlayer();
	}
	
	@After
	public void tearDown() throws Exception
	{
		test1.dispose();
		test2.dispose();
		test3.dispose();
		test4.dispose();
		p1.dispose();
		p2.dispose();
		comPlayer.dispose();	
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
		ReadableBoard copy1 = new Board(test1);
		ReadableBoard copy2 = new Board(test2);
		assertEquals(test1.getHeight(), copy1.getHeight());
		assertEquals(test2.getHeight(), copy2.getHeight());		
	}
	
	@Test
	public void testWhoPlayed()
	{
		test2.boardContents[0][0] = p1;
		test3.boardContents[0][0] = p2;
		test4.boardContents[0][0] = comPlayer;
		assertEquals(null, test1.whoPlayed(0, 0));
		assertEquals(p1, test2.whoPlayed(0, 0));
		assertEquals(p2, test3.whoPlayed(0, 0));
		assertEquals(comPlayer, test4.whoPlayed(0, 0));		
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
		assertEquals(6, test2.getHeight());
		assertEquals(8, test3.getHeight());
		assertEquals(3, test4.getHeight());
	}
	
	@Test
	public void testPlay()
	{
		test1.play(1, comPlayer);
		test2.play(2, p2);
		for(int i = 0; i < 4; i++){
			test3.play(1, p1);
		}
		test3.play(0, p1);
		assertEquals(1, test1.moveCount);
		assertEquals(comPlayer, test1.boardContents[1][0]);
		assertEquals(1, test2.moveCount);
		assertEquals(p2, test2.boardContents[2][0]);
		assertEquals(5, test3.moveCount);
		assertEquals(p1, test3.boardContents[1][3]);
		assertEquals(0, test4.moveCount);
		assertEquals(null, test4.boardContents[7][0]);
	}
	
	@Test
	public void testGetColumnHeight()
	{		
		for (int i = 0; i < 6; i++)
		{
			test5.play(0, p2);
			test2.play(4, p2);
		}
		for (int j = 0; j < 4; j++){
			test3.play(2, p2);
		}
		assertEquals(6, test5.getColumnHeight(0));
		assertEquals(6, test2.getColumnHeight(4));
		assertEquals(0, test1.getColumnHeight(1));
		assertEquals(4, test3.getColumnHeight(2));
	}
	
	@Test
	public void testClear()
	{
		test1.moveCount = 9;
		test3.moveCount = 5;
		test1.clear();
		test3.clear();
		for(int i = 0; i < 6; i++){
			test5.play(1, p1);
			test2.play(2, p2);
		}
		test5.clear();
		test2.clear();
		assertEquals(0, test1.moveCount);
		assertEquals(null, test1.boardContents[0][0]);
		assertEquals(0, test2.moveCount);
		assertEquals(null, test2.boardContents[2][0]);
		assertEquals(0, test3.moveCount);
		assertEquals(null, test3.boardContents[0][0]);
		assertEquals(0, test5.moveCount);
		assertEquals(null, test5.boardContents[1][0]);
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
	@Test
	public void testIsColumnFull()
	{
		for(int i = 0; i < 6; i++){
			test5.play(4, p1);
			test2.play(0, p1);
		}
		assertEquals(false, test1.isColumnFull(1));
		assertEquals(true, test2.isColumnFull(0));
		assertEquals(false, test3.isColumnFull(2));
		assertEquals(true, test5.isColumnFull(4));
	}
	@Test
	public void testIsFull()
	{
		test2.moveCount = 42;
		test3.moveCount = 24;
		test4.moveCount = 23;
		assertEquals(false, test1.isFull());
		assertEquals(true, test2.isFull());
		assertEquals(true, test3.isFull());
		assertEquals(false, test4.isFull());		
	}
}
