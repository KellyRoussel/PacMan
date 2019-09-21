package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Models.Characters.Ghost;
import Models.Characters.PacMan;
import Models.Foods.Food;
import Threads.AudioThread;
import Threads.PhysicsThread;
import Views.MainGame;
import Views.StatusBar;

class PhysicsThreadTest {
	
	private static int JOIN_TIMER = 500;
	private static PhysicsThread Physics;
	
	private int height = 800;
	private int width = 600;
	private int nRow = 30;
	private int nColumn = 30;
	private int defaultSize = Math.min(height / nRow, width / nColumn);
	
	private int PINK_INITIAL_POSITION = 26;
	private int ORANGE_INITIAL_POSITION = 27;
	private int RED_INITIAL_POSITION = 28;
	private int TURQUOISE_INITIAL_POSITION = 29;
	
	private int [][] grille = new int[nRow][nColumn];
	private ArrayList<Point> listTunnelLeft;
	private ArrayList<Point> listTunnelRight;
	
	private Image[][] images;
	private BufferedImage output;
	private Graphics g;
	
	private PacMan pacman;
	private ArrayList<Ghost> ghostList;
	
	private Image loadImage(String fileName) {
		ImageIcon icon = new ImageIcon("ressources" + File.separator + fileName);
		return icon.getImage();
	}
	
	private Point definePosition(int initialPositionValue) {
		Point p = new Point();
		for(int i = 0; i < nRow; i++)
			for(int j = 0; j < nColumn; j++) {
				if(grille[i][j] == initialPositionValue) {
					p.x = j * defaultSize;
					p.y = i * defaultSize;
				}
			}
		return p;
	}
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception 
	{
		
	}

	@BeforeEach
	void setUp() throws Exception {
		
		//Init PacMan
		int width1 = 0;
		int height1 = 0;
		Point initPosition = new Point();
		
		grille = new int[nRow][nColumn];
		
		for (int i = 0; i < nRow; i++) 
		{
			for (int j = 0; j < nColumn; j++)
			{
				//Init Ghost initialPosition
				if(j ==1) {
					grille[i][j] = 26;
				}
				else if(j ==2){
					grille[i][j] = 27;
				}
				else if(j ==3){
					grille[i][j] = 28;
				}
				else if(j ==4){
					grille[i][j] = 29;
				}
				else {
					grille[i][j] = 30;
				}
			}
		}
	
		
		//Init List Ghost
		ArrayList<Ghost> ghostList = new ArrayList<Ghost>();
		ghostList.add(new Ghost(defaultSize, defaultSize, loadImage("ghostpink.png"), definePosition(PINK_INITIAL_POSITION), "pink", defaultSize, grille, listTunnelLeft, listTunnelRight, nColumn, nRow));    	
		ghostList.add(new Ghost(defaultSize, defaultSize, loadImage("ghostorange.png"), definePosition(ORANGE_INITIAL_POSITION), "orange", defaultSize, grille, listTunnelLeft, listTunnelRight, nColumn, nRow));    	    	
		ghostList.add(new Ghost(defaultSize, defaultSize, loadImage("ghostred.png"), definePosition(RED_INITIAL_POSITION), "red", defaultSize, grille, listTunnelLeft, listTunnelRight, nColumn, nRow));    	    	
		ghostList.add(new Ghost(defaultSize, defaultSize, loadImage("ghostturquoise.png"), definePosition(TURQUOISE_INITIAL_POSITION), "turquoise", defaultSize, grille, listTunnelLeft, listTunnelRight, nColumn, nRow));
		
		
		ImageIcon ii = new ImageIcon("ressources" + File.separator + "Left_0.png");
		PacMan pacman = new PacMan(width1, height1, ii.getImage(), initPosition);
		
		ArrayList<Food> foodlist = new ArrayList<Food>();
		
		Physics = new PhysicsThread(pacman,ghostList,foodlist);
		assertNotNull(Physics, "Thread init failed");		
		
		pacman.setRectangleX(160);
		pacman.setRectangleY(160);
		
		pacman.setEllipseX(160);
		pacman.setEllipseY(160);
		
		for(Ghost x : ghostList) 
		{		
			x.setRectangleX(170);
			x.setRectangleY(170);
			
			x.setAdvancedLowerRectangleX(170);
			x.setAdvancedLowerRectangleY(170);
			
			x.setAdvancedTopArcX(170);
			x.setAdvancedTopArcY(170);
		}
		
		Physics.start();
		assertTrue(Physics.isAlive(), "Thread should be started");
	}

	@AfterEach
	void tearDown() throws Exception {
		// Stop the Counter thread		
		synchronized(Physics)
		{
			try 
			{		
				Physics.stopThread();
				Physics.join(JOIN_TIMER);
				if (Physics.isAlive())
				{
					Physics.interrupt();
					throw new InterruptedByTimeoutException();
				}	
			} 
			catch (InterruptedException | InterruptedByTimeoutException e) 
			{
				assertEquals(null, e, "Throw exception occurred");
			}
			assertFalse(Physics.isAlive(), "Thread should be stopped");
		}
	}

	@Test
	void TestCollisionAdvanced() {
		assertEquals(true,pacman.isDead());
	}
	

}
