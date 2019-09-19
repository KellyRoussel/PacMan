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

public class GameMenu extends JPanel{

	private static GameController gameController;
	public static JLabel startGame;
	private JLabel audio;
	private JLabel exit;
	private JLabel help;
	public static JLabel lCursor;
	public static Cursor cursor;

	private Point startPosition, audioPosition, helpPosition, exitPosition;

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
		Image Iimage = image.getScaledInstance(580, 150,Image.SCALE_SMOOTH);
		lImage.setIcon(new ImageIcon(Iimage));
		add(lImage);
		lImage.setLocation(0 , 0);
		lImage.setSize(600,400);


		startGame = new JLabel("<html><font color='WHITE'> START GAME </font></html>");
		startGame.setFont(defaultFont);
		add(startGame);
		startGame.setLocation(245 , 450);
		startGame.setSize(300, 50);
		startPosition = new Point(180, 450);


		audio = new JLabel("<html><font color='WHITE'> AUDIO </font></html>");
		audio.setFont(defaultFont);
		add(audio);
		audio.setLocation(245 , 520);
		audio.setSize(150, 50);
		audioPosition = new Point(180, 520);

		exit = new JLabel("<html><font color='WHITE'> EXIT </font></html>");
		exit.setFont(defaultFont);
		exit.setLocation(245 , 660);
		exit.setSize(150, 50);
		add(exit);
		exitPosition = new Point(180, 660);


		help = new JLabel("<html><font color='WHITE'> HELP </font></html>");
		help.setFont(defaultFont);
		add(help);
		help.setLocation(245 , 590);
		help.setSize(150,50);
		helpPosition = new Point(180, 590);


		cursor = new Cursor(startPosition, 4, gameController);
		cursor.addPossiblePosition(startPosition);
		cursor.addPossiblePosition(audioPosition);
		cursor.addPossiblePosition(helpPosition);
		cursor.addPossiblePosition(exitPosition);

		lCursor = new JLabel();

		lCursor.setIcon(cursor.iiCursor);
		add(lCursor);

		lCursor.setSize(50,50);
		lCursor.setLocation(cursor.getCurrentPosition().x, cursor.getCurrentPosition().y);

	}


	public void gameRunning() {
		startGame.setText("<html><font color='WHITE'> RESUME </font></html>");
	}

	public void noMoreRunning() {
		startGame.setText("<html><font color='WHITE'> START GAME </font></html>");
	}


	public static void moveInMenu(int key) {
		switch(key) {
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
			switch(n) {
			case 0: //StartGame
				gameController.startGame();
				break;
			case 1: //Audio
				gameController.changeVolume();
				break;
			case 2: //Help
				gameController.getFrame().setContentPane(gameController.getFrame().getHelpPane());
				gameController.getFrame().requestFocus();
				gameController.getFrame().revalidate();
				break;
			case 3: //Exit
				if(gameController.getGameThread() != null && gameController.getGameThread().isAlive()) {
					gameController.stop();
				}
//				if(gameController.tRender != null && gameController.tRender.isAlive()) {
//					gameController.tRender.stop();
//				}
//				if(gameController.tAudio != null && gameController.tAudio.isAlive()) {
//					gameController.tAudio.stop();
//				}
				if(gameController.gettPhysics() != null && gameController.gettPhysics().isAlive()) {
					gameController.gettPhysics().stopThread();
				}
				
				if(gameController.gettRender() != null && gameController.gettRender().isAlive()) {
					gameController.gettRender().stopThread();
				}
				
				gameController.getFrame().dispose();
				break;
			default:
				break;
			}
			break;
			default:
				break;
		}
	}
		}
