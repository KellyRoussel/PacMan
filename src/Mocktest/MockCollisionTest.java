package Mocktest;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Image;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Iterator;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controllers.GameController;
import Models.Characters.Ghost;
import Models.Foods.Food;
import Models.Foods.PacGum;
import Views.Cursor;
import Views.GameMenu;
import Views.GamePanel;
import Views.MainGame;
import Views.StatusBar;

class MockCollisionTest {
	
	private GameController gameController;
	private GamePanel gamePanel;
	private int nRow;
	private int nColumn;
	private int [][] grille;
	private MainGame mainGame;
	Robot robot;

	
	@BeforeEach
	public void setUp() throws Exception{
		robot= new Robot();
		mainGame = MainGame.getInstance();
		mainGame.resize();
		gameController = mainGame.getGameController();
		
		gameController.startGame();
		
		assertNotNull(gameController, "GameController init failed");
		assertNotNull(gameController.getMainPane(), "MainPane init failed");
		assertNotNull(gameController.getFrame(), "Frame init failed");
		assertNotNull(gameController.getGamePanel(), "GamePanel init faild");
		assertNotNull(gameController.gettAudio(), "tAudio init failed");
		assertNotNull(gameController.getStatusBar(), " StatusBar init failed"); 
	}

	@Test
	void testCollisionPacmanGhostNotInvincible() {
		
		Iterator iter = gameController.getGhostList().iterator();
		while (iter.hasNext()) {
			Ghost x = (Ghost) iter.next();
			x.setPosition(gameController.getPacMan().getX() - 80, gameController.getPacMan().getY());
		}
		
		gameController.setLives(1);
		robot.keyPress(KeyEvent.VK_LEFT);
		MySleep();
		robot.keyRelease(KeyEvent.VK_LEFT);
		assertEquals(0, gameController.getLives(), "PacMan should be dead");
		
	}
	
	@Test
	void testCollisionPacmanGhostInvincible() {
		Iterator iter = gameController.getGhostList().iterator();
		while (iter.hasNext()) {
			Ghost xghost = (Ghost) iter.next();
			xghost.setPosition(gameController.getPacMan().getX() - 80, gameController.getPacMan().getY());
		}
		
		gameController.getFoodList().add(new PacGum(gameController.getPacMan().getWidth(), gameController.getPacMan().getHeight(), loadImage("pacGum.png"),
				new Point(gameController.getPacMan().getX() - 30, gameController.getPacMan().getY())));
		
		gameController.setLives(1);
		robot.keyPress(KeyEvent.VK_LEFT);
		MySleep();
		robot.keyRelease(KeyEvent.VK_LEFT);
		assertEquals(1, gameController.getLives(), "PacMan should be alive");
	}
	
	public void MySleep() { 
		try {
			Thread.sleep(5000);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private Image loadImage(String fileName) {
		ImageIcon icon = new ImageIcon("ressources" + File.separator + fileName);
		return icon.getImage();
	}
	
	@AfterEach
	public void tearDown() throws Exception {
		gameController.closeWindow();
	}

}
