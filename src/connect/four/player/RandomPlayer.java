package connect.four.player;

import connect.four.board.ReadWritableBoard;

import java.util.Random;

/**
 * Simple AI implementation of Player, identified by its
 * {@link #performPlay(ReadWritableBoard)} algorithm, which selects a random
 * (valid) column, and selects that column to make a move.
 * 
 * @see connect.four.player.Player
 * 
 */
public class RandomPlayer implements Player
{
	/* (non-Javadoc)
	 * @see connect.four.player.Player#getName()
	 */
	@Override
	public String getName()
	{
		return "Computer";
	}
	
	/* (non-Javadoc)
	 * @see connect.four.player.Player#performPlay(connect.four.board.ReadWritableBoard)
	 */
	@Override
	public void performPlay(ReadWritableBoard board)
	{
		int width = board.getWidth();
		int height = board.getHeight();
		Random rand = new Random();
		int x = rand.nextInt(width);
		if (board.whoPlayed(x, height - 1) != null)
		{
			int chosenX = (x + 1) % width;
			while (board.whoPlayed(chosenX, height - 1) != null && chosenX != x)
			{
				chosenX = (chosenX + 1) % width;
			}
			x = chosenX;
		}
		
		board.play(x, this);
	}
}
