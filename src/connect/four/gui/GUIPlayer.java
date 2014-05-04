/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connect.four.gui;

import connect.four.board.ReadWritableBoard;
import connect.four.player.Player;

/**
 * Human player to play on GamePanel
 *
 */
public class GUIPlayer implements Player
{
	private String m_name;
	GamePanel gpGUI;
	ReadWritableBoard board;
	
	/**
	 * Create a player with the specified name, who plays on the given GUI
	 * 
	 * @param name Name of the player
	 * @param gp GamePanel on which to play
	 */
	public GUIPlayer(String name, GamePanel gp)
	{
		m_name = name;
		gpGUI = gp;
	}
	
	/* (non-Javadoc)
	 * @see connect.four.player.Player#getName()
	 */
	@Override
	public String getName()
	{
		return m_name;
	}
	
	/* (non-Javadoc)
	 * @see connect.four.player.Player#performPlay(connect.four.board.ReadWritableBoard)
	 */
	@Override
	public void performPlay(ReadWritableBoard board)
	{
		this.board = board;
		
	}
	
	/**
	 * @return Board used by this player
	 */
	public ReadWritableBoard getBoard()
	{
		return board;
	}
}
