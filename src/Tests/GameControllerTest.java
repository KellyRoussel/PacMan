package Tests;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Image;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controllers.GameController;
import Models.Characters.Ghost;
import Views.GamePanel;
import Views.MainGame;
import Views.StatusBar;

class GameControllerTest {

	private GameController gameController;

	private GamePanel gamePanel;
	private int nRow;
	private int nColumn;
	private int [][] grille;
	private MainGame mainGame;
	Robot robot;

	@BeforeEach
	public void setUp() throws Exception {
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

		// partie init()

//		assertFalse(gameController.isResume(), "Resume should be false");
//		assertFalse(gameController.isFullScreen(), "FullScreen should be false");
//		assertFalse(gameController.isGameOver(), "GameOver should be false");
//		assertFalse(gameController.isResize(), "Resize should be false");
//		assertFalse(gameController.isRunning(), "Running should be false");	
//
//		assertEquals(0, gameController.getScore(), "Score should be 0");
//		assertEquals(3, gameController.getLives(), "Lives should be 3");
//		assertEquals(1, gameController.getLevel(), "Level should be 1");
//
//		nRow = gameController.getnRow();
//		nColumn = gameController.getnColumn();
//
//		assertTrue(nColumn > 0, "nColumn should be greater than 0");
//		assertTrue(nRow > 0 , "nRow should be greater than 0");
//		assertNotEquals(0, gameController.getSize(), "size should not be 0");
//		assertNotEquals(0, gameController.getDefaultSize(), "nColumn should not be 0");
//
//		grille = gameController.getGrille();
//		Image [][] images = gameController.getImages();
//		assertNotNull(grille , "Grille init failed");
//		assertNotNull(images, "Images init failed");
//		assertNotNull(gameController.getOutput(), "Output init failed");
//		assertNotNull(gameController.getG(), "g init failed");
//		for(int i = 0; i < nRow; i++) {
//			for(int j = 0; j < nColumn; j++) {
//				if(grille[i][j]<30) {
//					assertNotNull(images[i][j], "Image missing");
//				}
//			}
//		}
//
//		assertNotNull(gameController.getListTunnelLeft(), "listTunnelLeft init failed");
//		assertNotNull(gameController.getListTunnelRight(), "listTunnelRight init failed");
//		assertNotEquals(0,gameController.getListTunnelLeft().size(), "listTunnelLeft empty");
//		assertNotEquals(0,gameController.getListTunnelRight().size(), "listTunnelRight empty");
//
//		assertNotNull(gameController.getMaze(), "Maze init failed");
//		assertNotNull(gameController.getPacMan(), "PacMan init failed");
//		assertNotNull(gameController.getFoodList(), "FoodList init failed");
//
//		ArrayList<Ghost> ghostList = gameController.getGhostList();
//		assertNotNull(ghostList, "FoodList init failed");
//		assertEquals(4, ghostList.size(), "There should be 4 ghosts");
//
//		assertTrue(gameController.getFirstGhostToQuit() < 4, "FirstGhostToQuit should be less than 4");
//		assertTrue(true);
	}

