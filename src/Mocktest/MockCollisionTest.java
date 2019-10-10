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
import Views.MainGame;

class MockCollisionTest {
	
	private GameMenu menuPanel;
	private Robot robot;
	private MainGame mainGame;
	
	@BeforeEach
	public void setUp() throws Exception{
		mainGame = MainGame.getInstance();
		mainGame.resize();
		mainGame.displayMenu();
		menuPanel = mainGame.getMenuPane();
		mainGame.requestFocus();
		
		assertEquals(mainGame.getContentPane(), menuPanel);
		
		robot = new Robot();
	}

	@Test
	void testSelectionDirection() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(true, true, "True should be true");
	}
	
	@Test
	void testCollisionStandard() {

	}
	
	@Test
	void testCollisionInvincible() {

	}
	
	public void MySleep() { 
		try {
			Thread.sleep(100);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@AfterEach
	public void tearDown() throws Exception{
		
//		assertFalse(mainGame.getGameController().getGameThread().isAlive(), "GameThread Not stopped");
//		assertFalse(mainGame.getGameController().gettAudio().isAlive(), "tAudio Not stopped");
//		assertFalse(mainGame.getGameController().gettRender().isAlive(), "tRender Not stopped");
//		assertFalse(mainGame.getGameController().gettPhysics().isAlive(), "tPhysics Not stopped");
	}

}
