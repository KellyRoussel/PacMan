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

class GameMenuTest {
	
	
	private GameMenu menuPane;
	private Robot robot;
	private MainGame mainGame;
	
	@BeforeEach
	public void setUp() throws Exception{
		mainGame = MainGame.getInstance();
		mainGame.resize();
		mainGame.displayMenu();
		menuPane = mainGame.getMenuPane();
		mainGame.requestFocus();
		
		assertEquals(mainGame.getContentPane(), menuPane, "Panel should be menu");
		
		robot = new Robot();

	}

	@Test
	void testSelectionDirect() {
		assertEquals(mainGame.getContentPane(), menuPane, "Panel should be menu");
		// Vérification que le GameMenu est affiché fait dans le BeforeEach
		Cursor cursor = menuPane.cursor;
//		System.out.println(MainGame.getInstance().getContentPane());
//		System.out.println(MainGame.getInstance().getMenuPane());
		mainGame.requestFocus();
		assertEquals(menuPane.getStartPosition().y,cursor.getCurrentPosition().y, "Le curseur devrait être positionné sur StartGame");
		//System.out.println("Cursor " + cursor.getCurrentPosition().y + " start " + menuPane.getStartPosition().y);
		// Le curseur est déjà positionné sur StartGame donc on ne passera pas dans la boucle
		while(cursor.getCurrentPosition().y!= menuPane.getStartPosition().y) {
			mainGame.requestFocus();
			robot.keyPress(KeyEvent.VK_DOWN);
			MySleep();
			robot.keyRelease(KeyEvent.VK_DOWN);
		}
		mainGame.requestFocus();
		robot.keyPress(KeyEvent.VK_ENTER);
		MySleep();
		robot.keyRelease(KeyEvent.VK_ENTER);
		MySleep();
		//System.out.println(mainGame.getContentPane().equals(menuPane));
		assertEquals( mainGame.getGameController().getMainPane(), mainGame.getContentPane(),"Le panel affiché n'est pas le bon");
	}
	
	@Test
	void testSelectionDownToFirst() {
		// Vérification que le GameMenu est affiché fait dans le BeforeEach
		Cursor cursor = menuPane.cursor;
		assertEquals( menuPane.getStartPosition().y,cursor.getCurrentPosition().y, "Le curseur devrait être positionné sur StartGame");
		cursor.nextPosition(); // On décale le curseur de 1 en bas
		while(cursor.getCurrentPosition().y!= menuPane.getStartPosition().y) { // On descend tant qu'on n'est pas remonté à StartGame
			//mainGame.resize();
			mainGame.requestFocus();
			robot.keyPress(KeyEvent.VK_DOWN);
			MySleep();
			mainGame.requestFocus();
			robot.keyRelease(KeyEvent.VK_DOWN);
			System.out.println("Cursor " + cursor.getCurrentPosition().y + " start pos: " + menuPane.getStartPosition().y);
		}
//		mainGame.resize();
		mainGame.requestFocus();
		robot.keyPress(KeyEvent.VK_ENTER);
		MySleep();
		robot.keyRelease(KeyEvent.VK_ENTER);
		MySleep();
		assertEquals( mainGame.getGameController().getMainPane(),mainGame.getContentPane());
	}
	
	@Test
	void testSelectionUpToFirst() {
		// Vérification que le GameMenu est affiché fait dans le BeforeEach
		Cursor cursor = menuPane.cursor;
		assertEquals( menuPane.getStartPosition().y,cursor.getCurrentPosition().y, "Le curseur devrait être positionné sur StartGame");
		// On décale le curseur de 2 en bas
		cursor.nextPosition(); 
		cursor.nextPosition(); 
		while(cursor.getCurrentPosition().y!= menuPane.getStartPosition().y) { // On remonte tant qu'on n'est pas remonté à StartGame
			//mainGame.resize();
			mainGame.requestFocus();
			robot.keyPress(KeyEvent.VK_UP);
			MySleep();
			mainGame.requestFocus();
			robot.keyRelease(KeyEvent.VK_UP);
		}
//		mainGame.resize();
		mainGame.requestFocus();
		robot.keyPress(KeyEvent.VK_ENTER);
		MySleep();
		robot.keyRelease(KeyEvent.VK_ENTER);
		MySleep();
		assertEquals( mainGame.getGameController().getMainPane(), mainGame.getContentPane());
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
		mainGame.getGameController().closeWindow();
	}

}
