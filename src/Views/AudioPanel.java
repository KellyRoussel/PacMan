package Views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import Controllers.GameController;
import Threads.AudioThread;

public class AudioPanel extends JPanel {

	private static GameController gameController;

	private JLabel musicLabel;
	private JLabel muteMusicLabel;
	public static JToggleButton muteMusicButton;
	private JLabel soundLabel;
	public static JToggleButton muteSoundButton;
	private JLabel muteSoundLabel;
	private static JLabel musicValue;
	private static JLabel soundValue;

	private JLabel musicPlus;
	private JLabel musicMinus;
	private JLabel soundPlus;
	private JLabel soundMinus;
	private JLabel back;

	private Point musicPosition;
	private Point musicMutePosition;
	private Point soundPosition;
	private Point soundMutePosition;
	private Point backPosition;

	public static Cursor cursor;
	public static JLabel lCursor;

	private static int musicVal;
	private static int soundVal;

	private Font defaultFont = new Font("Joystix", Font.BOLD, 20);

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

		muteMusicLabel = new JLabel();
		muteMusicLabel.setFont(defaultFont);
		muteMusicLabel.setText("<html><font color = 'WHITE'> MUTE MUSIC </font></html>");

		soundLabel = new JLabel();
		soundLabel.setFont(defaultFont);
		soundLabel.setText("<html><font color = 'WHITE'> SOUND </font></html>");

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

		musicPlus = new JLabel("<html><font color='WHITE'> + </font></html>");
		musicPlus.setFont(defaultFont);

		muteMusicButton = new JToggleButton("M");
		muteMusicButton.setFont(defaultFont);
		muteMusicButton.setAlignmentX(CENTER_ALIGNMENT);
		muteMusicButton.setAlignmentY(CENTER_ALIGNMENT);
		muteMusicButton.setBackground(Color.white);

		musicMinus = new JLabel("<html><font color='WHITE'> - </font></html>");
		musicMinus.setFont(defaultFont);

		muteSoundButton = new JToggleButton("S");
		muteSoundButton.setFont(defaultFont);
		muteSoundButton.setAlignmentX(CENTER_ALIGNMENT);
		muteSoundButton.setAlignmentY(CENTER_ALIGNMENT);
		muteSoundButton.setBackground(Color.white);

		soundPlus = new JLabel("<html><font color='WHITE'> + </font></html>");
		soundPlus.setFont(defaultFont);

		soundMinus = new JLabel("<html><font color='WHITE'> - </font></html>");
		soundMinus.setFont(defaultFont);

		back = new JLabel("<html><font color='WHITE'> BACK </font></html>");
		back.setFont(new Font("Joystix", Font.BOLD, 15));

		add(musicLabel);
		musicLabel.setLocation(70, 100);
		musicLabel.setSize(100, 30);
		musicPosition = new Point(30, 90);

		add(muteMusicLabel);
		muteMusicLabel.setLocation(70, 245);
		muteMusicLabel.setSize(200, 100);
		musicMutePosition = new Point(30, 240);

		add(muteMusicButton);
		muteMusicButton.setBounds(350, 240, 60, 50);
		muteMusicButton.setBackground(Color.white);

		add(musicPlus);
		musicPlus.setLocation(450, 90);
		musicPlus.setSize(60, 50);

		add(musicValue);
		musicValue.setLocation(375, 100);
		musicValue.setSize(100, 30);

		add(musicMinus);
		musicMinus.setLocation(315, 90);
		musicMinus.setSize(60, 50);
		musicMinus.setBackground(Color.white);

		add(soundLabel);
		soundLabel.setLocation(70, 400);
		soundLabel.setSize(150, 30);
		soundPosition = new Point(30, 390);

		add(muteSoundLabel);
		muteSoundLabel.setLocation(70, 560);
		muteSoundLabel.setSize(300, 30);
		soundMutePosition = new Point(30, 550);

		add(muteSoundButton);
		muteSoundButton.setBounds(350, 550, 60, 50);
		muteSoundButton.setBackground(Color.white);

		add(soundPlus);
		soundPlus.setLocation(450, 385);
		soundPlus.setSize(60, 50);

		add(soundMinus);
		soundMinus.setLocation(300, 385);
		soundMinus.setSize(60, 50);

		add(soundValue);
		soundValue.setLocation(370, 400);
		soundValue.setSize(150, 30);

		add(back);
		back.setLocation(450, 650);
		back.setSize(100, 40);
		backPosition = new Point(400, 650);

		cursor = new Cursor(musicPosition, 5, gameController);
		cursor.addPossiblePosition(musicPosition);
		cursor.addPossiblePosition(musicMutePosition);
		cursor.addPossiblePosition(soundPosition);
		cursor.addPossiblePosition(soundMutePosition);
		cursor.addPossiblePosition(backPosition);

		lCursor = new JLabel();
		lCursor.setIcon(cursor.iiCursor);
		add(lCursor);

		lCursor.setSize(50, 50);
		lCursor.setLocation(cursor.getCurrentPosition().x, cursor.getCurrentPosition().y);

	}

	public static void moveInMenu(int key) {
		switch (key) {
		case KeyEvent.VK_DOWN:
			cursor.nextPosition();
			lCursor.setLocation(cursor.getCurrentPosition().x, cursor.getCurrentPosition().y);
			break;
		case KeyEvent.VK_UP:
			cursor.previousPosition();
			lCursor.setLocation(cursor.getCurrentPosition().x, cursor.getCurrentPosition().y);
			break;
		case KeyEvent.VK_ENTER:
			int n = cursor.getNumPosition();
			switch (n) {
			case 1: // MusicMute
				if (muteMusicButton.isSelected()) {
					muteMusicButton.setSelected(false);
				} else {
					muteMusicButton.setSelected(true);
				}
				AudioThread.setMuteOnOffMusic(true);
				break;
			case 3: // SoundMute
				if (muteSoundButton.isSelected()) {
					muteSoundButton.setSelected(false);

				} else {
					muteSoundButton.setSelected(true);
				}
				AudioThread.setMuteOnOffSound(true);
				break;
			case 4: // Back
				gameController.getFrame().displayMenu();
				break;
			default:
				break;
			}
			break;
		case KeyEvent.VK_LEFT:
			int i = cursor.getNumPosition();
			switch (i) {
			case 0: // MusicVolume
				if (musicVal > 0) {
					musicVal -= 5;
					AudioThread.setMusicVolume((float) (musicVal / 100.0));
					musicValue.setText("<html><font color = 'WHITE'>" + musicVal + "</font></html>");
				}
				break;
			case 2: // SoundVolume
				if (soundVal > 0) {
					soundVal -= 5;
					AudioThread.setSoundVolume((float) (soundVal / 100.0));
					soundValue.setText("<html><font color = 'WHITE'>" + soundVal + "</font></html>");
				}
				break;
			default:
				break;
			}
			break;

		case KeyEvent.VK_RIGHT:
			int k = cursor.getNumPosition();
			switch (k) {
			case 0: // MusicVolume
				if (musicVal < 100) {
					musicVal += 5;
					AudioThread.setMusicVolume((float) (musicVal / 100.0));
					musicValue.setText("<html><font color = 'WHITE'>" + musicVal + "</font></html>");
				}
				break;
			case 2: // SoundVolume
				if (soundVal < 100) {
					soundVal += 5;
					AudioThread.setSoundVolume((float) (soundVal / 100.0));
					soundValue.setText("<html><font color = 'WHITE'>" + soundVal + "</font></html>");
				}
				break;
			default:
				break;
			}
			break;
		}
	}

}
