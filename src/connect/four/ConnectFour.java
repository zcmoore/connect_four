package connect.four;

import java.io.*;
import connect.four.player.*;
import connect.four.board.*;

/**
 * Main class used for playing a console version of ConnectFour. This class uses
 * ConsolePlayer to interface the user to the board, and supports only human
 * players. For a GUI implementation of ConnectFour, see connect.four.GUI.GUI.
 * 
 * @see connect.four.GUI.GUI
 * @see connect.four.player.ConsolePlayer
 * 
 */
public class ConnectFour
{
	/**
	 * @param args
	 *            the command line arguments; unused
	 */
	public static void main(String[] args) throws IOException
	{
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Player's name: ");
		ConsolePlayer p1 = new ConsolePlayer(stdin.readLine());
		System.out.print("How many players (1/2)?");
		Player p2;
		if (stdin.readLine().equals("1"))
		{
			p2 = new ComputerPlayer();
		}
		else
		{
			System.out.print("Other player's name: ");
			p2 = new ConsolePlayer(stdin.readLine());
		}
		Game game = new Game(new Player[] { p1, p2 }, new Board(7, 6), 4);
		game.registerListener(p1);
		if (p2 instanceof ScoreChart.Listener)
			game.registerListener((ScoreChart.Listener) p2);
		game.start();
	}
	
}
