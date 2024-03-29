package Views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controllers.GameController;

public class GameMenu extends JPanel {

	private static GameController gameController;
	public static JLabel startGame;
	private JLabel audio;
	private JLabel exit;
	private JLabel help;
	private JLabel highestScores;
	public static JLabel lCursor;
	public static Cursor cursor;

	private static Point startPosition;
	private static Point audioPosition;
	private static Point helpPosition;
	private static Point exitPosition;
	private static Point highestScoresPosition;

	private Font defaultFont = new Font("Joystix", Font.BOLD, 30);

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
		Image Iimage = image.getScaledInstance(580, 150, Image.SCALE_SMOOTH);
		lImage.setIcon(new ImageIcon(Iimage));
		add(lImage);
		lImage.setLocation(0, 0);
		lImage.setSize(600, 400);

		startGame = new JLabel("<html><font color='WHITE'> START GAME </font></html>");
		startGame.setFont(defaultFont);
		add(startGame);
		startGame.setLocation(245, 380);
		startGame.setSize(300, 50);
		startPosition = new Point(180, 380);
		
		highestScores = new JLabel("<html><font color='WHITE'> HIGHEST SCORES </font></html>");
		highestScores.setFont(defaultFont);
		add(highestScores);
		highestScores.setLocation(245, 450);
		highestScores.setSize(450, 50);
		highestScoresPosition = new Point(180, 450);

		audio = new JLabel("<html><font color='WHITE'> AUDIO </font></html>");
		audio.setFont(defaultFont);
		add(audio);
		audio.setLocation(245, 520);
		audio.setSize(150, 50);
		audioPosition = new Point(180, 520);

		exit = new JLabel("<html><font color='WHITE'> EXIT </font></html>");
		exit.setFont(defaultFont);
		exit.setLocation(245, 660);
		exit.setSize(150, 50);
		add(exit);
		exitPosition = new Point(180, 660);

		help = new JLabel("<html><font color='WHITE'> HELP </font></html>");
		help.setFont(defaultFont);
		add(help);
		help.setLocation(245, 590);
		help.setSize(150, 50);
		setHelpPosition(new Point(180, 590));

		cursor = new Cursor(getStartPosition(), gameController);
		cursor.addPossiblePosition(getStartPosition());
		cursor.addPossiblePosition(getHighestScoresPosition());
		cursor.addPossiblePosition(audioPosition);
		cursor.addPossiblePosition(getHelpPosition());
		cursor.addPossiblePosition(getExitPosition());
		

		lCursor = new JLabel();

		lCursor.setIcon(cursor.iiCursor);
		add(lCursor);

		lCursor.setSize(50, 50);
		lCursor.setLocation(cursor.getCurrentPosition().x, cursor.getCurrentPosition().y);

	}

	public void gameRunning() {
		startGame.setText("<html><font color='WHITE'> RESUME </font></html>");
	}

	public void noMoreRunning() {
		startGame.setText("<html><font color='WHITE'> START GAME </font></html>");
	}

	public static void moveInMenu(int key) {

		switch (key) {
		case KeyEvent.VK_DOWN:
			System.out.println("pressed down");
			cursor.nextPosition();
			lCursor.setLocation(cursor.getCurrentPosition().x, cursor.getCurrentPosition().y);
			break;
		case KeyEvent.VK_UP:
			cursor.previousPosition();
			lCursor.setLocation(cursor.getCurrentPosition().x, cursor.getCurrentPosition().y);
			break;
		case KeyEvent.VK_ENTER:
			System.out.println("enter pressed");
			Point position = cursor.getCurrentPosition();
			if(position.y == startPosition.y) { // StartGame
				gameController.startGame();
			}
			else if(position.y == highestScoresPosition.y) { // HighestScores
				gameController.getFrame().getScorePane().displayScores();
				gameController.getFrame().setContentPane(gameController.getFrame().getScorePane());
				gameController.getFrame().requestFocus();
				gameController.getFrame().revalidate();
			}
			else if(position.y == audioPosition.y) { //Audio
				gameController.changeVolume();
			}
			else if(position.y == helpPosition.y) {
				gameController.getFrame().setContentPane(gameController.getFrame().getHelpPane());
				gameController.getFrame().requestFocus();
				gameController.getFrame().revalidate();
			}
			else if(position.y == exitPosition.y) {
				gameController.closeWindow();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * @return the startPosition
	 */
	public Point getStartPosition() {
		return startPosition;
	}

	/**
	 * @return the helpPosition
	 */
	public Point getHelpPosition() {
		return helpPosition;
	}

	/**
	 * @param helpPosition the helpPosition to set
	 */
	public void setHelpPosition(Point helpPosition) {
		this.helpPosition = helpPosition;
	}

	/**
	 * @return the exitPosition
	 */
	public Point getExitPosition() {
		return exitPosition;
	}

	/**
	 * @return the highestScoresPosition
	 */
	public Point getHighestScoresPosition() {
		return highestScoresPosition;
	}
}
