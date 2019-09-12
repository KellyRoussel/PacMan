package Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controllers.GameController;

public class StatusBar extends JPanel{

	
	
	public static final int HEIGHT = 150;
	
	private JLabel levelLabel;
	private JLabel scoreLabel;
	private JPanel fruitsPane; 
	private JPanel lvPane;
	private JLabel collisionPane;
	
	public StatusBar() {
		setBackground(Color.black);
		setPreferredSize(new Dimension(600, HEIGHT));
		setBorder(new EmptyBorder(0, 20, 0, 20));
		setLayout(new GridLayout(2, 3));
		
		JLabel levelL = new JLabel();
		levelL.setFont(new Font("Joystix", Font.BOLD, 25));
		levelL.setText("<html><font color = 'WHITE'> LEVEL </font></html>");
		levelL.setAlignmentX(CENTER_ALIGNMENT);
		levelL.setAlignmentY(CENTER_ALIGNMENT);
		add(levelL);
		
		JLabel scoreL = new JLabel();
		scoreL.setFont(new Font("Joystix", Font.BOLD, 25));
		scoreL.setText("<html><font color = 'WHITE'> SCORE </font></html>");
		scoreL.setAlignmentX(CENTER_ALIGNMENT);
		scoreL.setAlignmentY(CENTER_ALIGNMENT);
		add(scoreL);
		
		
		JLabel livesL = new JLabel();
		livesL.setFont(new Font("Joystix", Font.BOLD, 25));
		livesL.setText("<html><font color = 'WHITE'> VIES </font></html>");
		livesL.setAlignmentX(CENTER_ALIGNMENT);
		livesL.setAlignmentY(CENTER_ALIGNMENT);
		add(livesL);
		
		JLabel CollisionL = new JLabel();
		CollisionL.setFont(new Font("Joystix", Font.BOLD, 25));
		CollisionL.setText("<html><font color = 'WHITE'> COLLISION </font></html>");
		CollisionL.setAlignmentX(CENTER_ALIGNMENT);
		CollisionL.setAlignmentY(CENTER_ALIGNMENT);
		add(CollisionL);
		
		// Creation du niveau		
		JPanel levelPane = new JPanel(new FlowLayout());
		levelPane.setBackground(Color.black);
		levelLabel = new JLabel();
		levelLabel.setText("<html><font color='YELLOW'> 1 </font></html>");
		levelLabel.setFont(new Font("Joystix", Font.BOLD, 25));
		levelPane.add(levelLabel);
		add(levelPane);
				
				
		// Creation du score		
		JPanel scorePane = new JPanel(new FlowLayout());
		scorePane.setBackground(Color.black);
		scoreLabel = new JLabel();
		scoreLabel.setText("<html><font color='WHITE'>SCORE </font></html>");
		scoreLabel.setFont(new Font("Joystix", Font.BOLD, 25));
		scorePane.add(scoreLabel);
		add(scorePane);
		
		 // Creation des vies
		
		lvPane = new JPanel(new FlowLayout());
		lvPane.setBackground(Color.black);
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("ressources" + File.separator + "life.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(20, 20,Image.SCALE_SMOOTH);
		
		for(int i = 0; i < GameController.getLives(); i++) {
			ImageIcon icon = new ImageIcon(dimg);
			JLabel thumb = new JLabel();
			thumb.setPreferredSize(new Dimension(20, 20));
			thumb.setBackground(Color.black);
			thumb.setIcon(icon);
			
			lvPane.add(thumb);
		}
		
		add(lvPane);
		
		// Creation du score
		JPanel collision = new JPanel(new FlowLayout());
		collision.setBackground(Color.black);
		collisionPane = new JLabel();
		collisionPane.setFont(new Font("Joystix", Font.BOLD, 30));
		updateCollision("NONE");
		collision.add(collisionPane);
		add(collision);
	
	}
		
	public void updateCollision(String string) {
		// TODO Auto-generated method stub
		collisionPane.setText("<html><font color = 'YELLOW'>" + string + "</font></html>");
	}
	
	public void updateLevel() {
		levelLabel.setText("<html><font color = 'YELLOW'>" + GameController.getLevel() + "</font></html>");
	}

	public void updateScore() {
		scoreLabel.setText("<html><font color = 'YELLOW'>" + GameController.getScore() + "</font></html>");
	}	
	
	public void load() {
		
	}
	public void update() {
		
	}

	public boolean decrementLife() {
		// TODO Auto-generated method stub
		lvPane.removeAll();
		lvPane.setBackground(Color.black);
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("ressources" + File.separator + "life.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(20, 20,Image.SCALE_SMOOTH);
		
		for(int i = 0; i < GameController.getLives(); i++) {
			ImageIcon icon = new ImageIcon(dimg);
			JLabel thumb = new JLabel();
			thumb.setPreferredSize(new Dimension(20, 20));
			thumb.setBackground(Color.black);
			thumb.setIcon(icon);
			
			lvPane.add(thumb);
		}
		
		lvPane.repaint();
		lvPane.validate();
		
		return GameController.getLives() != 0;
	}
		
	public JLabel getScoreLabel() {
		return scoreLabel;
	}
	
	public JPanel getFruitsPane() {
		return fruitsPane;
	}
	
	public JPanel getLvPanel() {
		return lvPane;
	}
	
	public JLabel getCollisionPanel() {
		return collisionPane;
	}
}
