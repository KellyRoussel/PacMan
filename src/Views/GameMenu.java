package Views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controllers.GameController;

public class GameMenu extends JPanel implements ActionListener{
	
	private GameController gameController;
	private JButton startGame;
	private JButton audio;
	private JButton exit;
	private JButton help;
	
	private Font defaultFont = new Font("Joystix", Font.BOLD, 15);
	

	public GameMenu(GameController gameController) {
		
		this.gameController = gameController;
		
		setBackground(Color.black);
		setPreferredSize(new Dimension(600, 800));

		setLayout(null);
		
	
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("ressources" + File.separator + "menu.png"));
		} catch (IOException e) {
			e.printStackTrace();
			}
		
		
	    JLabel lImage = new JLabel();
	    Image Iimage = image.getScaledInstance(580, 150,Image.SCALE_SMOOTH);
		lImage.setIcon(new ImageIcon(Iimage));
		add(lImage);
		lImage.setLocation(0 , 0);
		lImage.setSize(600,400);
	    lImage.setLocation(0 , 0);
	    
		startGame = new JButton("Start Game");
		startGame.setFont(defaultFont);
		startGame.setAlignmentX(CENTER_ALIGNMENT);
		startGame.setAlignmentY(CENTER_ALIGNMENT);
		add(startGame);
		startGame.setBounds(220 , 450, 150, 50);
		startGame.setBackground(Color.white);
		
		audio = new JButton ("Audio");
		audio.setFont(defaultFont);
		audio.setAlignmentX(CENTER_ALIGNMENT);
		audio.setAlignmentY(CENTER_ALIGNMENT);
		add(audio);
		audio.setBounds(220 , 520, 150, 50);
		audio.setBackground(Color.white);
		
		exit = new JButton("Exit");
		exit.setFont(defaultFont);
		exit.setAlignmentX(CENTER_ALIGNMENT);
		exit.setAlignmentY(CENTER_ALIGNMENT);
		add(exit);
		exit.setBounds(220 , 590, 150, 50);
		exit.setBackground(Color.white);
		
		
		help = new JButton("Help");
		help.setFont(defaultFont);
		help.setAlignmentX(CENTER_ALIGNMENT);
		help.setAlignmentY(CENTER_ALIGNMENT);
		add(help);
		help.setBounds(220 , 660, 150, 50);
		help.setBackground(Color.white);
		
		//Des essais pour styliser les boutons
//		help.setForeground(Color.white);
//		help.setOpaque(false);
//		help.setContentAreaFilled(false);
//		help.setBorderPainted(false);
			
		
		startGame.addActionListener(this);
		audio.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		switch(cmd) {
		case "Start Game":
			gameController.startGame();	
			break;
		case "Audio":
			gameController.changeVolume();
			break;
		default:
			break;
		}
			
	}
	
}
