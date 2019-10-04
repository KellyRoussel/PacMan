package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controllers.GameController;
import Views.Cursor;
import Views.GameMenu;
import Views.MainGame;

class GameMenuTest {
	
	private MainGame mainGame;
	private GameMenu menuPane;
	private Robot robot;
	
	@BeforeEach
	public void setUp() throws Exception{
		mainGame = new MainGame();
		menuPane = mainGame.getMenuPane();
		assertEquals(mainGame.getContentPane(), menuPane, "Panel should be menu");
		
		robot = new Robot();

	}

	@Test
	void testSelectionStartGame() {
		Cursor cursor = menuPane.cursor;
		// StartGame
		while(cursor.getCurrentPosition().y!= menuPane.getStartPosition().y) {
			robot.keyPress(KeyEvent.VK_DOWN);
			MySleep();
			robot.keyRelease(KeyEvent.VK_DOWN);
		}
		assertEquals(menuPane.getStartPosition().y, menuPane.getStartPosition().y);
	}
	
	public void MySleep() { 
		try {
			Thread.sleep(100);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

}
