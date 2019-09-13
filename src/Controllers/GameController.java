﻿package Controllers;

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



import  Models.Maze;
import  Models.Sound;
import  Models.Characters.PacMan;
import Models.Foods.Food;
import  Models.Foods.Fruit;
import  Models.Foods.Gum;
import  Models.Foods.PacGum;
import  Views.GamePanel;
import  Views.MainGame;
import  Views.StatusBar;

public class GameController implements Runnable{


	private MainGame frame; 

	private GamePanel gamePanel;
	private StatusBar statusBar;

	private PacMan pacMan;
	private Maze maze;
	private ArrayList<Food> foodList;

	private Sound music;
	private static int size, defaultSize;
	private static int level;
	private static int score;
	private static int lives;

	public static final int FPS = 10;
	private static final int PM_INITIAL_POSITION = 60;

	private static final long JOIN_TIMER = 10;
	private final int SLEEP_TIMER = 10;

	private boolean running;
	private boolean soundOn;
	private boolean wantSound = true;

	public static boolean pause;  
	public static boolean resume;
	public static boolean fullScreen;
	public static boolean resize;
	public static boolean gameOver; 

	private static int [][] grille;
	private static int nRow; 
	private static int nColumn;
	private Sound background;
	private Sound beginning;

	public GameController(GamePanel gamePanel, MainGame frame) {    	    	

		init();

		this.gamePanel = gamePanel;
		this.frame = frame;

		// Creation du Border Layout pour contenir le gamePanel et le StatusBar
		BorderLayout bl = new BorderLayout();
		JPanel mainPane = new JPanel(bl);

		frame.setContentPane(mainPane);

		mainPane.add(gamePanel,BorderLayout.CENTER);
		mainPane.add(statusBar, BorderLayout.SOUTH);


		background = new Sound(GameController.class.getResource("/Sounds/loop.wav"));
		beginning = new Sound(GameController.class.getResource("/Sounds/beginning.wav"));

		//Lancer un listener sur le clavier
		addListeners();

		//Lancer la musique
		music = beginning;
		music.play();
		soundOn = true;

		// Lancer le jeu
		startGame();
	}

