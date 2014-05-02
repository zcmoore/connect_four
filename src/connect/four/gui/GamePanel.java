/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connect.four.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import connect.four.Game;
import connect.four.ScoreChart;
import connect.four.board.Board;
import connect.four.board.ReadableBoard;
import connect.four.player.ComputerPlayer;
import connect.four.player.Player;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ScoreChart.Listener
{
	
	private GUI gui;
	private static final int PLAY_TIME = 1500;
	private boolean falling;
	private int columnNum;
	private int turnNum;
	private int newDrawPos;
	private int newColumnNum;
	private Player[] players;
	private Game game;
	private GUIPiece[] pieces;
	private Board board;
	private boolean isComputerEnabled;
	private boolean justWon;
	
	private JLabel bgImage;
	private JPanel col1;
	private JPanel col2;
	private JPanel col3;
	private JPanel col4;
	private JPanel col5;
	private JPanel col6;
	private JPanel col7;
	private JLabel currentWins;
	private JLabel pNameDisplay;
	private JLabel player1NameBox;
	private JLabel player2NameBox;
	private JPanel topGlass;
	private JLabel turnDisplay;
	
	private class GlowTimer extends Timer
	{
		public GlowTimer(int delay)
		{
			super(delay, new GlowListener());
		}
	}
	
	private class GlowListener implements ActionListener
	{
		private static final int NUMBER_OF_TICKS = 6;
		private int tick;
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			for (GUIPiece piece : pieces)
			{
				if (piece != null)
				{
					if (piece.getParent() == topGlass)
						piece.setIcon(piece.getGlow(tick));
				}
			}
			
			tick = (tick + 1) % NUMBER_OF_TICKS;
			topGlass.invalidate();
			topGlass.revalidate();
			topGlass.repaint();
		}
	}
	
	private class ColumnMouseAdapter extends MouseAdapter
	{
		private int column;
		
		public ColumnMouseAdapter(int column)
		{
			super();
			this.column = column;
		}
		
		public void mouseClicked(java.awt.event.MouseEvent evt)
		{
			super.mouseClicked(evt);
			if (game.getCurrentPlayer() != players[1] || !isComputerEnabled)
			{
				turn();
			}
		}
		
		public void mouseExited(java.awt.event.MouseEvent evt)
		{
			super.mouseExited(evt);
		}
		
		public void mouseEntered(java.awt.event.MouseEvent evt)
		{
			super.mouseEntered(evt);
			calcNewPos(column - 1);
			calcWidth(column - 1);
		}
	}
	
	public GamePanel(GUI gui, boolean isComputerEnabled)
	{
		players = new Player[2];
		players[0] = new GUIPlayer(gui.getPlayer1Name(), this);
		this.isComputerEnabled = isComputerEnabled;
		if (!isComputerEnabled)
		{
			players[1] = new GUIPlayer(gui.getPlayer2Name(), this);
		}
		else
		{
			players[1] = new GUIWrapperPlayer(new ComputerPlayer(), this);
		}
		setSize(1280, 800);
		initComponents();
		this.gui = gui;
		board = new Board(7, 6);
		game = new Game(players, board, 4);
		game.start();
		game.registerListener(this);
		justWon = false;
		
		GlowTimer glowTimer = new GlowTimer(100);
		glowTimer.start();
		
		initNewGame();
	}
	
	private void initComponents()
	{
		player1NameBox = new JLabel();
		currentWins = new JLabel();
		pNameDisplay = new JLabel();
		turnDisplay = new JLabel();
		col1 = new JPanel();
		col2 = new JPanel();
		col3 = new JPanel();
		col4 = new JPanel();
		col5 = new JPanel();
		col6 = new JPanel();
		topGlass = new JPanel();
		col7 = new JPanel();
		player2NameBox = new JLabel();
		bgImage = new JLabel();
		
		setBackground(new java.awt.Color(0, 0, 0));
		setPreferredSize(new java.awt.Dimension(1280, 800));
		setLayout(null);
		
		player1NameBox.setFont(new java.awt.Font("Lucida Grande", 0, 18));
		player1NameBox.setForeground(new java.awt.Color(255, 255, 255));
		player1NameBox.setText("player 1");
		add(player1NameBox);
		player1NameBox.setBounds(1070, 40, 210, 40);
		
		currentWins.setFont(new java.awt.Font("Lucida Grande", 0, 18));
		currentWins.setForeground(new java.awt.Color(255, 255, 255));
		currentWins.setText("CURRENT WINS");
		add(currentWins);
		currentWins.setBounds(1070, 0, 200, 40);
		
		pNameDisplay.setFont(new java.awt.Font("Lucida Grande", 0, 18));
		pNameDisplay.setForeground(new java.awt.Color(255, 255, 255));
		pNameDisplay.setText("jLabel2");
		add(pNameDisplay);
		pNameDisplay.setBounds(1070, 210, 200, 40);
		
		turnDisplay.setFont(new java.awt.Font("Lucida Grande", 0, 18));
		turnDisplay.setForeground(new java.awt.Color(255, 255, 255));
		turnDisplay.setText("jLabel2");
		add(turnDisplay);
		turnDisplay.setBounds(1070, 150, 200, 40);
		
		col1.setBackground(new java.awt.Color(102, 102, 102));
		col1.setOpaque(false);
		col1.addMouseListener(new ColumnMouseAdapter(1));
		col1.setLayout(null);
		add(col1);
		col1.setBounds(0, 0, 310, 740);
		
		col2.setBackground(new java.awt.Color(102, 102, 102));
		col2.setOpaque(false);
		col2.addMouseListener(new ColumnMouseAdapter(2));
		col2.setLayout(null);
		add(col2);
		col2.setBounds(320, 0, 80, 740);
		
		col3.setBackground(new java.awt.Color(102, 102, 102));
		col3.setOpaque(false);
		col3.addMouseListener(new ColumnMouseAdapter(3));
		col3.setLayout(null);
		add(col3);
		col3.setBounds(410, 10, 80, 740);
		
		col4.setBackground(new java.awt.Color(102, 102, 102));
		col4.setOpaque(false);
		col4.addMouseListener(new ColumnMouseAdapter(4));
		col4.setLayout(null);
		add(col4);
		col4.setBounds(500, -10, 80, 740);
		
		col5.setBackground(new java.awt.Color(102, 102, 102));
		col5.setOpaque(false);
		col5.addMouseListener(new ColumnMouseAdapter(5));
		col5.setLayout(null);
		add(col5);
		col5.setBounds(590, 0, 80, 740);
		
		col6.setBackground(new java.awt.Color(102, 102, 102));
		col6.setOpaque(false);
		col6.addMouseListener(new ColumnMouseAdapter(6));
		col6.setLayout(null);
		add(col6);
		col6.setBounds(680, -30, 80, 740);
		
		topGlass.setBackground(new java.awt.Color(102, 102, 102));
		topGlass.setOpaque(false);
		topGlass.setLayout(null);
		
		col7.setBackground(new java.awt.Color(102, 102, 102));
		col7.setOpaque(false);
		col7.addMouseListener(new ColumnMouseAdapter(7));
		col7.setLayout(null);
		// topGlass.add(col7);
		add(col7);
		col7.setBounds(770, 0, 300, 740);
		
		player2NameBox.setFont(new java.awt.Font("Lucida Grande", 0, 18));
		player2NameBox.setForeground(new java.awt.Color(255, 255, 255));
		player2NameBox.setText("player 1");
		topGlass.add(player2NameBox);
		player2NameBox.setBounds(1070, 90, 210, 40);
		
		add(topGlass);
		topGlass.setBounds(0, 0, 1280, 800);
		
		bgImage.setIcon(new ImageIcon(getClass().getResource("/board.png")));
		bgImage.setText("jLabel1");
		bgImage.setIgnoreRepaint(true);
		add(bgImage);
		bgImage.setBounds(0, 150, 1070, 590);
	}
	
	void dropPiece()
	{
		falling = true;
		final int destination = getTargetY();
		final long startTime = System.currentTimeMillis();
		pieces[turnNum].setVisible(true);
		Timer timer = new Timer((8000 / (destination)), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int x = pieces[turnNum].getX();
				int y = pieces[turnNum].getY();
				int startY = y;
				long duration = System.currentTimeMillis() - startTime;
				float progress = (float) duration / (float) PLAY_TIME;
				
				y = y + (int) Math.round((destination - startY) * progress);
				
				if ((progress > 1f) || (y >= getTargetY()))
				{
					progress = 1f;
					y = getTargetY();
					((Timer) (e.getSource())).stop();
					falling = false;
					System.out.println("Piece fell.");
					if (!isComputerEnabled)
					{
						GUIPlayer player = (GUIPlayer) game.getCurrentPlayer();
						player.getBoard().play(getColumnNum(), player);
					}
					else
					{
						if (game.getCurrentPlayer() == players[0])
						{
							GUIPlayer player = (GUIPlayer) game
									.getCurrentPlayer();
							player.getBoard().play(getColumnNum(), player);
						}
						else if (game.getCurrentPlayer() == players[1])
						{
							GUIWrapperPlayer player = (GUIWrapperPlayer) game
									.getCurrentPlayer();
							player.getBoard().play(getColumnNum(), player);
						}
						
					}
					turnUp();
				}
				
				pieces[turnNum].setLocation(x, y);
			}
		});
		timer.start();
	}
	
	public int getTargetY()
	{
		int height = board.getColumnHeight(getColumnNum());
		switch (height)
		{
			case 0:
				return 605;
			case 1:
				return 513;
			case 2:
				return 421;
			case 3:
				return 330;
			case 4:
				return 242;
			case 5:
				return 150;
			default:
				return 150;
		}
	}
	
	public boolean isValidMove()
	{
		boolean valid = true;
		if (board.getColumnHeight(getColumnNum()) > 5)
		{
			valid = false;
		}
		return valid;
	}
	
	public int getColumnNum()
	{
		return columnNum;
	}
	
	public void calcWidth(int columnEntered)
	{
		int xPos = 0;
		
		switch (columnEntered)
		{
			case 0:
				xPos = 190;
				break;
			case 1:
				xPos = 280;
				break;
			case 2:
				xPos = 372;
				break;
			case 3:
				xPos = 463;
				break;
			case 4:
				xPos = 554;
				break;
			case 5:
				xPos = 645;
				break;
			case 6:
				xPos = 738;
				break;
		}
		
		if (falling == false)
		{
			columnNum = columnEntered;
			pieces[turnNum].setLocation(xPos, 0);
			revalidate();
			repaint();
		}
	}
	
	void calcNewPos(int columnEntered)
	{
		int xPos = 0;
		
		switch (columnEntered)
		{
			case 0:
				xPos = 190;
				break;
			case 1:
				xPos = 280;
				break;
			case 2:
				xPos = 372;
				break;
			case 3:
				xPos = 463;
				break;
			case 4:
				xPos = 554;
				break;
			case 5:
				xPos = 645;
				break;
			case 6:
				xPos = 738;
				break;
		}
		
		newDrawPos = xPos;
		newColumnNum = columnEntered;
	}
	
	void turnUp()
	{
		if (!justWon)
		{
			if (falling == false)
			{
				// move piece to top glass, for glow.
				topGlass.add(pieces[turnNum]);
				
				turnNum += 1;
				
				// Displays who is playing this turn
				if (turnNum % 2 == 0)
				{
					pNameDisplay.setForeground(Color.red);
				}
				else
				{
					pNameDisplay.setForeground(Color.blue);
				}
				
				// Turn goes up, unless there is a tie
				if (turnNum == 42)
				{
					for (GUIPiece piece : pieces)
					{
						piece.setIcon(null);
						topGlass.remove(piece);
					}
					gui.setWinner("It's a tie!");
					board.clear();
					initNewGame();
					gui.addGameOver();
				}
				else
				{
					pieces[turnNum] = new GUIPiece(turnNum % 2);
					pieces[turnNum].setLocation(newDrawPos, 0);
					if (game.getCurrentPlayer() == players[1]
							&& isComputerEnabled)
					{
						pieces[turnNum].setVisible(false);
					}
					columnNum = newColumnNum;
					System.out.println("Turn " + (turnNum + 1) + " Started!");
					pNameDisplay.setText(game.getCurrentPlayer().getName()
							+ "'s turn.");
					turnDisplay.setText("Round number " + (turnNum / 2 + 1));
					add(pieces[turnNum]);
				}
				
				// Makes the round display turn red if there are 10 or less
				// turns
				if (turnNum >= 32)
				{
					// Turns red to show 10 turns left
					turnDisplay.setForeground(Color.red);
				}
				
				revalidate();
				repaint();
				
			}
		}
		else
		{
			justWon = false;
		}
	}
	
	public void turn()
	{
		if (falling == false && isValidMove())
		{
			System.out.println(getColumnNum());
			System.out.println(board.getColumnHeight(getColumnNum()));
			dropPiece();
		}
	}
	
	void initNewGame()
	{
		turnNum = 0;
		columnNum = 0;
		falling = false;
		pieces = new GUIPiece[43];
		pieces[turnNum] = new GUIPiece(turnNum);
		pieces[turnNum].setLocation(newDrawPos, 0);
		if (game.getCurrentPlayer() == players[1] && isComputerEnabled)
		{
			pieces[turnNum].setVisible(false);
		}
		add(pieces[turnNum]);
		pNameDisplay.setForeground(Color.red);
		pNameDisplay.setText(game.getCurrentPlayer().getName() + "'s turn.");
		turnDisplay.setForeground(Color.white);
		turnDisplay.setText("Round number " + (turnNum / 2 + 1));
		player1NameBox
				.setText(players[0].getName() + ":    " + gui.getScore1());
		player2NameBox
				.setText(players[1].getName() + ":    " + gui.getScore2());
		setVisible(true);
	}
	
	void globalGlow()
	{
		
	}
	
	// GAME OVER
	@Override
	public void gameOver(Player winner, ScoreChart scores, ReadableBoard end)
	{
		if (turnNum < 41)
		{
			if (game.getCurrentPlayer() == players[0])
			{
				gui.setScore1(gui.getScore1() + 1);
			}
			else if (game.getCurrentPlayer() == players[1])
			{
				gui.setScore2(gui.getScore2() + 1);
			}
			gui.setWinner(game.getCurrentPlayer().getName());
			
			board.clear();
			for (GUIPiece piece : pieces)
			{
				if (piece != null)
				{
					piece.setIcon(null);
					topGlass.remove(piece);
				}
			}
			
			initNewGame();
			gui.addGameOver();
			justWon = true;
		}
	}
	
}
