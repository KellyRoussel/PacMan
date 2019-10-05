package Threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import Controllers.GameController;
import Models.Maze;
import Models.Characters.Ghost;
import Models.Characters.PacMan;
import Models.Foods.Food;
import Views.GamePanel;
import Views.StatusBar;

public class RenderThread extends Thread {

	private PacMan pacMan;
	private GamePanel gamePanel;
	private Maze maze;
	private List<Food> foodList;
	private ArrayList<Ghost> ghostList;
	private StatusBar statusBar;
	private long date = 0;
	private TimerThread timerThread;

	private static AtomicBoolean running = new AtomicBoolean(false);
	private static AtomicBoolean resume = new AtomicBoolean(false);
	private static AtomicBoolean pause = new AtomicBoolean(false);
	private static boolean isInvincible;

	public RenderThread(PacMan pacMan, GamePanel gamePanel, Maze maze, List<Food> list,
			ArrayList<Ghost> ghostList, StatusBar statusBar) {
		super();
		this.pacMan = pacMan;
		this.gamePanel = gamePanel;
		this.maze = maze;
		this.foodList = list;
		this.ghostList = ghostList;
		this.statusBar = statusBar;
	}
	// Boucle du jeu

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

	@Override
	public void run() {
		setRunning(true);
		System.out.println("START - " + this.getName());
		setPause(true);
		int counter = 0;
		while (isRunning()) {
			try {
				if (pacMan.isDead()) {
					pacMan.deadAnimate();
				}
				if (isPause()) {
					pause();
				} else if (isResume())
					Resume();
				
				gamePanel.gameRender(pacMan, maze, foodList, ghostList);
				gamePanel.paintScreen();


				int fps = updatedFPS(GameController.getFPS(), date);

				date = System.currentTimeMillis();
				GameController.setFPS(fps);

				Thread.sleep(1000L / fps);
			
				if (counter == 0)
					statusBar.updateFPS("" + GameController.getFPS());
				counter++;
				counter = counter % 10;
			} catch (InterruptedException ex) {

			}
		}
		System.out.println("STOP - " + this.getName());
	}


	

	public void pause() {
		setPause(true);
		statusBar.updateState("PAUSED");
	}

	public void Resume() {
		setPause(false);
		statusBar.updateState("RESUME");

		GameController.setRESUME(0);
		while (GameController.getRESUME() < 3) {
			GameController.setRESUME(GameController.getRESUME() + 1);

			gamePanel.gameRender(pacMan, maze, foodList, ghostList);
			gamePanel.paintScreen();

			timerThread = new Threads.TimerThread(1);
			timerThread.start();
			timerThread.setName(" RESUME TIMER");

			synchronized (timerThread) {

				try {
					timerThread.wait(1 * 1000 + 500);
					timerThread.join(GameController.getJoinTimer());
					if (timerThread.isAlive()) {
						timerThread.interrupt();
					}
				} catch (InterruptedException e) {
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
			if (this.isAlive()) {

				this.interrupt();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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

	public static boolean getResume() {
		return resume.get();
	}

	public static void setResume(boolean resume) {
		RenderThread.resume = new AtomicBoolean(resume);
	}

	public void setIsInvincible(boolean b) {
		// TODO Auto-generated method stub
		isInvincible = b;
	}

}