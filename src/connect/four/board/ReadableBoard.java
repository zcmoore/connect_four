package connect.four.board;

import connect.four.player.Player;

/**
 * Game Board capable of being read
 * 
 */
public interface ReadableBoard
{
	/**
	 * Determines who owns the piece at a given location. Returns null if the
	 * tile is empty
	 * 
	 * @param x
	 *            Column index
	 * @param y
	 *            Row index
	 * @return The player who owns the piece at the specified location
	 */
	Player whoPlayed(int x, int y);
	
	/**
	 * @return Number of columns
	 */
	int getWidth();
	
	/**
	 * @return Number of rows
	 */
	int getHeight();
	
	/**
	 * Determines how many pieces have been played in a particular column
	 * 
	 * @param x Column index
	 * @return The number of pieces in column x
	 */
	int getColumnHeight(int x);
	
	/**
	 * @return Number of moves made on this board
	 */
	int getMoveCount();
	
	/** 
	 * @param columnIndex Column to check
	 * @return True if no moves can be made in the specified column
	 */
	boolean isColumnFull(int columnIndex);
	
	/**
	 * @return True if no tiles are empty on this board
	 */
	boolean isFull();
}
