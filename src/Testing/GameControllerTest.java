package Testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

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
	
	@BeforeEach
	public void setUp() throws Exception {
		
		gameController = new GameController(gamePanel, MainGame.SINGLE_INSTANCE);
		assertNotNull(gameController, "GameController init failed");
		assertNotNull(gameController.getMainPane());
		assertNotNull(gameController.getBackground());
		assertNotNull(gameController.getBeginning());
		assertNotNull(gameController.getDeath());
		
		assertFalse(gameController.isFullScreen(), "FullScreen should be false");
		assertFalse(gameController.isResize(), "Resize should be false");
		assertFalse(gameController.isGameOver(), "GameOver should be false");
		//assertNotNull(gameController.statusBar, " StatusBar init failed"); 
		assertEquals(0, gameController.getScore(), "Score should be 0");
		assertEquals(3, gameController.getLives(), "Lives should be 3");
		assertEquals(1, gameController.getLevel(), "Level should be 1");
	
		int nRow = gameController.getnRow();
		int nColumn = gameController.getnColumn();
		
		assertNotEquals(0, nColumn, "nColumn should not be 0");
		assertNotEquals(0, nRow , "nRow should not be 0");
		assertNotEquals(0, gameController.getSize(), "size should not be 0");
		assertNotEquals(0, gameController.getDefaultSize(), "nColumn should not be 0");
		
		assertNotNull(gameController.getGrille(), "Grille init failed");
		assertNotNull(gameController.images, "Images init failed");
		assertNotNull(gameController.output, "Output init failed");
		assertNotNull(gameController.g, "g init failed");
		for(int i = 0; i < nRow; i++) {
			for(int j = 0; j < nColumn; j++) {
				assertNotNull(gameController.images[i][j], "Image missing");
			}
			
			}
		assertNotNull(gameController.listTunnelLeft, "listTunnelLeft init failed");
		assertNotNull(gameController.listTunnelRight, "listTunnelRight init failed");
		assertNotEquals(0,gameController.listTunnelLeft.size(), "listTunnelLeft empty");
		assertNotEquals(0,gameController.listTunnelRight.size(), "listTunnelRight empty");
		
		assertNotNull(gameController.getMaze(), "Maze init failed");
		assertNotNull(gameController.getPacMan(), "PacMan init failed");
		assertNotNull(gameController.getFoodList(), "FoodList init failed");
		
		ArrayList<Ghost> ghostList = gameController.getGhostList();
		assertNotNull(ghostList, "FoodList init failed");
		assertEquals(4, ghostList.size(), "There should be 4 ghosts");
		
		assertTrue(gameController.getFirstGhostToQuit() < 4, "FirstGhostToQuit should be less than 4");
	}
	

}
