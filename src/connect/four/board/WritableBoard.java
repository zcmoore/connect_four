package connect.four.board;

import connect.four.player.Player;

/**
 * Game Board upon which moves can be made.
 * 
 */
public interface WritableBoard
{
	/**
	 * Adds a piece to this board. The player specifies which piece will be
	 * added; x specifies which column the piece will be added to.
	 * 
	 * @param x Which column to add the piece to
	 * @param p Which player will the piece belong to
	 */
	void play(int x, Player p);
	
	/**
	 * Remove all pieces from the board.
	 */
	void clear();
}
