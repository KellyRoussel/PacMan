package Views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Controllers.GameController;

public class ScorePanel extends JPanel {

	private static GameController gameController;
	
	private JLabel score;
	
	private JLabel back;
	private Point backPosition;
	
	public static Cursor cursor;
	public static JLabel lCursor;
	

	private Font defaultFont = new Font("Joystix", Font.BOLD, 30);

	public ScorePanel(GameController gameController) {

		this.gameController = gameController;

		setLayout(null);
		setBackground(Color.black);
		setPreferredSize(new Dimension(600, 800));
		
		
		back = new JLabel("<html><font color='WHITE'> BACK </font></html>");
		back.setFont(new Font("Joystix", Font.BOLD, 15));
		
		
		back.setLocation(450, 650);
		back.setSize(100, 40);
		backPosition = new Point(400, 650);
		
		cursor = new Cursor(backPosition, 1, gameController);
		cursor.addPossiblePosition(backPosition);
		
		lCursor = new JLabel();   
		lCursor.setIcon(cursor.iiCursor);
		

		lCursor.setSize(50,50);
	    lCursor.setLocation(cursor.getCurrentPosition().x, cursor.getCurrentPosition().y);
	
}
	
	public void displayScores() {
		removeAll();

		add(back);
		add(lCursor);
		
		List<String> scorerList = gameController.getHighScore().getScorerList();
		List<Integer> scoreList = gameController.getHighScore().getScoreList();
		
		for(int i = 0; i < scoreList.size(); i++) {
			score = new JLabel("<html><font color='WHITE'>" + scorerList.get(i)+ " - "+ scoreList.get(i) +  "</font></html>");
			score.setFont(defaultFont);
			add(score);
			score.setLocation(200, 200+70*i);
			score.setSize(300, 40);
		}
		
		revalidate();
		repaint();
	}
	
	public static void moveInMenu(int key) {
		if(key == KeyEvent.VK_ENTER) {
			gameController.getFrame().displayMenu();
		}
		}
}
