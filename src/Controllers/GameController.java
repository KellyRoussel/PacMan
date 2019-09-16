package Controllers;

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
import Models.ToSprite;
import Models.Characters.Ghost;
import  Models.Characters.PacMan;
import Models.Foods.Food;
import  Models.Foods.Fruit;
import  Models.Foods.Gum;
import  Models.Foods.PacGum;
import Threads.PhysicsThread;
import Views.GameMenu;
import  Views.GamePanel;
import  Views.MainGame;
import  Views.StatusBar;

public class GameController implements Runnable{


	public MainGame frame; 

	private JPanel mainPane;
	private GamePanel gamePanel;
	private StatusBar statusBar;

	private PacMan pacMan;
	private Maze maze;
	private ArrayList<Food> foodList;

	private Sound music;
	private static int size, defaultSize;
	private static int level;
	private static int score;
	private static int lives = 3 ;

	public static final int FPS = 5;
	private static final int PM_INITIAL_POSITION = 60;

	private static final long JOIN_TIMER = 10;
	private static final int PINK_INITIAL_POSITION = 26;
	private static final int ORANGE_INITIAL_POSITION = 27;
	private static final int RED_INITIAL_POSITION = 28;
	private static final int TURQUOISE_INITIAL_POSITION = 29;
	
    private boolean running;
    private boolean soundOn;
    
	private ArrayList<Ghost> ghostList;

	public static int ghostOutside;
	private int firstGhostToQuit;
	private final int SLEEP_TIMER = 1;

    public static boolean pause;    


	private boolean wantSound = true;

	public static int RESUME;
	public static boolean resume;
	public static boolean fullScreen;
	public static boolean resize;
	public static boolean gameOver; 

	private static int [][] grille;
	private static int nRow; 
	private static int nColumn;
	private Sound background;
	private Sound beginning;
	private Sound death;

	public static ArrayList<Point> listTunnelLeft;

	public static ArrayList<Point> listTunnelRight;

	public GameController(GamePanel gamePanel, MainGame frame) {    	    	

		init();
		
		this.gamePanel = gamePanel;
		this.frame = frame;

		// Creation du Border Layout pour contenir le gamePanel et le StatusBar
		BorderLayout bl = new BorderLayout();
		mainPane = new JPanel(bl);
		mainPane.add(gamePanel,BorderLayout.CENTER);
		mainPane.add(statusBar, BorderLayout.SOUTH);


		background = new Sound(GameController.class.getResource("/Sounds"+File.separator+"loop.wav"));
		beginning = new Sound(GameController.class.getResource("/Sounds"+File.separator+"beginning.wav"));
		death = new Sound(GameController.class.getResource("/Sounds"+File.separator+"pacman_death.wav"));


		//Lancer la musique
//		music = beginning;
//		music.play();
//		soundOn = true;

		level = 1;
		
		// Lancer le jeu
		//startGame();
	}

