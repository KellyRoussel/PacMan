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
import Threads.RenderThread;
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
	private static int lives;

	public static final int FPS = 10;
	private static final int PM_INITIAL_POSITION = 60;

	public static final long JOIN_TIMER = 10;
	private static final int PINK_INITIAL_POSITION = 26;
	private static final int ORANGE_INITIAL_POSITION = 27;
	private static final int RED_INITIAL_POSITION = 28;
	private static final int TURQUOISE_INITIAL_POSITION = 29;
	
    public static boolean running;
    public Image [][] images;
    public BufferedImage output;
    public Graphics g;

    private boolean soundOn;
    
	private ArrayList<Ghost> ghostList;

	public static int ghostOutside;
	private int firstGhostToQuit;
	private final int SLEEP_TIMER = 1;

    public static boolean pause;    

    

    private Thread gameThread; 
    
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

	private RenderThread tRender;

	public static ArrayList<Point> listTunnelLeft;

	public static ArrayList<Point> listTunnelRight;

	public GameController(GamePanel gamePanel, MainGame frame) {    	

		init();
		
		this.gamePanel = gamePanel;
		this.frame = frame;

		statusBar = new StatusBar();
		
		// Creation du Border Layout pour contenir le gamePanel et le StatusBar
		BorderLayout bl = new BorderLayout();
		mainPane = new JPanel(bl);
		getMainPane().add(gamePanel,BorderLayout.CENTER);
		getMainPane().add(statusBar, BorderLayout.SOUTH);


		background = new Sound(GameController.class.getResource("/Sounds"+File.separator+"loop.wav"));
		beginning = new Sound(GameController.class.getResource("/Sounds"+File.separator+"beginning.wav"));
		death = new Sound(GameController.class.getResource("/Sounds"+File.separator+"pacman_death.wav"));


		//Lancer la musique
//		music = beginning;
//		music.play();
//		soundOn = true;

		
		
		// Lancer le jeu
		//startGame();
	}

	private void init() {
		fullScreen = false;
		resize = false;
		gameOver = false; 
		score = 0;
		lives = 3;
		level = 1;

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

		images = new Image[nRow][nColumn];
		output = new BufferedImage(defaultSize * nColumn, defaultSize * nRow, BufferedImage.TYPE_INT_ARGB );
		g = output.getGraphics();

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
    	getGhostList().add(new Ghost(defaultSize, defaultSize, loadImage("ghostpink.png"), definePosition(PINK_INITIAL_POSITION), "pink"));    	
    	getGhostList().add(new Ghost(defaultSize, defaultSize, loadImage("ghostorange.png"), definePosition(ORANGE_INITIAL_POSITION), "orange"));    	    	
    	getGhostList().add(new Ghost(defaultSize, defaultSize, loadImage("ghostred.png"), definePosition(RED_INITIAL_POSITION), "red"));    	    	
    	getGhostList().add(new Ghost(defaultSize, defaultSize, loadImage("ghostturquoise.png"), definePosition(TURQUOISE_INITIAL_POSITION), "turquoise"));    	    	
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
    				getFoodList().add(new Gum((defaultSize*3)/4, (defaultSize*3)/4, loadImage("gum.png"), new Point(j * defaultSize, i * defaultSize)));
		    	if(grille[i][j] == 40)
    				getFoodList().add(new PacGum(defaultSize, defaultSize, loadImage("pacGum.png"), new Point(j * defaultSize, i * defaultSize)));
		    	if(grille[i][j] == 50)
    				getFoodList().add(new Fruit(defaultSize, defaultSize, loadImage("fruit.png"), new Point(j * defaultSize, i * defaultSize)));
    		}
    }

	private Image loadImage(String fileName) {
		ImageIcon icon = new ImageIcon("ressources" + File.separator + fileName);
		return icon.getImage();
	}

	private void addListeners() {
		getMainPane().addKeyListener(new KeyAdapter() {
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
					frame.displayMenu();
				}


				// Changer la direction du PacMan selon la fleche choisie.
				if(!pause){
					if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
						getPacMan().setNextDirection(key);
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
			}
			if(resume) {
				resume();
			}
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
				updateGhostPosition(getGhostList().get(getFirstGhostToQuit()));
    			raw = getGhostList().get(getFirstGhostToQuit()).getY() / GameController.getDefaultSize();
    			column = getGhostList().get(getFirstGhostToQuit()).getX() / GameController.getDefaultSize();
    			if(GameController.getGrille()[raw][column] == 15 ||GameController.getGrille()[raw][column] == 2) {
    				ghostOutside++;
    				getGhostList().get(getFirstGhostToQuit()).setOutside(true);
    				firstGhostToQuit = (getFirstGhostToQuit() + 1) % 4;
    			}
    		}
    		
    		for(int i = 0; i < getGhostList().size(); i++) {
    			if(getGhostList().get(i).isOutside()) {
    				updateGhostPosition(getGhostList().get(i));
    			}
    		}
			}
			//Pour savoir le tile suivant ou le pacman va se placer
			

			int tile = -1;
			if(!getPacMan().isInsideTunnel()) {
				getPacMan().nextNextX();
				getPacMan().nextNextY();
				raw = (int) Math.floor((getPacMan().getNextY() + getPacMan().pacManFront.get(getPacMan().getNextDirection()).x)/defaultSize) % nRow;
				column = (int) Math.floor((getPacMan().getNextX() + getPacMan().pacManFront.get(getPacMan().getNextDirection()).y)/defaultSize) % nColumn;
				tile = grille[raw][column];
			}
			    			
			
			if(tile == 0 || tile >= 30) {
				updatePositions(raw, column, true);
			}
			else{
				getPacMan().nextX();
				getPacMan().nextY();
				raw = (int) Math.floor((getPacMan().getNextY() + getPacMan().pacManFront.get(getPacMan().getDirection()).x)/defaultSize) % nRow;
				column = (int) Math.floor((getPacMan().getNextX() + getPacMan().pacManFront.get(getPacMan().getDirection()).y)/defaultSize) % nColumn;    		
				tile = grille[raw][column];

				if(tile == 0 || tile >= 30) {
					updatePositions(raw, column, false);
				}
			}
		}
		
		if(getPacMan().isDead()) {
			getPacMan().deadAnimate();	
			music.stop();
		}
		if(getPacMan().isResurrection()) {
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
		for(int i = 0; i < getFoodList().size(); i++) {
			if(getFoodList().get(i).getInitialPosition().x / defaultSize == column && getFoodList().get(i).getInitialPosition().y / defaultSize == raw) {

				//Tile contenant une Gum
				score += getFoodList().get(i).getGain();
				getFoodList().remove(i);
				//statusBar.updateScore();
			}
		}
		if(getFoodList().size()==0) {
			setLevel(level+1);
			//statusBar.updateLevel();
			getPacMan().returnInitialPosition();
			for(int i = 0; i < getGhostList().size(); i++) {
				getGhostList().get(i).returnInitialPosition();
			}
			getPacMan().initPM();
			getPacMan().setNextDirection(KeyEvent.VK_LEFT);
			getPacMan().loadImage();
			fillFoodList();
			wantSound = soundOn;
			pause = true;
			mute();
			resume = true;
		}

		if(!resume) {
			getPacMan().move(); 
			
			if(flag)
				getPacMan().updateDirection();
			getPacMan().setInsideTile(raw, column);
		}
	}
	
	private void startNewLife() 
	{
			lives--;
			getPacMan().returnInitialPosition();
			for(int i = 0; i < 4; i++) {
				getGhostList().get(i).returnInitialPosition();
			}
			getPacMan().initPM();
			getPacMan().setNextDirection(KeyEvent.VK_LEFT);
			getPacMan().loadImage();
			
			pause = true;
			resume = true;
			
			getPacMan().setIsDead(false);
	}

	
	public void startGame() {
		
		
		frame.setContentPane(getMainPane());
		getMainPane().requestFocus();
		frame.revalidate();
		
		if(gameThread == null || !gameThread.isAlive()) {
		
			gameThread = new Thread(this);
			init();
			gameThread.start();
			
			//Lancer un listener sur le clavier
			addListeners();
			
			//System.out.println("Physics coming threw");
			
			PhysicsThread tPhysics = new PhysicsThread(getPacMan(),getGhostList());
			tPhysics.setName("Physics");
			tPhysics.start();
			
			tRender = new RenderThread(getPacMan(), gamePanel, getMaze(), getFoodList(), getGhostList(), statusBar);
			tRender.setName("Render");
			tRender.start();
			
			
			frame.menuPane.gameRunning();
		}
		System.out.println("haa");
		resume = true;
	}
	
	
	public void stop() {
		running = false;
		try {
			gameThread.join(500);
			if (gameThread.isAlive()){
				gameThread.interrupt();
			}
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		frame.menuPane.noMoreRunning();
		frame.displayMenu();
	}

	public void pause() {
		tRender.pause();
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
		while(tRender == null) {}
		tRender.res();
		
		if(wantSound) {
			music = getBackground();
			unMute();}		
		
		resume = false;
		if(getPacMan().isResurrection()) {
			getPacMan().setResurrection(false);
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

	/**
	 * @return the maze
	 */
	public Maze getMaze() {
		return maze;
	}

	/**
	 * @return the pacMan
	 */
	public PacMan getPacMan() {
		return pacMan;
	}

	/**
	 * @return the foodList
	 */
	public ArrayList<Food> getFoodList() {
		return foodList;
	}

	/**
	 * @return the ghostList
	 */
	public ArrayList<Ghost> getGhostList() {
		return ghostList;
	}

	/**
	 * @return the firstGhostToQuit
	 */
	public int getFirstGhostToQuit() {
		return firstGhostToQuit;
	}

	/**
	 * @return the background
	 */
	public Sound getBackground() {
		return background;
	}

	/**
	 * @return the beginning
	 */
	public Sound getBeginning() {
		return beginning;
	}

	/**
	 * @return the death
	 */
	public Sound getDeath() {
		return death;
	}

	/**
	 * @return the mainPane
	 */
	public JPanel getMainPane() {
		return mainPane;
	}	
}