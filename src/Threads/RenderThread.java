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
	private boolean pause;
	private boolean running;
	private long date = 0;
	private TimerThread timerThread;
	
	public RenderThread(PacMan pacMan, GamePanel gamePanel, Maze maze, ArrayList<Food> foodList,
			ArrayList<Ghost> ghostList, StatusBar statusBar) {
		super();
		this.pacMan = pacMan;
		this.gamePanel = gamePanel;
		this.maze = maze;
		this.foodList = foodList;
		this.ghostList = ghostList;
		this.statusBar = statusBar;
		pause = false;
		running = false;
	}
	//Boucle du jeu
	
	public boolean isPause() {
		return pause;
	}



	public void setPause(boolean pause) {
		this.pause = pause;
	}



	public boolean isRunning() {
		return running;
	}



	public void setRunning(boolean running) {
		this.running = running;
	}
	
	
	@Override
	public void run() {
		running = true;
		pause = true;
		int counter = 0;
		while(running) {
			if(pacMan.isDead()) {
				System.out.println("haa");
				pacMan.deadAnimate();
			}
    		gamePanel.gameRender(pacMan, maze, foodList, ghostList);
			gamePanel.paintScreen();

			try {
				
				long currentTime = System.currentTimeMillis();
				int sleeptime = (int)(1000L / GameController.getFPS());
				if(date != 0 && (currentTime - date - sleeptime) != 0)
					GameController.setFPS((int)(1000 / (currentTime - date - sleeptime)));

				date = currentTime;
				
				
				
				
				if(GameController.getFPS() > 60)
					GameController.setFPS(60);
				else if(GameController.getFPS() < 30)
					GameController.setFPS(30);
			
				if(counter == 0)
					statusBar.updateFPS("" + GameController.getFPS());
				counter++;
				counter = counter % 10;
				
				Thread.sleep(1000L / (long)GameController.getFPS());
				
			}catch(InterruptedException ex) {}
		}
    }

	public void pause() {
		pause = true;
		statusBar.updateState("PAUSED");
	}

	public void res(){
		statusBar.updateState("RESUME");
		
		GameController.setRESUME(0);
		while(GameController.getRESUME() <3) {
			GameController.setRESUME(GameController.getRESUME() + 1);
			gamePanel.gameRender(pacMan, maze, foodList, ghostList);
			gamePanel.paintScreen();
			
			timerThread = new Threads.TimerThread(1);
			timerThread.start();
			timerThread.setName(" RESUME TIMER");
			
			synchronized(timerThread) {
					
				try {
					timerThread.wait(1 * 1000 + 500);
					timerThread.join(GameController.getJoinTimer()); 
					if(timerThread.isAlive()) {	timerThread.interrupt();}
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
			statusBar.updateState("PLAY");		
	}
	
	public void stopThread() {

		running = false;
		try {
			this.join(300);
			if (this.isAlive()){

				this.interrupt();
			}
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
}
