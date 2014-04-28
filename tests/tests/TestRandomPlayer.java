package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import connect.four.board.Board;
import connect.four.player.RandomPlayer;

public class TestRandomPlayer
{
	@Test
	public void test()
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
	
}
