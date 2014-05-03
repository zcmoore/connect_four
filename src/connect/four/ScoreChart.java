package connect.four;

import connect.four.board.ReadableBoard;
import connect.four.player.Player;
import java.util.List;

/**
 * Represents an object which is capable of keeping track of a score.
 *
 */
public interface ScoreChart
{
	/**
	 * Listener to update this score chart
	 *
	 */
	public static interface Listener
	{
		void gameOver(Player winner, ScoreChart scores, ReadableBoard end);
	}
	
	List<Player> getPlayers();
	
	int getScore(Player p);
	
	void registerListener(Listener l);
	
	void unregisterListener(Listener l);
	
}
