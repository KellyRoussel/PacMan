package Views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controllers.GameController;

public class HelpPanel  extends JPanel implements ActionListener{
	private GameController gameController;
	
	private JLabel menu;
	private JLabel mute;
	private JLabel pause;
	private JLabel resume;
	private JLabel movements;
	private JButton exit;
	
	
	private Font defaultFont = new Font("Joystix", Font.BOLD, 30);
	
	public HelpPanel(GameController gameController) {
		
		this.gameController = gameController;
		
	
		setLayout(null);
		setBackground(Color.black);
		setPreferredSize(new Dimension(600, 800));
		
		menu = new JLabel();
		menu.setFont(defaultFont);
		menu.setText("<html><font color = 'WHITE'>ESC: Menu</font></html>");
		menu.setAlignmentX(CENTER_ALIGNMENT);
		menu.setAlignmentY(CENTER_ALIGNMENT);
		
		mute = new JLabel();
		mute.setFont(defaultFont);
		mute.setText("<html><font color = 'WHITE'>M: Mute M</font></html>");
		mute.setAlignmentX(CENTER_ALIGNMENT);
		mute.setAlignmentY(CENTER_ALIGNMENT);
		
		pause = new JLabel();
		pause.setFont(defaultFont);
		pause.setText("<html><font color = 'WHITE'> P: Pause </font></html>");
		pause.setAlignmentX(CENTER_ALIGNMENT);
		pause.setAlignmentY(CENTER_ALIGNMENT);
		
		resume = new JLabel();
		resume.setFont(defaultFont);
		resume.setText("<html><font color = 'WHITE'> R: Resume</font></html>");
		resume.setAlignmentX(CENTER_ALIGNMENT);
		resume.setAlignmentY(CENTER_ALIGNMENT);
		
		movements = new JLabel();
		movements.setFont(defaultFont);
		movements.setText("<html><font color = 'WHITE'> Arrow keys for Pacman</font></html>");
		movements.setAlignmentX(CENTER_ALIGNMENT);
		movements.setAlignmentY(CENTER_ALIGNMENT);
		
		exit = new JButton("EXIT");
		exit.setFont(new Font("Joystix", Font.BOLD, 15));
		exit.setAlignmentX(CENTER_ALIGNMENT);
		exit.setAlignmentY(CENTER_ALIGNMENT);
		
		add(menu);
		menu.setLocation(100,70);
		menu.setSize(200,200);
		
		add(mute);
		mute.setLocation(100,170);
		mute.setSize(200,200);
		
		add(pause);
		pause.setLocation(100,270);
		pause.setSize(200,200);
		
		add(resume);
		resume.setLocation(100,370);
		resume.setSize(200,200);
		
		add(movements);
		movements.setLocation(100, 470);
		movements.setSize(400,200);
		
		add(exit);
		exit.setBounds(450 , 650, 70,  40 );
		exit.setBackground(Color.white);
		exit.addActionListener(this);
		exit.setActionCommand("exit");
	}
		
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			switch(cmd) {
			case "exit":
				gameController.frame.displayMenu();
				break;
			default:
				break;
			}
		
		
	}
}
