package connect.four.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import connect.four.ScoreChart;
import connect.four.board.ReadWritableBoard;
import connect.four.board.ReadableBoard;

/**
 * Player implementation to be used on the console. ConsolePlayer's
 * {@link #performPlay(ReadWritableBoard)} algorithm requests and accepts input
 * from System.in, and uses that to make a move. This implementation is intended
 * for use with a human player only.
 * 
 */
public class ConsolePlayer implements Player, ScoreChart.Listener
{
	/** Name of this player */
	String name;
	
	/**
	 * Constructs a ConsolePlayer with the specified name.
	 * 
	 * @param name
	 *            Name of the new player
	 */
	public ConsolePlayer(String name)
	{
		this.name = name;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see connect.four.player.Player#getName()
	 */
	@Override
	public String getName()
	{
		return name;
	}
	
	/**
	 * @param name
	 *            New name of this player
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * Prints the current score, and prints the given board.
	 * 
	 * @see
	 * connect.four.ScoreChart.Listener#gameOver(connect.four.player.Player,
	 * connect.four.ScoreChart, connect.four.board.ReadableBoard)
	 */
	@Override
	public void gameOver(Player winner, ScoreChart scores, ReadableBoard board)
	{
		System.out.println(name + (winner == this ? " won." : " lost."));
		dumpBoard(board);
		System.out.println(name + ": " + scores.getScore(this));
	}
	
	/* (non-Javadoc)
	 * @see connect.four.player.Player#performPlay(connect.four.board.ReadWritableBoard)
	 */
	@Override
	public void performPlay(ReadWritableBoard board)
	{
		int width = board.getWidth();
		
		System.out.println("\n" + name + "'s turn!");
		dumpBoard(board);
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		int x = 0;
		while (x < 1 || x > width)
		{
			try
			{
				System.out.print("Enter the column you want to play in: ");
				x = Integer.parseInt(stdin.readLine());
			}
			catch (IOException e)
			{
				// loop again.
			}
			catch (NumberFormatException e)
			{
				// loop again.
			}
		}
		
		board.play(x - 1, this);
	}
	
	/**
	 * Prints the given board to the console.
	 * 
	 * @param board Board to be printed
	 */
	private void dumpBoard(ReadableBoard board)
	{
		int width = board.getWidth();
		int height = board.getHeight();
		
		System.out.println("@ is you, X is the other player, and O is empty.");
		for (int i = height - 1; i != -1; --i)
		{
			for (int j = 0; j != width; ++j)
			{
				Player played = board.whoPlayed(j, i);
				System.out
						.print(played == this ? "@" : played == null ? "O" : "X");
			}
			System.out.println();
		}
		for (int i = 0; i != width; ++i)
		{
			System.out.print(i + 1);
		}
		System.out.println();
	}
}
