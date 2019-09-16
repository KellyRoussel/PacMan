package Views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import Controllers.GameController;

public class GameMenu extends JPanel implements ActionListener{
	
	private GameController gameController;
	private JButton startGame = new JButton("Start Game");
	private JButton audio = new JButton ("Audio");
	private JButton exit = new JButton("Exit");
	private JButton help = new JButton("Help");
	

	public GameMenu(GameController gameController) {
		setBackground(Color.black);
		setPreferredSize(new Dimension(600, 800));
		//setBorder(new EmptyBorder(0, 20, 0, 20));
		setLayout(new GridLayout(4, 1));
		this.gameController = gameController;
		
		add(startGame);
		add(audio);
		add(exit);
		add(help);
		
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
		}
			
	}
	
}