	@Test
	public void testDeplacementPM() {
		
		try {
			Thread.sleep(4000); // On attend la fin du resume initial
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		//Le pacMan est à sa position initiale
		gameController.getPacMan().returnInitialPosition();
		assertEquals(gameController.getPacMan().getPosition().x, gameController.getPacMan().getInitialPosition().x);
		assertEquals(gameController.getPacMan().getPosition().y, gameController.getPacMan().getInitialPosition().y);
		Point positionBeforeMove = new Point(gameController.getPacMan().getPosition().x , gameController.getPacMan().getPosition().y);

		//On va le faire bouger à droite

		//On renvoie les fantomes dans leur boite pour ne pas se faire manger
		for (int i = 0; i < gameController.getGhostList().size(); i++) {
			gameController.getGhostList().get(i).returnInitialPosition();
		}
		mainGame.getGameController().getMainPane().requestFocus();
		robot.keyPress(KeyEvent.VK_RIGHT);
		MySleep();
		robot.keyRelease(KeyEvent.VK_RIGHT);
		assertTrue(positionBeforeMove.x < gameController.getPacMan().getPosition().x, "PacMan non à droite");

		//A gauche
		//On renvoie les fantomes dans leur boite pour ne pas se faire manger
		for (int i = 0; i < gameController.getGhostList().size(); i++) {
			gameController.getGhostList().get(i).returnInitialPosition();
		}
		mainGame.resize();
		mainGame.getGameController().getMainPane().requestFocus();
		positionBeforeMove.x = gameController.getPacMan().getPosition().x;
		robot.keyPress(KeyEvent.VK_LEFT);
		MySleep();
		robot.keyRelease(KeyEvent.VK_LEFT);
		assertTrue(positionBeforeMove.x > gameController.getPacMan().getPosition().x, "PacMan non à gauche");

		//En haut
		gameController.getPacMan().returnInitialPosition();
		positionBeforeMove.x = gameController.getPacMan().getPosition().x;
		mainGame.getGameController().getMainPane().requestFocus();
		robot.keyPress(KeyEvent.VK_UP);
		MySleep();
		robot.keyRelease(KeyEvent.VK_UP);
		assertTrue(positionBeforeMove.y > gameController.getPacMan().getPosition().y, "PacMan non monté");

		//En bas
		positionBeforeMove.y = gameController.getPacMan().getPosition().y;
		mainGame.getGameController().getMainPane().requestFocus();
		robot.keyPress(KeyEvent.VK_DOWN);
		MySleep();
		robot.keyRelease(KeyEvent.VK_DOWN);
		assertTrue(positionBeforeMove.y < gameController.getPacMan().getPosition().y, "PacMan non descendu");
	}

	public void MySleep() { 
		try {
			Thread.sleep(1000);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}



//	@Test
//	public void fillFoodListTest() {
//		for(int i = 0; i < nRow; i++)
//			for(int j = 0; j < nColumn; j++) {
//				if(grille[i][j] == 30 || grille[i][j] == 40 || grille[i][j] == 50) {
//					assertNotNull(grille[i][j], "Grille filling failed");
//				}
//			}
//	}

//	@Test
//	public void startGameTest() {
//		//Si on démarre un nouveau jeu
//		//gameController.startGame();
//
//		assertEquals(gameController.getMainPane(), gameController.getFrame().getContentPane(), "Content Pane should be MainPane");
//		assertTrue(gameController.getGameThread().isAlive(), "GameThread should be running");
//		assertTrue(gameController.gettAudio().isAlive(), "tAudio should be started");
//		assertNotNull(gameController.gettPhysics(), "tPhysics init failed");
//		assertTrue(gameController.gettPhysics().isAlive(), "tPhysdics should be started");
//		assertNotNull(gameController.gettRender(), "tRender init failed");
//		assertTrue(gameController.gettRender().isAlive(), "tRender should be started");
//		assertFalse(gameController.isPause(), "pause should be false");
//		assertTrue(gameController.isResume(), "resume should be true");
//		gameController.stop();
//
//
//		//Si on reprend un jeu 
//		gameController.setGameThread(new Thread(gameController));
//		gameController.getGameThread().start();
//		gameController.startGame();
//		assertEquals(gameController.getMainPane(), gameController.getFrame().getContentPane(), "Content Pane should be MainPane");
//		assertTrue(gameController.getGameThread().isAlive(), "GameThread should be running");
//		assertFalse(gameController.isPause(), "pause should be false");
//		assertTrue(gameController.isResume(), "resume should be true");
//		gameController.stop();
//	}

//	@Test
//	public void StartNewLifeTest() {
//		gameController.startNewLife();
//		assertFalse(gameController.getPacMan().isDead(), "Pacman wasn't set alive");
//		assertEquals(2, gameController.getLives());
//		assertTrue(gameController.isPause());
//		assertTrue(gameController.isResume());
//	}

//	@Test
//	public void GameOverTest() {
//		gameController.setLives(1);
//		gameController.startNewLife();
//		assertTrue(gameController.isGameOver());
//	}

//
//
//	@Test
//	public void pauseTest() {
//		gameController.startGame();
//		gameController.pause();
//		assertTrue(gameController.gettRender().isPause(), "Render Thread wasn't set to pause");
//		assertTrue(gameController.isPause());
//	}
//
//	@Test
//	public void changeVolumeTest() {
//		gameController.changeVolume();
//		assertEquals(gameController.getFrame().getAudioPane(),gameController.getFrame().getContentPane());
//	}
//
//	@Test
//	public void updatePositionsTest() {
//
//		//Si foodList n'est pas vide
//		int size1 = gameController.getFoodList().size();
//		int score1 = gameController.getScore();
//		gameController.updatePositions(2,2,true);
//		int size2 = gameController.getFoodList().size();
//		int score2 = gameController.getScore();
//		assertTrue(size2 == size1 - 1, "Size 1 should be greater than size 2");
//		assertTrue(score2 > score1, "Score 2 should be greater than score 1");
//
//		//Si foodList est vide
//		gameController.getFoodList().clear();
//		gameController.updatePositions(1,1,true);
//
//		assertTrue(gameController.getLevel()> 1, "level should be greater than 1");
//
//		assertEquals(gameController.getPacMan().getPosition(), gameController.getPacMan().getInitialPosition(), "PacMan should be at InitialPosition");
//		for(int i = 0; i < gameController.getGhostList().size(); i++) {
//			assertEquals(gameController.getGhostList().get(i).getPosition(), gameController.getGhostList().get(i).getInitialPosition(), "Ghost should be at InitialPosition");
//		}
//		assertEquals(KeyEvent.VK_LEFT,gameController.getPacMan().getNextDirection(),"NextDirection should be VK_LEFT");
//		assertTrue(gameController.gettAudio().isAlive(), "tAudio should be started");
//		assertTrue(gameController.isPause(), "Game should be paused");
//		assertTrue(gameController.isResume(), "Resume should be true");
//
//	}
//
//	@Test
//	public void testGameUpdate() {
//		//Les fantomes sont tous sortis
//		Point position1;
//		Point position2;
//		gameController.setGhostOutside(4);
//		for (int i = 0; i < gameController.getGhostList().size(); i++) {
//			gameController.getGhostList().get(i).setOutside(true);
//			position1 = gameController.getGhostList().get(i).getPosition();
//			gameController.gameUpdate();
//			position2 = gameController.getGhostList().get(i).getPosition();
//			//assertNotEquals(position1, position2);
//		}

//	}
	
	@AfterEach
	public void tearDown() throws Exception {
		gameController.closeWindow();
	}


}
