package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import connect.four.Game;
import connect.four.board.Board;
import connect.four.board.ReadWritableBoard;
import connect.four.player.Player;

public class TestGame
{
	final int boardWidth = 5;
	final int boardHeight = 5;
	DummyPlayer player;
	DummyPlayer opponent;
	Board board;
	Game game;
	
	public static class DummyPlayer implements Player
	{
		private int nextMove;
		
		@Override
		public String getName()
		{
			return "Tester";
		}
		
		@Override
		public void performPlay(ReadWritableBoard board)
		{
			board.play(nextMove, this);
		}
		
		public int getNextMove()
		{
			return nextMove;
		}
		
		public void setNextMove(int nextMove)
		{
			this.nextMove = nextMove;
		}
	}
	
	@Before
	public void setUp() throws Exception
	{
		this.player = new DummyPlayer();
		this.opponent = new DummyPlayer();
		this.board = new Board(boardWidth, boardHeight);
		this.game = new Game(new Player[] { player, opponent }, board, 4);
	}
	
	@After
	public void tearDown() throws Exception
	{
		this.player = null;
		this.opponent = null;
		this.board = null;
	}
	
	@Test
	public void testPlayers()
	{
		assertEquals("Expected 2 players", 2, game.getPlayers().size());
		assertTrue(game.getPlayers() instanceof List);
		
		Player p0 = game.getPlayers().get(0);
		Player p1 = game.getPlayers().get(1);
		assertTrue((p0 == player) || (p0 == opponent));
		assertTrue((p1 == player) || (p1 == opponent));
		assertTrue(p0 != p1);
	}
	
	@Test
	public void testInitialScores()
	{
		assertEquals("Expected initial score 0", 0, game.getScore(player));
		assertEquals("Expected initial score 0", 0, game.getScore(opponent));
	}
	
	@Test
	public void testScores()
	{
		try
		{
			game.getScore(null);
			fail("null player should fail");
		}
		catch (Exception e)
		{
			try
			{
				game.getScore(new DummyPlayer());
				fail("new player should fail");
			}
			catch (Exception e2)
			{
				
			}
		}
	}
	
	@Test
	public void testGets()
	{
		assertEquals("Test score to win", 4, game.getInRow());
		assertEquals(board, game.getBoard());
	}
	
}
