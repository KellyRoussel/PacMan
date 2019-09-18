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

public class HelpPanel extends JPanel implements ActionListener {
	private GameController gameController;

	private JLabel menu;
	private JLabel muteMusic;
	private JLabel muteSound;
	private JLabel pause;
	private JLabel resume;
	private JLabel movements;
	private JButton back;

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

		back = new JButton("BACK");
		back.setFont(new Font("Joystix", Font.BOLD, 15));
		back.setAlignmentX(CENTER_ALIGNMENT);
		back.setAlignmentY(CENTER_ALIGNMENT);

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
		back.setBounds(450, 650, 100, 50);
		back.setBackground(Color.white);
		back.addActionListener(this);
		back.setActionCommand("Back");
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		switch (cmd) {
		case "Back":
			gameController.frame.displayMenu();
			break;
		default:
			break;
		}

	}
}
