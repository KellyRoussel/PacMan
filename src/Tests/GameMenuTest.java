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
		mainGame.resize();
		menuPane = mainGame.getMenuPane();
		mainGame.requestFocus();
		
		assertEquals(mainGame.getContentPane(), menuPane, "Panel should be menu");
		
		robot = new Robot();

	}

	@Test
	void testSelectionDirect() {
		// Vérification que le GameMenu est affiché fait dans le BeforeEach
		Cursor cursor = menuPane.cursor;
		assertEquals(cursor.getCurrentPosition().y, menuPane.getStartPosition().y, "Le curseur devrait être positionné sur StartGame");
		// Le curseur est déjà positionné sur StartGame donc on ne passera pas dans la boucle
		while(cursor.getCurrentPosition().y!= menuPane.getStartPosition().y) {
			mainGame.resize();
			robot.keyPress(KeyEvent.VK_DOWN);
			MySleep();
			robot.keyRelease(KeyEvent.VK_DOWN);
		}
		mainGame.resize();
		mainGame.requestFocus();
		robot.keyPress(KeyEvent.VK_ENTER);
		MySleep();
		robot.keyRelease(KeyEvent.VK_ENTER);
		MySleep();
		assertEquals(mainGame.getContentPane(), mainGame.getGameController().getMainPane());
	}
	
	@Test
	void testSelectionDownToFirst() {
		// Vérification que le GameMenu est affiché fait dans le BeforeEach
		Cursor cursor = menuPane.cursor;
		assertEquals(cursor.getCurrentPosition().y, menuPane.getStartPosition().y, "Le curseur devrait être positionné sur StartGame");
		cursor.nextPosition(); // On décale le curseur de 1 en bas
		while(cursor.getCurrentPosition().y!= menuPane.getStartPosition().y) { // On descend tant qu'on n'est pas remonté à StartGame
			//mainGame.resize();
			robot.keyPress(KeyEvent.VK_DOWN);
			MySleep();
			robot.keyRelease(KeyEvent.VK_DOWN);
		}
//		mainGame.resize();
//		mainGame.requestFocus();
		robot.keyPress(KeyEvent.VK_ENTER);
		MySleep();
		robot.keyRelease(KeyEvent.VK_ENTER);
		MySleep();
		assertEquals(mainGame.getContentPane(), mainGame.getGameController().getMainPane());
	}
	
	@Test
	void testSelectionUpToFirst() {
		// Vérification que le GameMenu est affiché fait dans le BeforeEach
		Cursor cursor = menuPane.cursor;
		assertEquals(cursor.getCurrentPosition().y, menuPane.getStartPosition().y, "Le curseur devrait être positionné sur StartGame");
		cursor.nextPosition(); // On décale le curseur de 1 en bas
		while(cursor.getCurrentPosition().y!= menuPane.getStartPosition().y) { // On remonte tant qu'on n'est pas remonté à StartGame
			//mainGame.resize();
			robot.keyPress(KeyEvent.VK_UP);
			MySleep();
			robot.keyRelease(KeyEvent.VK_UP);
		}
//		mainGame.resize();
//		mainGame.requestFocus();
		robot.keyPress(KeyEvent.VK_ENTER);
		MySleep();
		robot.keyRelease(KeyEvent.VK_ENTER);
		MySleep();
		assertEquals(mainGame.getContentPane(), mainGame.getGameController().getMainPane());
	}
	
	
	
	public void MySleep() { 
		try {
			Thread.sleep(100);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

}
