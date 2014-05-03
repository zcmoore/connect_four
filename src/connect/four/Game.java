package connect.four;

import connect.four.player.Player;
import connect.four.board.ReadableBoard;
import connect.four.board.ReadWritableBoard;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game implements ScoreChart
{
	Player[] players;
	int[] scores;
	List<ScoreChart.Listener> listeners;
	ReadWritableBoard board;
	int inRow;
	int currentPlayerIndex;
	
	public Game(Player[] players, ReadWritableBoard board, int inRow)
	{
		this.players = Arrays.copyOf(players, players.length);
		this.scores = new int[players.length];
		this.listeners = new ArrayList<ScoreChart.Listener>();
		this.board = board;
		this.inRow = inRow;
	}
	
	public void start()
	{
		int first = (new Random()).nextInt(players.length);
		performPlay(first);
	}
	
	/* (non-Javadoc)
	 * @see connect.four.ScoreChart#registerListener(connect.four.ScoreChart.Listener)
	 */
	@Override
	public void registerListener(ScoreChart.Listener l)
	{
		listeners.add(l);
	}
	
	/* (non-Javadoc)
	 * @see connect.four.ScoreChart#unregisterListener(connect.four.ScoreChart.Listener)
	 */
	@Override
	public void unregisterListener(ScoreChart.Listener l)
	{
		listeners.remove(l);
	}
	
	/* (non-Javadoc)
	 * @see connect.four.ScoreChart#getPlayers()
	 */
	@Override
	public List<Player> getPlayers()
	{
		return Arrays.asList(players);
	}
	
	/* (non-Javadoc)
	 * @see connect.four.ScoreChart#getScore(connect.four.player.Player)
	 */
	@Override
	public int getScore(Player p)
	{
		int pos = -1;
		int l = players.length;
		for (int i = 0; i != l; ++i)
		{
			if (players[i] == p)
				pos = i;
		}
		return scores[pos];
	}
	
	void performPlay(final int player)
	{
		currentPlayerIndex = player;
		ReadWritableBoard controlledBoard = new ReadWritableBoard() {
			boolean played;
			
			@Override
			public Player whoPlayed(int x, int y)
			{
				return board.whoPlayed(x, y);
			}
			
			@Override
			public void play(int x, Player p)
			{
				if (played)
				{
					throw new Error(p + " Played more than once in a turn.");
				}
				played = true;
				board.play(x, p);
				Player win = detectWinner(board, inRow);
				if (win != null)
				{
					scores[player] += 1;
					for (ScoreChart.Listener l : listeners)
					{
						l.gameOver(win, Game.this, board);
					}
					board.clear();
					performPlay(player);
				}
				else if (board.getMoveCount() == board.getWidth()
						* board.getHeight())
				{
					for (ScoreChart.Listener l : listeners)
					{
						l.gameOver(null, Game.this, board);
					}
					board.clear();
					performPlay((player + 1) % players.length);
				}
				else
				{
					performPlay((player + 1) % players.length);
				}
			}
			
			@Override
			public void clear()
			{
				board.clear();
			}
			
			@Override
			public int getWidth()
			{
				return board.getWidth();
			}
			
			@Override
			public int getHeight()
			{
				return board.getHeight();
			}
			
			@Override
			public int getColumnHeight(int x)
			{
				return board.getColumnHeight(x);
			}
			
			@Override
			public int getMoveCount()
			{
				return board.getMoveCount();
			}

			@Override
			public boolean isColumnFull(int columnIndex)
			{
				return getColumnHeight(columnIndex) >= 6;
			}

			@Override
			public boolean isFull()
			{
				return (getWidth() * getHeight()) == getMoveCount();
			}
		};
		players[player].performPlay(controlledBoard);
	}
	
	public Player getCurrentPlayer()
	{
		return players[currentPlayerIndex];
	}
	
	public int getInRow()
	{
		return inRow;
	}
	
	public ReadableBoard getBoard()
	{
		return board;
	}
	
	public static Player detectWinner(ReadableBoard board, int inRow)
	{
		int l = board.getWidth();
		int m = board.getHeight();
		for (int i = 0; i != l; ++i)
		{
			Player possible = null;
			int found = 0;
			for (int j = 0; j != m; ++j)
			{
				if (board.whoPlayed(i, j) == possible && possible != null)
				{
					found += 1;
				}
				else
				{
					found = 1;
					possible = board.whoPlayed(i, j);
				}
				if (found == inRow)
				{
					return possible;
				}
			}
		}
		for (int i = 0; i != m; ++i)
		{
			Player possible = null;
			int found = 0;
			for (int j = 0; j != l; ++j)
			{
				if (board.whoPlayed(j, i) == possible && possible != null)
				{
					found += 1;
				}
				else
				{
					found = 1;
					possible = board.whoPlayed(j, i);
				}
				if (found == inRow)
				{
					return possible;
				}
			}
		}
		for (int i = -l; i != l; ++i)
		{
			Player possible = null;
			int found = 0;
			for (int j = 0; j != m; ++j)
			{
				int k = j + i;
				if (k >= 0 && k < l)
				{
					if (board.whoPlayed(k, j) == possible && possible != null)
					{
						found += 1;
					}
					else
					{
						found = 1;
						possible = board.whoPlayed(k, j);
					}
					if (found == inRow)
					{
						return possible;
					}
				}
			}
		}
		for (int i = -l; i != l; ++i)
		{
			Player possible = null;
			int found = 0;
			for (int j = 0; j != m; ++j)
			{
				int k = j + i;
				if (k >= 0 && k < l)
				{
					if (board.whoPlayed(l - k - 1, j) == possible
							&& possible != null)
					{
						found += 1;
					}
					else
					{
						found = 1;
						possible = board.whoPlayed(l - k - 1, j);
					}
					if (found == inRow)
					{
						return possible;
					}
				}
			}
		}
		return null;
	}
}
