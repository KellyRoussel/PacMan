package inDev;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar extends JPanel{

	
	private int score = 0;
	private int lives = 0;
	private JLabel scoreLabel = null;
	private JLabel livesLabel = null;
	private JLabel fruitsLabel = null;
	
	public StatusBar() {
		setBackground(Color.black);
		setPreferredSize(new Dimension(600, 150));
		setLayout(new GridLayout(1, 3));
		scoreLabel = new JLabel();
		
		scoreLabel.setFont(new Font("Trattatello", Font.BOLD, 30));

		scoreLabel.setAlignmentX(CENTER_ALIGNMENT);
		scoreLabel.setAlignmentY(CENTER_ALIGNMENT);
		updateScore();
		add(scoreLabel);
	
	}
	
	public void updateScore() {
		scoreLabel.setText("<html><font color='WHITE'>SCORE </font> <font color = 'YELLOW'>" + score + "</font></html>");
	}
	
	public void incrementScore(int value) {
		score += value;
	}
	
	
	public void load() {
		
	}
	public void update() {
		
	}
}
