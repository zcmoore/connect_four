package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import connect.four.board.Board;
import connect.four.board.ColumnFullException;
import connect.four.player.RandomPlayer;

public class TestRandomPlayer
{
	@Test
	public void testName()
	{
		RandomPlayer randomPlayer = new RandomPlayer();
		assertEquals("Computer", randomPlayer.getName());
	}
	
	@Test
	public void testPerformPlay()
	{
		final int boardWidth = 5;
		final int boardHeight = 5;
		Board board = new Board(boardWidth, boardHeight);
		RandomPlayer randomPlayer = new RandomPlayer();
		assertEquals("Computer", randomPlayer.getName());
		
		try
		{
			String message = "Expect a move to be made";
			randomPlayer.performPlay(board);
			assertEquals(message, 1, board.getMoveCount());
			
			board.clear();
			for (int i = 0; i < boardHeight * boardWidth; i++)
			{
				randomPlayer.performPlay(board);
				assertEquals(message, i + 1, board.getMoveCount());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("exception in board");
		}
	}
	
	@Test
	public void testInvalidPlay()
	{
		final int boardWidth = 5;
		final int boardHeight = 5;
		Board board = new Board(boardWidth, boardHeight);
		RandomPlayer randomPlayer = new RandomPlayer();
		
		try
		{
			for (int i = 0; i < boardHeight * boardWidth; i++)
			{
				randomPlayer.performPlay(board);
			}
			
			// Make a play on a full board - catch exception below
			randomPlayer.performPlay(board);
			fail("Expect ColumnFullException to be thrown");
		}
		catch (ColumnFullException e)
		{
			assertEquals("Expect full board", boardHeight * boardWidth,
					board.getMoveCount());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("exception in board");
		}
		
	}
	
}
