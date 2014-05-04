package connect.four.player;

import connect.four.board.Board;
import connect.four.board.ReadableBoard;
import connect.four.board.ReadWritableBoard;
import connect.four.Game;
import java.util.Arrays;
import java.util.Random;

/**
 * AI implementation of Player, identified by its
 * {@link #performPlay(ReadWritableBoard)} algorithm, which selects a column in
 * which to make its move by considering the possibilities for future moves down
 * to a certain depth.
 * 
 * @see connect.four.player.Player
 * 
 */
public class ComputerPlayer implements Player
{
	/**
	 * How many moves this AI will think ahead, when determining its next move.
	 */
	int depth;
	
	/**
	 * Constructs a CompiterPlayer object with the specified depth
	 * 
	 * @param depth
	 */
	public ComputerPlayer()
	{
		this.depth = 6;
	}
	
	/**
	 * Constructs a CompiterPlayer object with the default depth (6)
	 * 
	 * @see #ComputerPlayer(int)
	 */
	public ComputerPlayer(int depth)
	{
		this.depth = depth;
	}
	
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
		int l = board.getWidth();
		int m = board.getHeight();
		Random random = (new Random());
		if (board.getMoveCount() == 0)
		{
			board.play(random.nextInt(l), this);
		}
		else
		{
			Player opponent = getOpponent(board);
			int maxMove = random.nextInt(l);
			long maxScore = scoreMove(maxMove, depth, board, opponent);
			long[] scores = new long[l];
			for (int i = 0; i != l; ++i)
			{
				if (board.whoPlayed(i, m - 1) != null)
					continue;
				long iScore = scoreMove(i, depth, board, opponent);
				if (iScore > maxScore)
				{
					maxMove = i;
					maxScore = iScore;
				}
				scores[i] = iScore;
			}
			System.out.println(Arrays.toString(scores));
			board.play(maxMove, this);
		}
	}
	
	/**
	 * Recursively determines the best (locally - see depth) score, by
	 * determining the possibility of the player or the opponent winning in x
	 * number of turns, up to *depth*.
	 * 
	 * Moves that result in a quick victory for the player, and/or an elongated
	 * victory for the opponent are favoured.
	 * 
	 * @param x
	 *            Move to consider
	 * @param depth
	 *            How many moves in the future to consider
	 * @param board
	 *            Which board to consider playing on
	 * @param opponent
	 *            Opponent player
	 * @return Score indicative of how good the specified move is.
	 */
	private long scoreMove(int x, int depth, ReadableBoard board,
			Player opponent)
	{
		int m = board.getHeight();
		if (board.whoPlayed(x, m - 1) != null)
			return 0;
		Board myMove = new Board(board);
		myMove.play(x, this);
		int l = myMove.getWidth();
		long score = 0;
		if (Game.detectWinner(myMove, 4) == this)
		{
			score += Math.pow(l, depth);
		}
		else if (depth != 0)
		{
			for (int i = 0; i != l; ++i)
			{
				if (myMove.whoPlayed(i, m - 1) != null)
					continue;
				Board nextMove = new Board(myMove);
				nextMove.play(i, opponent);
				if (Game.detectWinner(nextMove, 4) == opponent)
				{
					score -= Math.pow(l, depth - 1);
				}
				else
				{
					for (int j = 0; j != l; ++j)
					{
						score += scoreMove(j, depth - 2, nextMove, opponent);
					}
				}
			}
		}
		return score;
	}
	
	/**
	 * Determines the opponent player based on the specified board. This method
	 * will search the specified board for a Player that is not this player
	 * object, and return the result.
	 * 
	 * Throws an Error if no opponent is found.
	 * 
	 * @param board
	 *            Board to consider
	 * @return The opponent player on the specified board
	 */
	private Player getOpponent(ReadableBoard board)
	{
		int l = board.getWidth();
		int m = board.getHeight();
		for (int i = 0; i != l; ++i)
		{
			for (int j = 0; j != m; ++j)
			{
				Player here = board.whoPlayed(i, j);
				if (here != null && here != this)
				{
					return here;
				}
			}
		}
		throw new Error("Can't call getOpponent on first turn.");
	}
	
}
