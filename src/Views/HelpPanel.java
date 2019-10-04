package Views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controllers.GameController;

public class HelpPanel extends JPanel {
	private static GameController gameController;

	private JLabel menu;
	private JLabel muteMusic;
	private JLabel muteSound;
	private JLabel pause;
	private JLabel resume;
	private JLabel movements;
	private JLabel back;
	private Point backPosition;
	
	public static Cursor cursor;
	public static JLabel lCursor;
	

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

		muteMusic = new JLabel();
		muteMusic.setFont(defaultFont);
		muteMusic.setText("<html><font color = 'WHITE'>M: Mute Music</font></html>");
		muteMusic.setAlignmentX(CENTER_ALIGNMENT);
		muteMusic.setAlignmentY(CENTER_ALIGNMENT);

		muteSound = new JLabel();
		muteSound.setFont(defaultFont);
		muteSound.setText("<html><font color = 'WHITE'>S: Mute Sound</font></html>");
		muteSound.setAlignmentX(CENTER_ALIGNMENT);
		muteSound.setAlignmentY(CENTER_ALIGNMENT);

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

		back = new JLabel("<html><font color='WHITE'> BACK </font></html>");
		back.setFont(new Font("Joystix", Font.BOLD, 15));

		add(menu);
		menu.setLocation(100, 0);
		menu.setSize(400, 200);

		add(muteMusic);
		muteMusic.setLocation(100, 100);
		muteMusic.setSize(400, 200);

		add(muteSound);
		muteSound.setLocation(100, 200);
		muteSound.setSize(400, 200);

		add(pause);
		pause.setLocation(100, 300);
		pause.setSize(400, 200);

		add(resume);
		resume.setLocation(100, 400);
		resume.setSize(400, 200);

		add(movements);

		movements.setLocation(100, 500);
		movements.setSize(400, 200);

		add(back);
		back.setLocation(450, 650);
		back.setSize(100, 40);
		backPosition = new Point(400, 650);
		
		cursor = new Cursor(backPosition, gameController);
		cursor.addPossiblePosition(backPosition);
		
		lCursor = new JLabel();   
		lCursor.setIcon(cursor.iiCursor);
		add(lCursor);

		lCursor.setSize(50,50);
	    lCursor.setLocation(cursor.getCurrentPosition().x, cursor.getCurrentPosition().y);
	}

	public static void moveInMenu(int key) {
	if(key == KeyEvent.VK_ENTER) {
		gameController.getFrame().displayMenu();
	}
	}
}
