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
import Models.ToSprite;
import Models.Characters.Ghost;
import  Models.Characters.PacMan;
import Models.Foods.Food;
import  Models.Foods.Fruit;
import  Models.Foods.Gum;
import  Models.Foods.PacGum;
import Threads.AudioThread;
import Threads.PhysicsThread;
import Threads.RenderThread;
import Views.GameMenu;
import  Views.GamePanel;
import  Views.MainGame;
import  Views.StatusBar;

public class GameController implements Runnable{


	private MainGame frame; 

	private JPanel mainPane;
	private GamePanel gamePanel;
	private StatusBar statusBar;

	private PacMan pacMan;
	private Maze maze;
	private ArrayList<Food> foodList;

	//private Sound music;
	private int size, defaultSize;
	private int level;
	private int score;
	private int lives;

	private static final int PM_INITIAL_POSITION = 60;
	private static final long JOIN_TIMER = 10;
	private static final int PINK_INITIAL_POSITION = 26;
	private static final int ORANGE_INITIAL_POSITION = 27;
	private static final int RED_INITIAL_POSITION = 28;
	private static final int TURQUOISE_INITIAL_POSITION = 29;
	
	private Image [][] images;
	private BufferedImage output;
	private Graphics g;    
	private ArrayList<Ghost> ghostList;
	private boolean running;
    private int ghostOutside;
	private boolean pause;    
    private static int RESUME;
    private boolean resume;
	private boolean fullScreen;
	private boolean resize;
	private boolean gameOver; 
	private int [][] grille;
	private int nRow; 
	private int nColumn;	
	private int firstGhostToQuit;
	private final int SLEEP_TIMER = 1;
	private int ghostCounter = 0;
	
	private static long timeUpdate = 10;
	private static int FPS = 30;

    private Thread gameThread; 
	private RenderThread tRender;
	private AudioThread tAudio;
	private ArrayList<Point> listTunnelLeft;
	private ArrayList<Point> listTunnelRight;

	private PhysicsThread tPhysics;
	
	
	public GameController(MainGame frame) {    	
		
		this.gamePanel = new GamePanel(this);

		init();
		
		this.setFrame(frame);

		statusBar = new StatusBar();
		
		// Creation du Border Layout pour contenir le gamePanel et le StatusBar
		BorderLayout bl = new BorderLayout();
		mainPane = new JPanel(bl);
		getMainPane().add(getGamePanel(),BorderLayout.CENTER);
		getMainPane().add(getStatusBar(), BorderLayout.SOUTH);

		tAudio = new AudioThread();
		gettAudio().setName("Audio");
		gettAudio().start();
		
		gettAudio().setIsPause(true);

		//background = new Sound(GameController.class.getResource("/Sounds"+File.separator+"loop.wav"));
		//beginning = new Sound(GameController.class.getResource("/Sounds"+File.separator+"beginning.wav"));
		//death = new Sound(GameController.class.getResource("/Sounds"+File.separator+"pacman_death.wav"));


		//Lancer la musique
//		music = beginning;
//		music.play();
//		soundOn = true;

		
		
		// Lancer le jeu
		//startGame();
	}

