package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import connect.four.board.Board;
import connect.four.board.ColumnFullException;
import connect.four.player.ComputerPlayer;

public class TestComputerPlayer
{
	@Test
	public void testName()
	{
		ComputerPlayer computerPlayer = new ComputerPlayer();
		assertEquals("Computer", computerPlayer.getName());
	}
	
	@Test
	public void testPerformPlay()
	{
		final int boardWidth = 5;
		final int boardHeight = 5;
		Board board = new Board(boardWidth, boardHeight);
		ComputerPlayer computerPlayer = new ComputerPlayer(6);
		ComputerPlayer computerOpponent = new ComputerPlayer();
		assertEquals("Computer", computerPlayer.getName());
		
		try
		{
			String message = "Expect a move to be made";
			computerPlayer.performPlay(board);
			assertEquals(message, 1, board.getMoveCount());
			
			board.clear();
			for (int i = 0; i < boardHeight * boardWidth; i++)
			{
				if ((i & 1) == 1)
					computerPlayer.performPlay(board);
				else
					computerOpponent.performPlay(board);
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
		ComputerPlayer computerPlayer = new ComputerPlayer();
		ComputerPlayer computerOpponent = new ComputerPlayer();
		
		try
		{
			for (int i = 0; i < boardHeight * boardWidth; i++)
			{
				if ((i & 1) == 1)
					computerPlayer.performPlay(board);
				else
					computerOpponent.performPlay(board);
			}
			
			// Make a play on a full board - catch exception below
			computerPlayer.performPlay(board);
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