	private void init() {
		fullScreen = false;
		resize = false;
		gameOver = false; 
		statusBar = new StatusBar();

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
					images[i][j] = loadImage("maze" + GameController.getGrille()[i][j] + ".png");
					g.drawImage(images[i][j], j * defaultSize, i * defaultSize,defaultSize,defaultSize, null);
				}
			}
		}
		
		listTunnelLeft = new ArrayList<Point>();
		listTunnelRight = new ArrayList<Point>();
		
	
		for(int i = 0; i < nRow; i++) {
			if(grille[i][0] >= 30) {
				int j = 0;
				while(grille[i][j] >= 30 && grille[i - 1][j] <= 25 && grille[i + 1][j] <= 25)
					j++;
				listTunnelLeft.add(new Point(i, j));
			}
		}
		
		for(int i = 0; i < nRow; i++) {
			if(grille[i][nColumn - 1] >= 30) {
				int j = nColumn - 1;
				while(grille[i][j] >= 30 && grille[i - 1][j] <= 25 && grille[i + 1][j] <= 25)
					j--;
				listTunnelRight.add(new Point(i, j));
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
	
        
        //Redimensionner le labyrinthe selon la dimension actuelle de la fenêtre du jeu
        MainGame.updateMazeSize();
        
    	//Creer le PacMan          
    	pacMan = new PacMan(defaultSize, defaultSize, loadImage("Left_0.png"), definePosition(PM_INITIAL_POSITION));
        
    	//Creer les fantomes
    	ghostList = new ArrayList<Ghost>();
    	ghostList.add(new Ghost(defaultSize, defaultSize, loadImage("ghostpink.png"), definePosition(PINK_INITIAL_POSITION), "pink"));    	
    	ghostList.add(new Ghost(defaultSize, defaultSize, loadImage("ghostorange.png"), definePosition(ORANGE_INITIAL_POSITION), "orange"));    	    	
    	ghostList.add(new Ghost(defaultSize, defaultSize, loadImage("ghostred.png"), definePosition(RED_INITIAL_POSITION), "red"));    	    	
    	ghostList.add(new Ghost(defaultSize, defaultSize, loadImage("ghostturquoise.png"), definePosition(TURQUOISE_INITIAL_POSITION), "turquoise"));    	    	
    	ghostOutside = 0;
    	firstGhostToQuit = (int)(Math.random() * 4);
    	
    	// Creer les Gums et PacGums
    	
    	foodList = new ArrayList();
    	fillFoodList();    	    	
    	
    }
    
    private void fillFoodList() {
    	for(int i = 0; i < nRow; i++)
    		for(int j = 0; j < nColumn; j++) {
    			if(grille[i][j] == 30)
    				foodList.add(new Gum((defaultSize*3)/4, (defaultSize*3)/4, loadImage("gum.png"), new Point(j * defaultSize, i * defaultSize)));
		    	if(grille[i][j] == 40)
    				foodList.add(new PacGum(defaultSize, defaultSize, loadImage("pacGum.png"), new Point(j * defaultSize, i * defaultSize)));
		    	if(grille[i][j] == 50)
    				foodList.add(new Fruit(defaultSize, defaultSize, loadImage("fruit.png"), new Point(j * defaultSize, i * defaultSize)));
    		}
    }

	private Image loadImage(String fileName) {
		ImageIcon icon = new ImageIcon("ressources" + File.separator + fileName);
		return icon.getImage();
	}

	private void addListeners() {
		mainPane.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {

				int key = e.getKeyCode();

				// Mettre le jeu en pause
				if (key == KeyEvent.VK_P && !pause) {
					pause();
				}
				if (key == KeyEvent.VK_R && pause) {
					resume = true;
				}


				if (key == KeyEvent.VK_M) {
					// Mettre le jeu en muet 
					if(!resume) {
					if(soundOn || pause) {
						mute();
					}else {unMute();}
				}
				}

				// Redimensionner le jeu
				if (key == KeyEvent.VK_F) {
					resize = true;
					fullScreen = !fullScreen;
				}
				if(key == KeyEvent.VK_ESCAPE) {
					pause = true;
					frame.setContentPane(frame.menuPane);
					frame.setVisible(true);
					frame.menuPane.requestFocus();
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
		pause = true;
		while(running && !gameOver) {
			if(! pause) {	
				gameUpdate();
			}else if(resume) {
				resume();
			}else if(pacMan.isDead()) {
				
				pacMan.deadAnimate();
			}
    		gamePanel.gameRender(pacMan, maze, foodList, ghostList);
			gamePanel.paintScreen();

			try {
				Thread.sleep(FPS);
			}catch(InterruptedException ex) {}
		}
    }

	private void gameUpdate() {

		int raw = 0;
		int column = 0;


		if(defaultSize != 0) {
			if(!resume) {
			if(ghostOutside < 4) {
				updateGhostPosition(ghostList.get(firstGhostToQuit));
    			raw = ghostList.get(firstGhostToQuit).getY() / GameController.getDefaultSize();
    			column = ghostList.get(firstGhostToQuit).getX() / GameController.getDefaultSize();
    			if(GameController.getGrille()[raw][column] == 15 ||GameController.getGrille()[raw][column] == 2) {
    				ghostOutside++;
    				ghostList.get(firstGhostToQuit).setOutside(true);
    				firstGhostToQuit = (firstGhostToQuit + 1) % 4;
    			}
    		}
    		
    		for(int i = 0; i < ghostList.size(); i++) {
    			if(ghostList.get(i).isOutside()) {
    				updateGhostPosition(ghostList.get(i));
    			}
    		}
			}
			//Pour savoir le tile suivant ou le pacman va se placer
			

			int tile = -1;
			if(!pacMan.isInsideTunnel()) {
				pacMan.nextNextX();
				pacMan.nextNextY();
				raw = (int) Math.floor((pacMan.getNextY() + pacMan.pacManFront.get(pacMan.getNextDirection()).x)/defaultSize) % nRow;
				column = (int) Math.floor((pacMan.getNextX() + pacMan.pacManFront.get(pacMan.getNextDirection()).y)/defaultSize) % nColumn;
				tile = grille[raw][column];
			}
			    			
			
			if(tile == 0 || tile >= 30) {
				updatePositions(raw, column, true);
			}
			else{
				pacMan.nextX();
				pacMan.nextY();
				raw = (int) Math.floor((pacMan.getNextY() + pacMan.pacManFront.get(pacMan.getDirection()).x)/defaultSize) % nRow;
				column = (int) Math.floor((pacMan.getNextX() + pacMan.pacManFront.get(pacMan.getDirection()).y)/defaultSize) % nColumn;    		
				tile = grille[raw][column];

				if(tile == 0 || tile >= 30) {
					updatePositions(raw, column, false);
				}
				else
					statusBar.updateCollision(pacMan.getDirectionString());
			}
		}
		
		if(pacMan.isDead()) {
			pacMan.deadAnimate();	
			music.stop();
		}
		if(pacMan.isResurrection()) {
			startNewLife();
		}
	}

	private void updateGhostPosition(Ghost currentGhost) {
		if(currentGhost.getUpdatedAvailableDirections()!= currentGhost.getAvailableDirections()) {
			currentGhost.setAvailableDirections(currentGhost.getUpdatedAvailableDirections());
			currentGhost.setRandomDirection();
		}
		currentGhost.move();
	}

	private void updatePositions(int raw, int column, boolean flag) {
		for(int i = 0; i < foodList.size(); i++) {
			if(foodList.get(i).getInitialPosition().x / defaultSize == column && foodList.get(i).getInitialPosition().y / defaultSize == raw) {

				//Tile contenant une Gum
				score += foodList.get(i).getGain();
				foodList.remove(i);
				//statusBar.updateScore();
			}
		}
		if(foodList.size()==0) {
			setLevel(level+1);
			//statusBar.updateLevel();
			pacMan.returnInitialPosition();
			for(int i = 0; i < ghostList.size(); i++) {
				ghostList.get(i).returnInitialPosition();
			}
			pacMan.initPM();
			pacMan.setNextDirection(KeyEvent.VK_LEFT);
			pacMan.loadImage();
			fillFoodList();
			wantSound = soundOn;
			pause = true;
			mute();
			resume = true;
		}

		if(!resume) {
			statusBar.updateCollision("NONE");
			pacMan.move(); 
			
			if(flag)
				pacMan.updateDirection();
			pacMan.setInsideTile(raw, column);
		}
	}
	
	private void startNewLife() 
	{
			lives--;
			pacMan.returnInitialPosition();
			for(int i = 0; i < 4; i++) {
				ghostList.get(i).returnInitialPosition();
			}
			pacMan.initPM();
			pacMan.setNextDirection(KeyEvent.VK_LEFT);
			pacMan.loadImage();
			
			pause = true;
			resume = true;
			
			pacMan.setIsDead(false);
	}


	public void startGame() {
		resume = true;
		
		frame.setContentPane(mainPane);
		mainPane.requestFocus();
		frame.revalidate();
		
		if(!running) {
		new Thread(this).start();
		
		//Lancer un listener sur le clavier
		addListeners();
		
		//System.out.println("Physics coming threw");
		
		PhysicsThread tPhysics = new PhysicsThread(pacMan,ghostList);
		tPhysics.setName("Physics");
		tPhysics.start();
		}
		
	}
	private void stop() {
		
	}

	public void pause() {
		statusBar.updateState("PAUSED");
		pause = true;
		wantSound = soundOn;
		mute();
		
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
		pause = false;
		statusBar.updateState("RESUME");
		
		RESUME = 0;
		while(RESUME <3) {
			RESUME += 1;
			gamePanel.gameRender(pacMan, maze, foodList, ghostList);
			gamePanel.paintScreen();
			
			Models.TimerThread timerThread = new Models.TimerThread(1);
			timerThread.start();
			timerThread.setName(" RESUME TIMER");
			
		synchronized(timerThread) {
				
			try {
				timerThread.wait(1 * 1000 + 500);
				timerThread.join(JOIN_TIMER); 
				if(timerThread.isAlive()) {	timerThread.interrupt();}
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
			
		}
			statusBar.updateState("PLAY");
			if(wantSound) {
				music = background;
				unMute();}		
		
		resume = false;
		if(pacMan.isResurrection()) {
			pacMan.setResurrection(false);
		}
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
	
	public void changeVolume() {
		frame.setContentPane(frame.audioPane);
		frame.audioPane.requestFocus();
		frame.revalidate();
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