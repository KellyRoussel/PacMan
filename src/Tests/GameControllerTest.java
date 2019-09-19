package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
	private MainGame mainGame = new MainGame();
	
	@BeforeEach
	public void setUp() throws Exception {
		
		gameController = new GameController(mainGame);
		assertNotNull(gameController, "GameController init failed");
		assertNotNull(gameController.getMainPane(), "MainPane init failed");
		assertNotNull(gameController.getFrame(), "Frame init failed");
		assertNotNull(gameController.getGamePanel(), "GamePanel init faild");
		assertNotNull(gameController.gettAudio(), "tAudio init failed");
		assertNotNull(gameController.getStatusBar(), " StatusBar init failed"); 

		// partie init()
		assertEquals(0, gameController.getRESUME(), "RESUME should be 0");
		assertFalse(gameController.isResume(), "Resume should be false");
		assertFalse(gameController.isFullScreen(), "FullScreen should be false");
		assertFalse(gameController.isGameOver(), "GameOver should be false");
		assertFalse(gameController.isResize(), "Resize should be false");
		assertFalse(gameController.isRunning(), "Running should be false");	

		assertEquals(0, gameController.getScore(), "Score should be 0");
		assertEquals(3, gameController.getLives(), "Lives should be 3");
		assertEquals(1, gameController.getLevel(), "Level should be 1");
	
		nRow = gameController.getnRow();
		nColumn = gameController.getnColumn();
		
		assertTrue(nColumn > 0, "nColumn should be greater than 0");
		assertTrue(nRow > 0 , "nRow should be greater than 0");
		assertNotEquals(0, gameController.getSize(), "size should not be 0");
		assertNotEquals(0, gameController.getDefaultSize(), "nColumn should not be 0");
		
		grille = gameController.getGrille();
		Image [][] images = gameController.getImages();
		assertNotNull(grille , "Grille init failed");
		assertNotNull(images, "Images init failed");
		assertNotNull(gameController.getOutput(), "Output init failed");
		assertNotNull(gameController.getG(), "g init failed");
		for(int i = 0; i < nRow; i++) {
			for(int j = 0; j < nColumn; j++) {
		if(grille[i][j]<30) {
				assertNotNull(images[i][j], "Image missing");
		}
			}
			}
		
		assertNotNull(gameController.getListTunnelLeft(), "listTunnelLeft init failed");
		assertNotNull(gameController.getListTunnelRight(), "listTunnelRight init failed");
		assertNotEquals(0,gameController.getListTunnelLeft().size(), "listTunnelLeft empty");
		assertNotEquals(0,gameController.getListTunnelRight().size(), "listTunnelRight empty");
		
		assertNotNull(gameController.getMaze(), "Maze init failed");
		assertNotNull(gameController.getPacMan(), "PacMan init failed");
		assertNotNull(gameController.getFoodList(), "FoodList init failed");
		
		ArrayList<Ghost> ghostList = gameController.getGhostList();
		assertNotNull(ghostList, "FoodList init failed");
		assertEquals(4, ghostList.size(), "There should be 4 ghosts");
		
		assertTrue(gameController.getFirstGhostToQuit() < 4, "FirstGhostToQuit should be less than 4");
		assertTrue(true);
	}
	
	
	@Test
	public void fillFoodListTest() {
		for(int i = 0; i < nRow; i++)
    		for(int j = 0; j < nColumn; j++) {
    			if(grille[i][j] == 30 || grille[i][j] == 40 || grille[i][j] == 50) {
    			assertNotNull(grille[i][j], "Grille filling failed");
    			}
    		}
	}
	
	@Test
	public void startGameTest() {
		//Si on démarre un nouveau jeu
		gameController.startGame();
		
		assertEquals(gameController.getMainPane(), gameController.getFrame().getContentPane(), "Content Pane should be MainPane");
		assertTrue(gameController.getGameThread().isAlive(), "GameThread should be running");
		assertTrue(gameController.gettAudio().isAlive(), "tAudio should be started");
		assertNotNull(gameController.gettPhysics(), "tPhysics init failed");
		assertTrue(gameController.gettPhysics().isAlive(), "tPhysdics should be started");
		assertNotNull(gameController.gettRender(), "tRender init failed");
		assertTrue(gameController.gettRender().isAlive(), "tRender should be started");
		assertFalse(gameController.isPause(), "pause should be false");
		assertTrue(gameController.isResume(), "resume should be true");
		gameController.stop();
		
		//Si on reprend un jeu 
		gameController.setGameThread(new Thread(gameController));
		gameController.getGameThread().start();
		gameController.startGame();
		assertEquals(gameController.getMainPane(), gameController.getFrame().getContentPane(), "Content Pane should be MainPane");
		assertTrue(gameController.getGameThread().isAlive(), "GameThread should be running");
		assertFalse(gameController.isPause(), "pause should be false");
		assertTrue(gameController.isResume(), "resume should be true");
		gameController.stop();
	}
	
	@Test
	public void updatePositionsTest() {
		gameController.setGameThread(new Thread(gameController));
		gameController.startGame();
		gameController.getFoodList().clear();
//		gameController.getGameThread().start();
//		gameController.setRunning(true);
//		gameController.setPause(false);
		System.out.println("Food size " + gameController.getFoodList().size());
		assertTrue(gameController.getLevel()> 1, "level should be greater than 1");
		assertEquals(gameController.getPacMan().getPosition(), gameController.getPacMan().getInitialPosition(), "PacMan should be at InitialPosition");
		for(int i = 0; i < gameController.getGhostList().size(); i++) {
			assertEquals(gameController.getGhostList().get(i).getPosition(), gameController.getGhostList().get(i).getInitialPosition(), "Ghost should be at InitialPosition");
		}
		assertEquals(KeyEvent.VK_LEFT,gameController.getPacMan().getNextDirection(),"NextDirection should be VK_LEFT");
		assertTrue(gameController.gettAudio().isAlive(), "tAudio should be started");
		assertTrue(gameController.isPause(), "Game should be paused");
		assertTrue(gameController.isResume(), "Resume should be true");
		
	}
	

}
