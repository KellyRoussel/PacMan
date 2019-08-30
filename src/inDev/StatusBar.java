package inDev;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar extends JPanel{

	
	private int score = 0;
	private int lives = 0;
	
	public StatusBar() {
		setPreferredSize(new Dimension(600, 150));
		
		setLayout(new GridLayout(1, 3));
		JLabel scr = new JLabel("score");
		add(scr);
		JLabel lives = new JLabel("vies");
		add(lives);
		JLabel level = new JLabel("niveau");
		add(level);
	}
	
	public void load() {
		
	}
	public void update() {
		
	}
}