	private void init() {
		fullScreen = false;
		resize = false;
		gameOver = false; 
		statusBar = new StatusBar();
		resume = true;

		Scanner sc = null;
		try {
			sc = new Scanner(new File("ressources" + File.separator + "maze.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String lineDimension = sc.nextLine();
		String[] dimensions = lineDimension.split(",");


		nColumn =  Integer.parseInt(dimensions[0]);
		nRow =  Integer.parseInt(dimensions[1]);	

		int height = MainGame.actualWindowHeight - StatusBar.HEIGHT;
		int width = MainGame.actualWindowWidth;

		size = Math.min(height / nRow, width / nColumn);
		defaultSize = size;

		// creer la matrice du labyrinthe
		grille = new int[nRow][nColumn];

		Image [][] images = new Image[nRow][nColumn];
		BufferedImage output = new BufferedImage(defaultSize * nColumn, defaultSize * nRow, BufferedImage.TYPE_INT_ARGB );
		Graphics g = output.getGraphics();

		for(int i = 0; i < nRow; i++) {
			String line = sc.nextLine();
			String[] strings = line.split(",");
			for(int j = 0; j < nColumn; j++) {
				grille[i][j] = Integer.parseInt(strings[j]);
				if(grille[i][j] < 30) {
					images[i][j] = new ImageIcon("ressources" + File.separator + "maze" + GameController.getGrille()[i][j] + ".png").getImage();
					g.drawImage(images[i][j], j * defaultSize, i * defaultSize,defaultSize,defaultSize, null);
				}
			}
		}



		//Creer une instance de la labyrinthe
		maze = new Maze(defaultSize * nColumn, defaultSize * nRow, output, new Point(0, 0));


		//Redimensionner le labyrinthe selon la dimension actuelle de la fenêtre du jeu
		MainGame.updateMazeSize();

		//Creer le PacMan  
		ImageIcon ii = new ImageIcon("ressources" + File.separator + "Left_0.png");

		pacMan = new PacMan(defaultSize, defaultSize, ii.getImage(), definePosition(PM_INITIAL_POSITION));

		// Creer les Gums et PacGums

		foodList = new ArrayList();
		fillFoodList();    	    	

	}

	private void fillFoodList() {
		for(int i = 0; i < nRow; i++)
			for(int j = 0; j < nColumn; j++) {
				if(grille[i][j] == 30)
					foodList.add(new Gum(defaultSize/3, defaultSize/3, loadImage("gum.png"), new Point(j * defaultSize, i * defaultSize)));
				if(grille[i][j] == 40)
					foodList.add(new PacGum(defaultSize/2, defaultSize/2, loadImage("pacGum.png"), new Point(j * defaultSize, i * defaultSize)));
				if(grille[i][j] == 50)
					foodList.add(new Fruit(defaultSize/2, defaultSize/2, loadImage("fruit.png"), new Point(j * defaultSize, i * defaultSize)));
			}
	}

	private Image loadImage(String fileName) {
		ImageIcon icon = new ImageIcon("ressources" + File.separator + fileName);
		return icon.getImage();
	}

	private void addListeners() {
		gamePanel.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {

				int key = e.getKeyCode();

				// Mettre le jeu en pause
				if (key == KeyEvent.VK_P) {
					pause();
				}
				if (key == KeyEvent.VK_R) {
					resume = true;
				}


				if (key == KeyEvent.VK_M) {
					// Mettre le jeu en muet 
					if(soundOn || pause) {
						mute();
					}else {unMute();}
				}

				// Redimensionner le jeu
				if (key == KeyEvent.VK_F) {
					resize = true;
					fullScreen = !fullScreen;
				}


				// Changer la direction du PacMan selon la fleche choisie.
				if(!pause){
					if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {

						pacMan.setNextDirection(key);
					}
				}

			}
		});
	}

	//Boucle du jeu
	@Override
	public void run() {
		running = true;
		pause = false;
		while(running && !gameOver) {
			if(! pause) {		
				gameUpdate();
			}else if(resume) {
				statusBar.updateState("RESUME");
				resume();
			}
			gamePanel.gameRender(pacMan, maze, foodList);
			gamePanel.paintScreen();

			try {
				Thread.sleep(FPS);
			}catch(InterruptedException ex) {}
		}
	}

	private void gameUpdate() {

		int raw = 0;
		int column = 0;

		pacMan.nextNextX();
		pacMan.nextNextY();


		if(defaultSize != 0) {
			//Pour savoir le tile suivant ou le pacman va se placer
			raw = (int) Math.floor((pacMan.getNextY() + pacMan.pacManFront.get(pacMan.getNextDirection()).x)/defaultSize) % nRow;
			column = (int) Math.floor((pacMan.getNextX() + pacMan.pacManFront.get(pacMan.getNextDirection()).y)/defaultSize) % nColumn;    		

			int tile = grille[raw][column];


			if(tile == 0 || tile >= 30) {
				//Tile vide


				for(int i = 0; i < foodList.size(); i++) {
					if(foodList.get(i).getInitialPosition().x / defaultSize == column && foodList.get(i).getInitialPosition().y / defaultSize == raw) {

						//Tile contenant une Gum
						score += foodList.get(i).getGain();
						foodList.remove(i);
						statusBar.updateScore();
					}
				}
				if(foodList.size()==0) {
					resume = true;
					setLevel(level+1);
					statusBar.updateLevel();
					pacMan.returnInitialPosition();
					//Renvoyer les fantomes à leur position initiale
					fillFoodList();
				}

				statusBar.updateCollision("NONE");
				pacMan.move(); 
				pacMan.updateDirection();
				pacMan.setInsideTile(raw, column);

			}else{
				//Mur 
				raw = 0;
				column = 0;

				pacMan.nextX();
				pacMan.nextY();


				if(defaultSize != 0) {
					//Pour savoir le tile suivant ou le pacman va se placer
					raw = (int) Math.floor((pacMan.getNextY() + pacMan.pacManFront.get(pacMan.getDirection()).x)/defaultSize) % nRow;
					column = (int) Math.floor((pacMan.getNextX() + pacMan.pacManFront.get(pacMan.getDirection()).y)/defaultSize) % nColumn;    		


					tile = grille[raw][column];

					if(tile == 0 || tile >= 30) {
						//Tile vide
						for(int i = 0; i < foodList.size(); i++) {
							if(foodList.get(i).getInitialPosition().x / defaultSize == column && foodList.get(i).getInitialPosition().y / defaultSize == raw) {
								//Tile contenant une Gum
								score += foodList.get(i).getGain();
								foodList.remove(i);
								statusBar.updateScore();
							}
						}

						statusBar.updateCollision("NONE");
						pacMan.move(); 
						pacMan.setInsideTile(raw, column);
					}
					else
						statusBar.updateCollision(pacMan.getDirectionString());
				}
			}
		}
	}	

	private void startGame() {
		new Thread(this).start();
	}

	private void stop() {

	}

	public void pause() {
		pause = true;
		wantSound = soundOn;
		mute();
		statusBar.updateState("PAUSED");
	}

	private void mute() {
		music.stop();
		soundOn = false;
	}

	private void unMute() {
		music.loop();
		soundOn = true;
	}

	public void resume(){

		music = background;
		Models.TimerThread timerThread = new Models.TimerThread(3);
		timerThread.start();
		timerThread.setName("TIMER 1");

		System.out.println("Start Resume");
		synchronized(timerThread) {
			try {
				timerThread.wait(3 * 1000 + 500); //pendant ce temps le thread vas afficher les 75 secaondes
				timerThread.join(JOIN_TIMER); 
				if(timerThread.isAlive()) {	
					timerThread.interrupt();
				}
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
			statusBar.updateState("PLAY");
			pause = false;
			if(wantSound) {
				unMute();
			}
		}
		resume = false;
	}

	public static Point definePosition(int initialPositionValue) {
		Point p = new Point();
		for(int i = 0; i < nRow; i++)
			for(int j = 0; j < nColumn; j++) {
				if(grille[i][j] == initialPositionValue) {
					p.x = j * GameController.getDefaultSize();
					p.y = i * GameController.getDefaultSize();
				}
			}
		return p;
	}

	public static int[][] getGrille() {
		return grille;
	}

	public static void setGrille(int[][] grille) {
		GameController.grille = grille;
	}

	public static int getnRow() {
		return nRow;
	}

	public static void setnRow(int nRow) {
		GameController.nRow = nRow;
	}

	public static int getnColumn() {
		return nColumn;
	}

	public static void setnColumn(int nColumn) {
		GameController.nColumn = nColumn;
	}

	public static int getSize() {
		return size;
	}

	public static void setSize(int size) {
		GameController.size = size;
	}

	public static int getDefaultSize() {
		return defaultSize;
	}

	public static void setDefaultSize(int defaultSize) {
		GameController.defaultSize = defaultSize;
	}

	public static int getScore() {
		return score;
	}

	public static void setScore(int score) {
		GameController.score = score;
	}

	public static int getLives() {
		return lives;
	}

	public static void setLives(int lives) {
		GameController.lives = lives;
	}

	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		GameController.level = level;
	}	
}