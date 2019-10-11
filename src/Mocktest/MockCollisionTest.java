package Mocktest;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controllers.GameController;
import Views.Cursor;
import Views.GameMenu;
import Views.GamePanel;
import Views.MainGame;

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

	/*@Test
	void testCollisionPacmanGhostNotInvincible() {
		gameController.setLives(1);
		robot.keyPress(KeyEvent.VK_LEFT);
		MySleep();
		robot.keyRelease(KeyEvent.VK_LEFT);
		System.out.println(gameController.getPacMan().isDead());
		assertEquals(0, gameController.getLives(), "PacMan should be dead");
		//assertEquals(true, gameController.getPacMan().isDead(), "PacMan should be dead");
	}*/
	
	@Test
	void testCollisionPacmanGhostInvincible() {
		gameController.setMazeFile("mazeTestPacmanAlive.txt");
		gameController.init();
		
		gameController.setLives(1);
		robot.keyPress(KeyEvent.VK_LEFT);
		MySleep();
		robot.keyRelease(KeyEvent.VK_LEFT);
		assertEquals(1, gameController.getLives(), "PacMan should be alive");
		//assertEquals(false, gameController.getPacMan().isDead(), "PacMan should be alive");
	}
	
	public void MySleep() { 
		try {
			Thread.sleep(5000);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@AfterEach
	public void tearDown() throws Exception {
		gameController.closeWindow();
	}

}
