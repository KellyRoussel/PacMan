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
import javax.swing.JToggleButton;

import Controllers.GameController;
import Threads.AudioThread;

public class AudioPanel extends JPanel implements ActionListener {

	private GameController gameController;

	private JLabel musicLabel;
	private JLabel muteMusicLabel;
	private JToggleButton muteMusicButton;
	private JLabel soundLabel;
	private JToggleButton muteSoundButton;
	private JLabel muteSoundLabel;
	private JLabel musicValue;
	private JLabel soundValue;
	private JButton musicPlus;
	private JButton musicMinus;
	private JButton soundPlus;
	private JButton soundMinus;
	private JButton back;

	
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
		
		muteMusicLabel = new JLabel();
		muteMusicLabel.setFont(defaultFont);
		muteMusicLabel.setText("<html><font color = 'WHITE'> MUTE MUSIC </font></html>");
		muteMusicLabel.setAlignmentX(CENTER_ALIGNMENT);
		muteMusicLabel.setAlignmentY(CENTER_ALIGNMENT);

		soundLabel = new JLabel();
		soundLabel.setFont(defaultFont);
		soundLabel.setText("<html><font color = 'WHITE'> SOUND </font></html>");
		soundLabel.setAlignmentX(CENTER_ALIGNMENT);
		soundLabel.setAlignmentY(CENTER_ALIGNMENT);

		muteSoundLabel = new JLabel();
		muteSoundLabel.setFont(defaultFont);
		muteSoundLabel.setText("<html><font color = 'WHITE'> MUTE SOUND </font></html>");
		muteSoundLabel.setAlignmentX(CENTER_ALIGNMENT);
		muteSoundLabel.setAlignmentY(CENTER_ALIGNMENT);
		
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
		
		muteMusicButton = new JToggleButton("M");
		muteMusicButton.setFont(defaultFont);
		muteMusicButton.setAlignmentX(CENTER_ALIGNMENT);
		muteMusicButton.setAlignmentY(CENTER_ALIGNMENT);
		muteMusicButton.setBackground(Color.white);

		musicMinus = new JButton("-");
		musicMinus.setFont(defaultFont);
		musicMinus.setAlignmentX(CENTER_ALIGNMENT);
		musicMinus.setAlignmentY(CENTER_ALIGNMENT);
		
		muteSoundButton = new JToggleButton("S");
		muteSoundButton.setFont(defaultFont);
		muteSoundButton.setAlignmentX(CENTER_ALIGNMENT);
		muteSoundButton.setAlignmentY(CENTER_ALIGNMENT);
		muteSoundButton.setBackground(Color.white);

		soundPlus = new JButton("+");
		soundPlus.setFont(defaultFont);
		soundPlus.setAlignmentX(CENTER_ALIGNMENT);
		soundPlus.setAlignmentY(CENTER_ALIGNMENT);

		soundMinus = new JButton("-");
		soundMinus.setFont(defaultFont);
		soundMinus.setAlignmentX(CENTER_ALIGNMENT);
		soundMinus.setAlignmentY(CENTER_ALIGNMENT);

		back = new JButton("BACK");
		back.setFont(new Font("Joystix", Font.BOLD, 15));
		back.setAlignmentX(CENTER_ALIGNMENT);
		back.setAlignmentY(CENTER_ALIGNMENT);

		add(musicLabel);
		musicLabel.setLocation(70, 100);
		musicLabel.setSize(100, 30);
		
		add(muteMusicLabel);
		muteMusicLabel.setLocation(70, 240);
		muteMusicLabel.setSize(200, 30);
		
		add(muteMusicButton);
		muteMusicButton.setBounds(350, 240, 60, 50);
		muteMusicButton.setBackground(Color.white);

		add(musicPlus);
		musicPlus.setBounds(450, 100, 60, 50);
		musicPlus.setBackground(Color.white);
		musicPlus.setActionCommand("music+");
		musicPlus.addActionListener(this);

		add(musicValue);
		musicValue.setLocation(375, 100);
		musicValue.setSize(100, 30);

		add(musicMinus);
		musicMinus.setBounds(260, 100, 60, 50);
		musicMinus.setBackground(Color.white);
		musicMinus.setActionCommand("music-");
		musicMinus.addActionListener(this);

		add(soundLabel);
		soundLabel.setLocation(70, 400);
		soundLabel.setSize(150, 30);
		
		add(muteSoundLabel);
		muteSoundLabel.setLocation(70, 550);
		muteSoundLabel.setSize(300, 30);
		
		add(muteSoundButton);
		muteSoundButton.setBounds(350, 550, 60, 50);
		muteSoundButton.setBackground(Color.white);

		add(soundPlus);
		soundPlus.setBounds(450, 400, 60, 50);
		soundPlus.setBackground(Color.white);
		soundPlus.addActionListener(this);
		soundPlus.setActionCommand("sound+");

		add(soundMinus);
		soundMinus.setBounds(260, 400, 60, 50);
		soundMinus.setBackground(Color.white);
		soundMinus.addActionListener(this);
		soundMinus.setActionCommand("sound-");

		add(soundValue);
		soundValue.setLocation(370, 400);
		soundValue.setSize(150, 30);

		add(back);
		back.setBounds(450, 650, 100, 40);
		back.setBackground(Color.white);
		back.addActionListener(this);
		back.setActionCommand("back");

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
		case "back":
			gameController.frame.displayMenu();
			break;
		default:
			break;
		}
	}
}
