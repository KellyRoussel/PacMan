package Views;

import java.awt.Container;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Controllers.GameController;

public class Cursor implements KeyListener{

	private ArrayList<Point> possiblePositions;
	private Point currentPosition;
	private int numPosition;
	public ImageIcon iiCursor;
	private GameController gameController;

	public Cursor(Point initialPosition, GameController gameController) {
		currentPosition = new Point(initialPosition.x, initialPosition.y);
		possiblePositions = new ArrayList<Point>();
		numPosition = 0;
		this.gameController = gameController;

		BufferedImage imageCursor = null;
		try {
			imageCursor = ImageIO.read(new File("ressources" + File.separator + "life.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Image iCursor = imageCursor.getScaledInstance(30, 30,Image.SCALE_SMOOTH);
		iiCursor = new ImageIcon(iCursor);
	}

	public void addPossiblePosition(Point position) {
		possiblePositions.add(position);
	}

	public void previousPosition() {
		int nButtons = possiblePositions.size();
		if(getNumPosition() != 0) {
			numPosition = (getNumPosition() - 1) % nButtons;
			setCurrentPosition(getNumPosition());
		}
	}

	public void nextPosition() {
		int nButtons = possiblePositions.size();
		numPosition = (getNumPosition() + 1) % nButtons;
		setCurrentPosition(getNumPosition());
	}

	/**
	 * @return the currentPosition
	 */
	public Point getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * @param currentPosition the currentPosition to set
	 */
	public void setCurrentPosition(int i) {
		this.currentPosition = possiblePositions.get(i);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	//	System.out.println("Cursor : key pressed");
		int key = e.getKeyCode();
	//	System.out.println(MainGame.getInstance().getContentPane());
		Container c = MainGame.getInstance().getContentPane();
//		System.out.println(c);
//		System.out.println(MainGame.getInstance().getMenuPane());
		if(c == MainGame.getInstance().getMenuPane()) {
			System.out.println("move in menu MenuPane");
			GameMenu.moveInMenu(key);
		}
		else if(c == MainGame.getInstance().getAudioPane()) {
			AudioPanel.moveInMenu(key);
		}
		else if(c == MainGame.getInstance().getHelpPane()) {
			HelpPanel.moveInMenu(key);
		}
		else if(c == MainGame.getInstance().getScorePane()) {
			ScorePanel.moveInMenu(key);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the numPosition
	 */
	public int getNumPosition() {
		return numPosition;
	}

}
