package inDev;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusBar extends JPanel{

	
	private int score = 0;
	private int lives = 0;
	
	public StatusBar() {
		setBackground(Color.black);
		setPreferredSize(new Dimension(600, 150));
		JLabel label = new JLabel("<html><font color='WHITE'>ICI C'EST STATUSBAR</font></html>");
		label.setFont (label.getFont ().deriveFont (20.0f));
		label.setAlignmentX(CENTER_ALIGNMENT);
		label.setAlignmentY(CENTER_ALIGNMENT);
		add(label);
	
	}
	
	public void load() {
		
	}
	public void update() {
		
	}
}
