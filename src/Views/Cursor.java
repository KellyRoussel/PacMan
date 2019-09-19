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
	private int nButtons;
	public ImageIcon iiCursor;
	private GameController gameController;

	public Cursor(Point initialPosition, int nButtons, GameController gameController) {
		currentPosition = new Point(initialPosition.x, initialPosition.y);
		possiblePositions = new ArrayList<Point>();
		numPosition = 0;
		this.nButtons = nButtons;
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
		if(getNumPosition() != 0) {
			numPosition = (getNumPosition() - 1) % nButtons;
			setCurrentPosition(getNumPosition());
		}
	}

	public void nextPosition() {
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
		int key = e.getKeyCode();
		Container c = MainGame.getInstance().getContentPane();
		if(c == MainGame.getInstance().getMenuPane()) {
			GameMenu.moveInMenu(key);
		}
		else if(c == MainGame.getInstance().getAudioPane()) {
			AudioPanel.moveInMenu(key);
		}
		else if(c == MainGame.getInstance().getHelpPane()) {
			HelpPanel.moveInMenu(key);
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
