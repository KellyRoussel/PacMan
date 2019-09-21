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
import java.util.concurrent.atomic.AtomicBoolean;

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
	private long date = 0;
	private TimerThread timerThread;

	private static AtomicBoolean running = new AtomicBoolean(false);
	private static AtomicBoolean resume = new AtomicBoolean(false);
	private static AtomicBoolean pause = new AtomicBoolean(false);

	
	public RenderThread(PacMan pacMan, GamePanel gamePanel, Maze maze, ArrayList<Food> foodList, ArrayList<Ghost> ghostList, StatusBar statusBar) {
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
		setRunning(true);
		System.out.println("START - " + this.getName());
		setPause(true);
		int counter = 0;
		while(isRunning()) {
			try {
				if(pacMan.isDead()) {
					pacMan.deadAnimate();
				}
				if(isPause()) {
					pause();
				}
				else
					if(isResume())
						Resume();

				gamePanel.gameRender(pacMan, maze, foodList, ghostList);
				gamePanel.paintScreen();
				
			
				int fps = updatedFPS(GameController.getFPS(), date);
				date = System.currentTimeMillis();
				GameController.setFPS(fps);
				
				Thread.sleep(1000L / fps);
				
				if(counter == 0)
					statusBar.updateFPS("" + fps);
				counter++;
				counter = counter % 10;
				
			}catch(InterruptedException ex) {
				
			}
		}
		System.out.println("STOP - " + this.getName());
    }




	public static int updatedFPS(int fps, long date) {
		int currFPS = 30;
		long currentTime = System.currentTimeMillis();
		int sleeptime = (int)(1000L / fps);
		
		if(date != 0 && (currentTime - date - sleeptime) != 0)
			currFPS = (int)(1000 / (currentTime - date - sleeptime) / 2);
		
		if(currFPS > 60)
			currFPS = 60;
		else if(currFPS < 30)
			currFPS = 30;
		
		return currFPS;
	}

	public boolean isResume() {
		return resume.get();
	}

	public void pause() {
		setPause(true);
		statusBar.updateState("PAUSED");
	}
	
	public void Resume(){
		setPause(false);
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

		setRunning(false);
		try {
			this.join(300);
			if (this.isAlive()){

				this.interrupt();
			}
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public boolean isPause() {
		return pause.get();
	}

	public void setPause(boolean pause) {
		this.pause = new AtomicBoolean(pause);
	}

	public boolean isRunning() {
		return running.get();
	}

	public void setRunning(boolean running) {
		this.running = new AtomicBoolean(running);
	}




	
	public static boolean getResume() {
		return resume.get();
	}




	public static void setResume(boolean resume) {
		RenderThread.resume = new AtomicBoolean(resume);
	}

	
}
