package Views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controllers.GameController;
import Threads.AudioThread;

public class AudioPanel extends JPanel implements ActionListener {

	private GameController gameController;

	private JLabel musicLabel;
	private JLabel soundLabel;
	private JLabel musicValue;
	private JLabel soundValue;
	private JButton musicPlus;
	private JButton musicMinus;
	private JButton soundPlus;
	private JButton soundMinus;
	private JButton exit;

	private int musicVal;
	private int soundVal;

	private Font defaultFont = new Font("Joystix", Font.BOLD, 30);

	public AudioPanel(GameController gameController) {

		this.gameController = gameController;

		setLayout(null);
		setBackground(Color.black);
		setPreferredSize(new Dimension(600, 800));
		musicVal = 70;
		soundVal = 50;

		musicLabel = new JLabel();
		musicLabel.setFont(defaultFont);
		musicLabel.setText("<html><font color = 'WHITE'> MUSIC </font></html>");
		musicLabel.setAlignmentX(CENTER_ALIGNMENT);
		musicLabel.setAlignmentY(CENTER_ALIGNMENT);

		soundLabel = new JLabel();
		soundLabel.setFont(defaultFont);
		soundLabel.setText("<html><font color = 'WHITE'> SOUND </font></html>");
		soundLabel.setAlignmentX(CENTER_ALIGNMENT);
		soundLabel.setAlignmentY(CENTER_ALIGNMENT);

		musicValue = new JLabel();
		musicValue.setFont(defaultFont);
		musicValue.setText("<html><font color = 'WHITE'>" + musicVal + "</font></html>");
		musicValue.setAlignmentX(CENTER_ALIGNMENT);
		musicValue.setAlignmentY(CENTER_ALIGNMENT);

		soundValue = new JLabel();
		soundValue.setFont(defaultFont);
		soundValue.setText("<html><font color = 'WHITE'>" + soundVal + "</font></html>");
		soundValue.setAlignmentX(CENTER_ALIGNMENT);
		soundValue.setAlignmentY(CENTER_ALIGNMENT);

		musicPlus = new JButton("+");
		musicPlus.setFont(defaultFont);
		musicPlus.setAlignmentX(CENTER_ALIGNMENT);
		musicPlus.setAlignmentY(CENTER_ALIGNMENT);

		musicMinus = new JButton("-");
		musicMinus.setFont(defaultFont);
		musicMinus.setAlignmentX(CENTER_ALIGNMENT);
		musicMinus.setAlignmentY(CENTER_ALIGNMENT);

		soundPlus = new JButton("+");
		soundPlus.setFont(defaultFont);
		soundPlus.setAlignmentX(CENTER_ALIGNMENT);
		soundPlus.setAlignmentY(CENTER_ALIGNMENT);

		soundMinus = new JButton("-");
		soundMinus.setFont(defaultFont);
		soundMinus.setAlignmentX(CENTER_ALIGNMENT);
		soundMinus.setAlignmentY(CENTER_ALIGNMENT);

		exit = new JButton("EXIT");
		exit.setFont(new Font("Joystix", Font.BOLD, 15));
		exit.setAlignmentX(CENTER_ALIGNMENT);
		exit.setAlignmentY(CENTER_ALIGNMENT);

		add(musicLabel);
		musicLabel.setLocation(70, 260);
		musicLabel.setSize(100, 30);

		add(musicPlus);
		musicPlus.setBounds(450, 250, 60, 50);
		musicPlus.setBackground(Color.white);
		musicPlus.setActionCommand("music+");
		musicPlus.addActionListener(this);

		add(musicValue);
		musicValue.setLocation(375, 255);
		musicValue.setSize(100, 30);

		add(musicMinus);
		musicMinus.setBounds(260, 250, 60, 50);
		musicMinus.setBackground(Color.white);
		musicMinus.setActionCommand("music-");
		musicMinus.addActionListener(this);

		add(soundLabel);
		soundLabel.setLocation(70, 450);
		soundLabel.setSize(100, 30);

		add(soundPlus);
		soundPlus.setBounds(450, 450, 60, 50);
		soundPlus.setBackground(Color.white);
		soundPlus.addActionListener(this);
		soundPlus.setActionCommand("sound+");

		add(soundMinus);
		soundMinus.setBounds(260, 450, 60, 50);
		soundMinus.setBackground(Color.white);
		soundMinus.addActionListener(this);
		soundMinus.setActionCommand("sound-");

		add(soundValue);
		soundValue.setLocation(370, 455);
		soundValue.setSize(100, 30);

		add(exit);
		exit.setBounds(450, 650, 70, 40);
		exit.setBackground(Color.white);
		exit.addActionListener(this);
		exit.setActionCommand("exit");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		switch (cmd) {
		case "music+":
			if (musicVal < 100) {
				musicVal += 5;
				AudioThread.setMusicVolume((float) (musicVal / 100.0));
				musicValue.setText("<html><font color = 'WHITE'>" + musicVal + "</font></html>");
			}
			break;
		case "music-":
			if (musicVal > 0) {
				musicVal -= 5;
				AudioThread.setMusicVolume((float) (musicVal / 100.0));
				musicValue.setText("<html><font color = 'WHITE'>" + musicVal + "</font></html>");
			}
			break;
		case "sound+":
			if (soundVal < 100) {
				soundVal+= 5;
				AudioThread.setSoundVolume((float)(soundVal/100.0));
				soundValue.setText("<html><font color = 'WHITE'>" + soundVal + "</font></html>");
			}
			break;
		case "sound-":
			if (soundVal > 0) {
				soundVal-= 5;
				AudioThread.setSoundVolume((float)(soundVal/100.0));
				soundValue.setText("<html><font color = 'WHITE'>" + soundVal + "</font></html>");
			}
			break;
		case "exit":
			gameController.frame.displayMenu();
			break;
		default:
			break;
		}
	}
}