	private void init() {
	    setRESUME(0);
		setResume(false); 
		grille = null;
		setRunning(false);
		setFullScreen(false);
		setResize(false);
		setGameOver(false); 
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
		int height = MainGame.getActualWindowHeight() - StatusBar.HEIGHT;
		int width = MainGame.getActualWindowWidth();
		size = Math.min(height / nRow, width / nColumn);
		defaultSize = size;

		// creer la matrice du labyrinthe
		grille = new int[nRow][nColumn];

		images = new Image[nRow][nColumn];
		output = new BufferedImage(defaultSize * nColumn, defaultSize * nRow, BufferedImage.TYPE_INT_ARGB );
		g = getOutput().getGraphics();

		for(int i = 0; i < nRow; i++) {
			String line = sc.nextLine();
			String[] strings = line.split(",");
			for(int j = 0; j < nColumn; j++) {
				grille[i][j] = Integer.parseInt(strings[j]);
				if(grille[i][j] < 30) {
					getImages()[i][j] = loadImage("maze" + grille[i][j] + ".png");
					getG().drawImage(getImages()[i][j], j * defaultSize, i * defaultSize,defaultSize,defaultSize, null);
				}
			}
		}
		
		setListTunnelLeft(new ArrayList<Point>());
		setListTunnelRight(new ArrayList<Point>());
		
	
		for(int i = 0; i < nRow; i++) {
			if(grille[i][0] >= 30) {
				int j = 0;
				while(grille[i][j] >= 30 && grille[i - 1][j] <= 25 && grille[i + 1][j] <= 25)
					j++;
				getListTunnelLeft().add(new Point(i, j));
			}
		}
		
		for(int i = 0; i < nRow; i++) {
			if(grille[i][nColumn - 1] >= 30) {
				int j = nColumn - 1;
				while(grille[i][j] >= 30 && grille[i - 1][j] <= 25 && grille[i + 1][j] <= 25)
					j--;
				getListTunnelRight().add(new Point(i, j));
			}
		}

		//Creer une instance de la labyrinthe
		maze = new Maze(defaultSize * nColumn, defaultSize * nRow, getOutput(), new Point(0, 0));


		//Redimensionner le labyrinthe selon la dimension actuelle de la fenêtre du jeu
		MainGame.updateMazeSize();

		
		// Creer les Gums et PacGums

		foodList = new ArrayList();
		fillFoodList(); 
	
        
        //Redimensionner le labyrinthe selon la dimension actuelle de la fenêtre du jeu
        MainGame.updateMazeSize();
        
        //Creer le PacMan  
      	ImageIcon ii = new ImageIcon("ressources" + File.separator + "Left_0.png");
         
		pacMan = new PacMan(defaultSize, defaultSize, ii.getImage(), definePosition(PM_INITIAL_POSITION), defaultSize, grille, getListTunnelLeft(), getListTunnelRight(), nColumn, nRow);
        
    	//Creer les fantomes
    	ghostList = new ArrayList<Ghost>();
    	getGhostList().add(new Ghost(defaultSize, defaultSize, loadImage("ghostpink.png"), definePosition(PINK_INITIAL_POSITION), "pink", defaultSize, grille, getListTunnelLeft(), getListTunnelRight(), nColumn, nRow));    	
    	getGhostList().add(new Ghost(defaultSize, defaultSize, loadImage("ghostorange.png"), definePosition(ORANGE_INITIAL_POSITION), "orange", defaultSize, grille, getListTunnelLeft(), getListTunnelRight(), nColumn, nRow));    	    	
    	getGhostList().add(new Ghost(defaultSize, defaultSize, loadImage("ghostred.png"), definePosition(RED_INITIAL_POSITION), "red", defaultSize, grille, getListTunnelLeft(), getListTunnelRight(), nColumn, nRow));    	    	
    	getGhostList().add(new Ghost(defaultSize, defaultSize, loadImage("ghostturquoise.png"), definePosition(TURQUOISE_INITIAL_POSITION), "turquoise", defaultSize, grille, getListTunnelLeft(), getListTunnelRight(), nColumn, nRow));    	    	
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
				if(!isGameOver()) {
				// Mettre le jeu en pause
					if (key == KeyEvent.VK_P && !isPause()) {
						pause();
					}
					if (key == KeyEvent.VK_R && isPause()) {
						setResume(true);
					}
	
	
					if (key == KeyEvent.VK_M) {
						// Mettre la musique en muet 
						if(!isResume()) {
						gettAudio().setMuteOnOffMusic(true);
					}
					}
					
					if (key == KeyEvent.VK_S) {
						// Mettre le son en muet 
						if(!isResume()) {
						gettAudio().setMuteOnOffSound(true);
					}
					}
	
					// Redimensionner le jeu
					if (key == KeyEvent.VK_F) {
						setResize(true);
						setFullScreen(!isFullScreen());
					}
				}
				if(key == KeyEvent.VK_ESCAPE) {
					setPause(true);
					if(isGameOver()) {
						setResume(true);
						getFrame().displayNewMenu();					
					}
					else
						getFrame().displayMenu();
				}


				// Changer la direction du PacMan selon la fleche choisie.
				if(!isPause()){
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
		setRunning(true);
		setPause(true);
		while(isRunning()) {
			long pastTime = System.currentTimeMillis();
			if(isGameOver()) {
				try {
					Thread.sleep(3000);					
				}catch(InterruptedException ex) {}
			}
			else {				
				if(!isPause()) {	
					gameUpdate();
				}
				else
					pause();
				if(isResume()) {
					resume();
				}
			}
			try {
				Thread.sleep(1000L / getFPS());
			}catch(InterruptedException ex) {}
		}
    }

	private void gameUpdate() {

		int raw = 0;
		int column = 0;


		if(defaultSize != 0) {
			if(!isResume()) {
			if(ghostOutside < 4) {
				if(ghostCounter == 0) {
					updateGhostPosition(getGhostList().get(getFirstGhostToQuit()));
	    			raw = getGhostList().get(getFirstGhostToQuit()).getY() / defaultSize;
	    			column = getGhostList().get(getFirstGhostToQuit()).getX() / defaultSize;
	    			if(grille[raw][column] == 15 ||grille[raw][column] == 2) {
	    				ghostOutside++;
	    				getGhostList().get(getFirstGhostToQuit()).setOutside(true);
	    				firstGhostToQuit = (getFirstGhostToQuit() + 1) % 4;
	    			}
				}
	    		ghostCounter++;
	    		ghostCounter = ghostCounter % 3;
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
				raw = (int) Math.floor((getPacMan().getNextY() + getPacMan().getPacManFront().get(getPacMan().getNextDirection()).x)/defaultSize) % nRow;
				column = (int) Math.floor((getPacMan().getNextX() + getPacMan().getPacManFront().get(getPacMan().getNextDirection()).y)/defaultSize) % nColumn;
				tile = grille[raw][column];
			}
			    			
			
			if(tile == 0 || tile >= 30) {
				updatePositions(raw, column, true);
			}
			else{
				getPacMan().nextX();
				getPacMan().nextY();
				raw = (int) Math.floor((getPacMan().getNextY() + getPacMan().getPacManFront().get(getPacMan().getDirection()).x)/defaultSize) % nRow;
				column = (int) Math.floor((getPacMan().getNextX() + getPacMan().getPacManFront().get(getPacMan().getDirection()).y)/defaultSize) % nColumn;    		
				tile = grille[raw][column];

				if(tile == 0 || tile >= 30) {
					updatePositions(raw, column, false);
				}
			}
		}
		
		if(getPacMan().isDead() && pacMan.getDeadAnimationCounter() == 0) {
		
				gettAudio().setIsDead(true);
				getPacMan().deadAnimate();
			
			//getPacMan().setIsDead(false);
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
				gettAudio().setIsEaten(true);
				//Tile contenant une Gum
				score += getFoodList().get(i).getGain();
				getFoodList().remove(i);
				//statusBar.updateScore();
			}
		}
		if(getFoodList().size()==0) {
			level++;
			//statusBar.updateLevel();
			getPacMan().returnInitialPosition();
			for(int i = 0; i < getGhostList().size(); i++) {
				getGhostList().get(i).returnInitialPosition();
			}
			getPacMan().initPM();
			getPacMan().setNextDirection(KeyEvent.VK_LEFT);
			getPacMan().loadImage();
			fillFoodList();
			gettAudio().setIsStart(true);
			//wantSound = soundOn;
			setPause(true);
			//mute();
			//tAudio.setMuteOnOff(true);
			resume = true;
		}

		if(!isResume()) {
			getPacMan().move(); 
			
			if(flag)
				getPacMan().updateDirection();
			getPacMan().setInsideTile(raw, column);
		}
	}
	
	private void startNewLife() 
	{
			getPacMan().setIsDead(false);
			lives--;
			if(lives == 0) {
				setGameOver(true);
			}
			else {		
				getPacMan().returnInitialPosition();
				for(int i = 0; i < 4; i++) {
					getGhostList().get(i).returnInitialPosition();
				}
				getPacMan().initPM();
				getPacMan().setNextDirection(KeyEvent.VK_LEFT);
				getPacMan().loadImage();
				
				setPause(true);
				setResume(true);
				
				
			}
	}

	
	public void startGame() {
		getFrame().setContentPane(getMainPane());
		getMainPane().requestFocus();
		getFrame().revalidate();
		
		
		if(getGameThread() == null || !getGameThread().isAlive()) {
		
			gettAudio().setIsStart(true);
			setGameThread(new Thread(this));
			init();
			getGameThread().start();
			
			//Lancer un listener sur le clavier
			addListeners();
			
			//System.out.println("Physics coming threw");
			
			settPhysics(new PhysicsThread(getPacMan(),getGhostList()));
			gettPhysics().setName("Physics");
			gettPhysics().start();
			
			tRender = new RenderThread(getPacMan(), getGamePanel(), getMaze(), getFoodList(), getGhostList(), getStatusBar());
			gettRender().setName("Render");
			gettRender().start();
			
			getFrame().getMenuPane().gameRunning();
			pause = false;
			resume = true;
		}
		setResume(true);
	}
	
	
	public void stop() {
		setRunning(false);
		gettRender().setRunning(false);
		//******************************************************
		//tAudio.setIsRunning(false);
		try {
			
			getGameThread().join(500);
			if (getGameThread().isAlive()){
				getGameThread().interrupt();
			}
		}catch (InterruptedException e){
			e.printStackTrace();
		}
		getFrame().getMenuPane().noMoreRunning();
		getFrame().displayMenu();
	}

	public void pause() {
		while(gettRender() == null) {}
		gettAudio().setIsPause(true);
		gettRender().pause();
		setPause(true);
	}

	/*private void mute() {
		music.stop();
		soundOn = false;
	}

	private void unMute() {
		music.loop();
		soundOn = true;
	}*/

	public void resume(){
		setPause(false);
		while(gettRender() == null) {}
		gettAudio().setIsPause(false);
		gettRender().res();
		
		/*if(wantSound) {
			music = getBackground();
			unMute();}	*/	
		setResume(false);
		if(getPacMan().isResurrection()) {
			getPacMan().setResurrection(false);
		}
	}
	
	
	
	public Point definePosition(int initialPositionValue) {
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
	
	public void changeVolume() {

		getFrame().setContentPane(getFrame().getAudioPane());
		getFrame().requestFocus();
		getFrame().revalidate();
	}

	public int[][] getGrille() {
		return grille;
	}

	public void setGrille(int[][] grille) {
		
		this.grille = grille;
	}

	public int getnRow() {
		return nRow;
	}

	public void setnRow(int nRow) {
		this.nRow = nRow;
	}

	public int getnColumn() {
		return nColumn;
	}

	public void setnColumn(int nColumn) {
		this.nColumn = nColumn;
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
	/*public Sound getBackground() {
		return background;
	}

	/**
	 * @return the beginning
	 */
	/*public Sound getBeginning() {
		return beginning;
	}*/

	/**
	 * @return the death
	 */
	/*public Sound getDeath() {
		return death;
	}*/

	/**
	 * @return the mainPane
	 */
	public JPanel getMainPane() {
		return mainPane;
	}

	public static long getJoinTimer() {
		return JOIN_TIMER;
	}

	public MainGame getFrame() {
		return frame;
	}

	public void setFrame(MainGame frame) {
		this.frame = frame;
	}

	public boolean isFullScreen() {
		return fullScreen;
	}

	public void setFullScreen(boolean fullScreen) {
		this.fullScreen = fullScreen;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public boolean isResume() {
		return resume;
	}

	public void setResume(boolean resume) {
		this.resume = resume;
	}

	public boolean isResize() {
		return resize;
	}

	public void setResize(boolean resize) {
		this.resize = resize;
	}

	public int getScore() {
		// TODO Auto-generated method stub
		return score;
	}

	public int getLives() {
		// TODO Auto-generated method stub
		return lives;
	}

	public int getLevel() {
		// TODO Auto-generated method stub
		return level;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public static int getRESUME() {
		return RESUME;
	}

	public static void setRESUME(int rESUME) {
		RESUME = rESUME;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getDefaultSize() {
		return defaultSize;
	}

	public void setDefaultSize(int defaultSize) {
		this.defaultSize = defaultSize;
	}

	public static long getTimeUpdate() {
		return timeUpdate;
	}

	public static void setTimeUpdate(long timeUpdate) {
		GameController.timeUpdate = timeUpdate;
	}

	public static int getFPS() {
		return FPS;
	}

	public static void setFPS(int fPS) {
		FPS = fPS;
	}

	public Thread getGameThread() {
		return gameThread;
	}

	public void setGameThread(Thread gameThread) {
		this.gameThread = gameThread;
	}

	public PhysicsThread gettPhysics() {
		return tPhysics;
	}

	public void settPhysics(PhysicsThread tPhysics) {
		this.tPhysics = tPhysics;
	}

	/**
	 * @return the gamePanel
	 */
	public GamePanel getGamePanel() {
		return gamePanel;
	}

	/**
	 * @return the tAudio
	 */
	public AudioThread gettAudio() {
		return tAudio;
	}

	/**
	 * @return the running
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * @return the statusBar
	 */
	public StatusBar getStatusBar() {
		return statusBar;
	}

	/**
	 * @return the images
	 */
	public Image [][] getImages() {
		return images;
	}

	/**
	 * @return the output
	 */
	public BufferedImage getOutput() {
		return output;
	}

	/**
	 * @return the g
	 */
	public Graphics getG() {
		return g;
	}

	/**
	 * @return the listTunnelLeft
	 */
	public ArrayList<Point> getListTunnelLeft() {
		return listTunnelLeft;
	}

	/**
	 * @return the listTunnelRight
	 */
	public ArrayList<Point> getListTunnelRight() {
		return listTunnelRight;
	}

	/**
	 * @param listTunnelRight the listTunnelRight to set
	 */
	public void setListTunnelRight(ArrayList<Point> listTunnelRight) {
		this.listTunnelRight = listTunnelRight;
	}

	/**
	 * @param listTunnelLeft the listTunnelLeft to set
	 */
	public void setListTunnelLeft(ArrayList<Point> listTunnelLeft) {
		this.listTunnelLeft = listTunnelLeft;
	}

	/**
	 * @return the tRender
	 */
	public RenderThread gettRender() {
		return tRender;
	}

	/**
	 * @param running the running to set
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}	
	
	
	
	
}