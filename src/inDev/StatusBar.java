package inDev;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar extends JPanel{

	
	private int score = 0;
	private int lives = 3;
	
	private JLabel scoreLabel = null;
	private JPanel fruitsPane = null; 
	
	
	public StatusBar() {
		setBackground(Color.black);
		setPreferredSize(new Dimension(600, 150));
		setLayout(new GridLayout(1, 3));
		
		// Creation du score
		
		scoreLabel = new JLabel();
		scoreLabel.setFont(new Font("Trattatello", Font.BOLD, 25));
		scoreLabel.setAlignmentX(CENTER_ALIGNMENT);
		scoreLabel.setAlignmentY(CENTER_ALIGNMENT);
		updateScore();
		add(scoreLabel);
		
		 // Creation des vies
		
		JPanel lvPane = new JPanel(new GridLayout(1, 4));
		lvPane.setBackground(Color.black);
		JLabel livesPn = new JLabel("<html><font color='WHITE'>LIVES </font></html>");
		livesPn.setFont(new Font("Trattatello", Font.BOLD, 25));
		livesPn.setAlignmentX(CENTER_ALIGNMENT);
		livesPn.setAlignmentY(CENTER_ALIGNMENT);
		lvPane.add(livesPn);
		
		for(int i = 0; i < lives; i++) {
			ImageIcon icon = new ImageIcon("ressources" + File.separator + "life.png");
			JLabel thumb = new JLabel();
			thumb.setBackground(Color.black);
			thumb.setIcon(icon);
			
			lvPane.add(thumb);
		}
		
		add(lvPane);
		
		fruitsPane = new JPanel();
		fruitsPane.setBackground(Color.black);
		add(fruitsPane);
	
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
