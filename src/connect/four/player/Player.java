package connect.four.player;

import connect.four.board.ReadWritableBoard;

/**
 * Interface used to represent a Player in the game of ConnectFour. This class
 * should be implemented by all players, including both humans and AIs, that use
 * the ConnectFour board.
 * 
 * Players must have a name, and an algorithm to play a piece on a board.
 * 
 * @see connect.four.board.Board
 * 
 */
public interface Player
{
	/**
	 * @return Name of this player
	 */
	String getName();
	
	/**
	 * Plays a piece on the specified board. Using the board's play method is
	 * suggested, and will take advantage of all optimizations and features in
	 * the board implementation.
	 * 
	 * @param board
	 *            Which board to play on.
	 * @see ReadWritableBoard#play(int, Player)
	 */
	void performPlay(ReadWritableBoard board);
}
