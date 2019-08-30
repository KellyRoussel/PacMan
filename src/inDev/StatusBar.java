package inDev;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar extends JPanel{

	
	private int score = 0;
	private int lives = 0;
	
	public StatusBar() {
		setPreferredSize(new Dimension(600, 150));
		
		setLayout(new BorderLayout());
		
		add(new JLabel("score"), BorderLayout.CENTER);
		add(new JLabel("vies"), BorderLayout.LINE_START);
		add(new JLabel("niveau"), BorderLayout.LINE_END);
	}
	
	public void load() {
		
	}
	public void update() {
		
	}
}
