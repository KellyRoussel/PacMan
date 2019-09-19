package Threads;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Controllers.GameController;
import Models.Maze;
import Models.Characters.Ghost;
import Models.Characters.PacMan;
import Models.Foods.Food;
import Models.Foods.Fruit;
import Models.Foods.Gum;
import Models.Foods.PacGum;
import Views.GamePanel;
import Views.MainGame;
import Views.StatusBar;

public class RenderThread extends Thread{


	private PacMan pacMan;
	private GamePanel gamePanel;
	private Maze maze;
	private ArrayList<Food> foodList;
	private ArrayList<Ghost> ghostList;
	private StatusBar statusBar;
	Threads.TimerThread timerThread;
	
	
	public RenderThread(PacMan pacMan, GamePanel gamePanel, Maze maze, ArrayList<Food> foodList,
			ArrayList<Ghost> ghostList, StatusBar statusBar) {
		super();
		this.pacMan = pacMan;
		this.gamePanel = gamePanel;
		this.maze = maze;
		this.foodList = foodList;
		this.ghostList = ghostList;
		this.statusBar = statusBar;
	}
	//Boucle du jeu
	
	
	
	@Override
	public void run() {
		long pastTime;
		long currentTime;
		long timeloop;
		while(GameController.running) {
			pastTime = System.currentTimeMillis();
			if(!GameController.pause){
				if(pacMan.isDead()) {
					pacMan.deadAnimate();
				}
			}
    		gamePanel.gameRender(pacMan, maze, foodList, ghostList);
			gamePanel.paintScreen();

			try {
				currentTime = System.currentTimeMillis();
				timeloop = currentTime - pastTime;
				if(timeloop <= 0) {
					timeloop = 10;
				}
				Thread.sleep(timeloop);
				statusBar.updateFPS("" + (1000/timeloop));
			}catch(InterruptedException ex) {}
		}
    }

	public void pause() {
		statusBar.updateState("PAUSED");
	}

	public void res(){
		statusBar.updateState("RESUME");
		
		GameController.RESUME = 0;
		while(GameController.RESUME <3) {
			GameController.RESUME += 1;
			gamePanel.gameRender(pacMan, maze, foodList, ghostList);
			gamePanel.paintScreen();
			
			timerThread = new Threads.TimerThread(1);
			timerThread.start();
			timerThread.setName(" RESUME TIMER");
			
			synchronized(timerThread) {
					
				try {
					timerThread.wait(1 * 1000 + 500);
					timerThread.join(GameController.JOIN_TIMER); 
					if(timerThread.isAlive()) {	timerThread.interrupt();}
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
			statusBar.updateState("PLAY");		
	}
	
}
