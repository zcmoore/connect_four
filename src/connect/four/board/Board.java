package connect.four.board;

import connect.four.player.Player;
import java.util.Arrays;

/**
 * ConnectFour board which can be read from and written to.
 * 
 * @see ReadableBoard
 * @see	WritableBoard
 *
 */
public class Board implements ReadWritableBoard
{
	/** The player that has played in each tile. Null represents an empty tile */
	Player[][] boardContents;
	
	/** Number of moves that have been made on this board */
	int moveCount;
	
	/**
	 * Creates an empty board of the specified dimensions
	 * 
	 * @param width
	 *            Number of columns
	 * @param height
	 *            Number of rows
	 */
	public Board(int width, int height)
	{
		boardContents = new Player[width][height];
		moveCount = 0;
	}
	
	/**
	 * Creates a new board object based on a different board object.
	 * 
	 * @param copy
	 *            Board to copy from
	 */
	public Board(ReadableBoard copy)
	{
		if (copy instanceof Board)
		{
			Board secondCopy = (Board) copy;
			this.moveCount = secondCopy.moveCount;
			int width = secondCopy.boardContents.length;
			int height = secondCopy.boardContents[0].length;
			boardContents = new Player[width][height];
			for (int columnIndex = 0; columnIndex != width; ++columnIndex)
			{
				boardContents[columnIndex] = Arrays.copyOf(
						secondCopy.boardContents[columnIndex], height);
			}
		}
		else
		{
			int width = copy.getWidth();
			int height = copy.getHeight();
			boardContents = new Player[width][height];
			moveCount = copy.getMoveCount();
			for (int columnIndex = 0; columnIndex != width; ++columnIndex)
			{
				for (int rowIndex = 0; rowIndex != height; ++rowIndex)
				{
					boardContents[columnIndex][rowIndex] = copy.whoPlayed(
							columnIndex, rowIndex);
				}
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see connect.four.board.ReadableBoard#whoPlayed(int, int)
	 */
	public @Override
	Player whoPlayed(int x, int y)
	{
		return boardContents[x][y];
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see connect.four.board.ReadableBoard#getWidth()
	 */
	public @Override
	int getWidth()
	{
		return boardContents.length;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see connect.four.board.ReadableBoard#getHeight()
	 */
	public @Override
	int getHeight()
	{
		return boardContents[0].length;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see connect.four.board.WritableBoard#play(int,
	 * connect.four.player.Player)
	 */
	public @Override
	void play(int x, Player p)
	{
		int y = getColumnHeight(x);
		if (y == boardContents[x].length)
		{
			throw new ColumnFullException();
		}
		boardContents[x][y] = p;
		moveCount += 1;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see connect.four.board.ReadableBoard#getColumnHeight(int)
	 */
	@Override
	public int getColumnHeight(int x)
	{
		int y = 0;
		int l = boardContents[0].length;
		
		while (y != l && boardContents[x][y] != null)
		{
			y += 1;
		}
		
		return y;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see connect.four.board.ReadableBoard#isColumnFull(int)
	 */
	@Override
	public boolean isColumnFull(int columnIndex)
	{
		return getColumnHeight(columnIndex) >= 6;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see connect.four.board.ReadableBoard#isFull()
	 */
	@Override
	public boolean isFull()
	{
		return (getWidth() * getHeight()) == getMoveCount();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see connect.four.board.WritableBoard#clear()
	 */
	public @Override
	void clear()
	{
		int l = boardContents.length;
		int m = boardContents[0].length;
		for (int i = 0; i != l; ++i)
		{
			boardContents[i] = new Player[m];
		}
		moveCount = 0;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see connect.four.board.ReadableBoard#getMoveCount()
	 */
	public @Override
	int getMoveCount()
	{
		return moveCount;
	}
}
