package connect.four.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class GUI extends JFrame
{
	MainMenuPanel mainMenu;
	GamePanel gamePanel;
	GameOverPanel gameOverPanel;
	String p1Name;
	String p2Name;
	String winner;
	int score1, score2;
	
	public GUI()
	{
		initComponents();
		score1 = 0;
		score2 = 0;
		mainMenu = new MainMenuPanel(this);
		add(mainMenu);
		
	}
	
	private void initComponents()
	{
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 1280, Short.MAX_VALUE));
		
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGap(0, 800, Short.MAX_VALUE));
		
		pack();
	}
	
	public static void main(String args[])
	{
		/* Set the Nimbus look and feel */
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try
		{
			for (UIManager.LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch (ClassNotFoundException ex)
		{
			logException(Level.SEVERE, null, ex);
		}
		catch (InstantiationException ex)
		{
			logException(Level.SEVERE, null, ex);
		}
		catch (IllegalAccessException ex)
		{
			logException(Level.SEVERE, null, ex);
		}
		catch (UnsupportedLookAndFeelException ex)
		{
			logException(Level.SEVERE, null, ex);
		}
		
		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				new GUI().setVisible(true);
			}
		});
		
	}
	
	private static void logException(Level level, String msg, Throwable thrown)
	{
		Logger.getLogger(GUI.class.getName()).log(level, msg, thrown);
	}
	
	// Methods
	void updateDisplay()
	{
		revalidate();
		repaint();
	}
	
	void setPlayer1Name(String name)
	{
		p1Name = name;
	}
	
	void setPlayer2Name(String name)
	{
		p2Name = name;
	}
	
	String getPlayer1Name()
	{
		return p1Name;
	}
	
	String getPlayer2Name()
	{
		return p2Name;
	}
	
	void addGamePanel()
	{
		gamePanel = new GamePanel(this, mainMenu.isAIEnabled());
		add(gamePanel);
	}
	
	void removeGamePanel()
	{
		remove(gamePanel);
	}
	
	void addMainMenu()
	{
		score1 = 0;
		score2 = 0;
		mainMenu = new MainMenuPanel(this);
		add(mainMenu);
	}
	
	void addGameOver()
	{
		remove(gamePanel);
		gameOverPanel = new GameOverPanel(this, winner);
		add(gameOverPanel);
		updateDisplay();
	}
	
	void setWinner(String winner)
	{
		this.winner = winner;
	}
	
	int getScore1()
	{
		return score1;
	}
	
	int getScore2()
	{
		return score2;
	}
	
	void setScore1(int newScore)
	{
		score1 = newScore;
	}
	
	void setScore2(int newScore)
	{
		score2 = newScore;
	}
}
