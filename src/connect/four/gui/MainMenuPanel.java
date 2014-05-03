/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connect.four.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle;

@SuppressWarnings("serial")
public class MainMenuPanel extends JPanel
{
	GUI gui;
	private boolean aiPlayerEnabled;
	
	public MainMenuPanel(GUI gui)
	{
		initComponents();
		setSize(1280, 800);
		this.gui = gui;
		aiPlayerEnabled = false;
		
		setVisible(true);
	}
	
	private void initComponents()
	{
		title = new JLabel();
		tfplayer1 = new JTextField();
		tfplayer2 = new JTextField();
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		playButton = new JButton();
		toggleComputerButton = new JToggleButton();
		
		setBackground(new Color(0, 0, 0));
		
		title.setFont(new Font("Lucida Grande", 0, 48)); // NOI18N
		title.setForeground(new Color(255, 255, 255));
		title.setText("Connect Four");
		
		tfplayer1.setText("Player 1");
		
		tfplayer2.setText("Player 2");
		
		jLabel1.setFont(new Font("Lucida Grande", 0, 18)); // NOI18N
		jLabel1.setForeground(new Color(255, 255, 255));
		jLabel1.setText("Player 1:");
		
		jLabel2.setFont(new Font("Lucida Grande", 0, 18)); // NOI18N
		jLabel2.setForeground(new Color(255, 255, 255));
		jLabel2.setText("Player 2:");
		
		playButton.setFont(new Font("Lucida Grande", 0, 36)); // NOI18N
		playButton.setText("PLAY");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				playButtonPressed(evt);
			}
		});
		
		toggleComputerButton.setFont(new Font("Lucida Grande", 0, 18)); // NOI18N
		toggleComputerButton.setText("Play Against Computer");
		toggleComputerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt)
			{
				toggleComputer(evt);
			}
		});
		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(
						GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(title).addGap(471, 471, 471))
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(378,
																		378,
																		378)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.TRAILING,
																				false)
																				.addGroup(
																						GroupLayout.Alignment.LEADING,
																						layout.createSequentialGroup()
																								.addComponent(
																										jLabel1)
																								.addGap(18,
																										18,
																										18)
																								.addComponent(
																										tfplayer1))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										jLabel2)
																								.addGap(18,
																										18,
																										18)
																								.addComponent(
																										tfplayer2,
																										GroupLayout.PREFERRED_SIZE,
																										431,
																										GroupLayout.PREFERRED_SIZE))))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(524,
																		524,
																		524)
																.addGroup(
																		layout.createParallelGroup(
																				GroupLayout.Alignment.LEADING,
																				false)
																				.addComponent(
																						playButton,
																						GroupLayout.DEFAULT_SIZE,
																						232,
																						Short.MAX_VALUE)
																				.addComponent(
																						toggleComputerButton,
																						GroupLayout.PREFERRED_SIZE,
																						0,
																						Short.MAX_VALUE))))
								.addContainerGap(379, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addGap(38, 38, 38)
						.addComponent(title)
						.addGap(59, 59, 59)
						.addGroup(
								layout.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(tfplayer1,
												GroupLayout.PREFERRED_SIZE, 45,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(jLabel1))
						.addGap(18, 18, 18)
						.addGroup(
								layout.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(tfplayer2,
												GroupLayout.PREFERRED_SIZE, 45,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(jLabel2))
						.addPreferredGap(
								LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(toggleComputerButton,
								GroupLayout.PREFERRED_SIZE, 52,
								GroupLayout.PREFERRED_SIZE)
						.addGap(45, 45, 45)
						.addComponent(playButton, GroupLayout.PREFERRED_SIZE, 76,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(352, Short.MAX_VALUE)));
	}
	
	private void playButtonPressed(ActionEvent evt)
	{
		gui.setPlayer1Name(tfplayer1.getText());
		gui.setPlayer2Name(tfplayer2.getText());
		gui.remove(this);
		gui.addGamePanel();
		gui.updateDisplay();
	}
	
	private void toggleComputer(ActionEvent evt)
	{
		if (!aiPlayerEnabled)
		{
			tfplayer2.setText("Computer");
			tfplayer2.setEditable(false);
			aiPlayerEnabled = true;
		}
		else
		{
			tfplayer2.setText("Player 2");
			tfplayer2.setEditable(true);
			aiPlayerEnabled = false;
		}
		
	}
	
	public boolean isAIEnabled()
	{
		return aiPlayerEnabled;
	}
	
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JButton playButton;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JToggleButton toggleComputerButton;
	private JTextField tfplayer1;
	private JTextField tfplayer2;
	private JLabel title;
	// End of variables declaration//GEN-END:variables
}
