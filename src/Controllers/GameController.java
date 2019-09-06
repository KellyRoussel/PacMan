package Controllers;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;



import  Models.Maze;
import  Models.Sound;
import  Models.Characters.PacMan;
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
	private ArrayList<Gum> gumList;
	private ArrayList<PacGum> pacGumList;
	private ArrayList<Fruit> fruitList;
	
	private Sound music;
	
	public static final int FPS =10;
	public static final int GUM_GAIN = 4;
	public static final int PAC_GUM_GAIN = 100;
	public static final int FRUIT_GAIN = 16;
	
    private boolean running;
    private boolean soundOn;
    
    public static boolean pause;    
	public static boolean fullScreen;
    public static boolean resize;
	public static boolean gameOver; 
	
	
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
    	
    	
    	Sound background = new Sound(GameController.class.getResource("/Sounds/loop.wav"));
    	
    	//Lancer un listener sur le clavier
        addListeners();
        
        //Lancer la musique
        music = background;
        background.loop();
        soundOn = true;
        
        // Lancer le jeu
        startGame();
	}

    private void init() {
    	fullScreen = false;
        resize = false;
    	gameOver = false; 
    	statusBar = new StatusBar();
    	//Creer une instance de la labyrinthe
        try {
    		int height = MainGame.actualWindowHeight - StatusBar.HEIGHT;
    		int width = MainGame.actualWindowWidth;
			maze = new Maze(height, width);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        
        //Redimensionner le labyrinthe selon la dimension actuelle de la fenêtre du jeu
        MainGame.updateMazeSize();
        
    	//Creer le PacMan  
        
    	pacMan = new PacMan();
        
    	// Creer les Gums et PacGums
    	
    	gumList = new ArrayList<Gum>();
    	pacGumList = new ArrayList<PacGum>();
    	fruitList = new ArrayList<Fruit>();
    	
    	int [][] mazeMat = maze.getMaze();
    	
    	for(int i = 0; i < maze.getnRaw(); i++)
    		for(int j = 0; j < maze.getnColumn(); j++) {
    			if(mazeMat[i][j] == 30)
    				gumList.add(new Gum(i, j));
		    	if(mazeMat[i][j] == 40)
					pacGumList.add(new PacGum(i, j));
		    	if(mazeMat[i][j] == 50)
		    		fruitList.add(new Fruit(i, j));
    		}
    }

	private void addListeners() {
		gamePanel.addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) {
        		
        		int key = e.getKeyCode();
        		
        		// Mettre le jeu en pause
        		if (key == KeyEvent.VK_P) {
    	        	pause = !pause;
        		}
        		
        		
        		if (key == KeyEvent.VK_M) {
        			// Mettre le jeu en muet 
        			if (soundOn) {
        				music.stop();
        				
        			// Relancer le son
        			} else { music.loop(); }
        			soundOn = !soundOn;
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
    		}
    		gamePanel.gameRender(pacMan, maze, gumList, pacGumList, fruitList);
    		gamePanel.paintScreen();

    		try {
    			Thread.sleep(FPS);
    		}catch(InterruptedException ex) {}
    	}
    }

	private void gameUpdate() {
		
    	int nRaw = 0;
    	int nColumn = 0;
    	
    	pacMan.nextNextX();
    	pacMan.nextNextY();
    	
    	int sz = maze.getDefaultSize();
    	
    	if(sz != 0) {
    		//Pour savoir le tile suivant ou le pacman va se placer
    		nRaw = (int) Math.floor((pacMan.getNextY() + pacMan.pacManFront.get(pacMan.getNextDirection()).x)/sz) % maze.getnRaw();
    		nColumn = (int) Math.floor((pacMan.getNextX() + pacMan.pacManFront.get(pacMan.getNextDirection()).y)/sz) % maze.getnColumn();    		
    		
    		int tile = maze.getMaze()[nRaw][nColumn];
    		
    		System.out.println(tile);
    		
    		if(tile == 0 || tile >= 30) {
    			//Tile vide
	    		for(int i = 0; i < gumList.size(); i++) {
	    			if(gumList.get(i).getX() == nRaw && gumList.get(i).getY() == nColumn && !gumList.get(i).isEaten()) {
	    				//Tile contenant une Gum
	    				gumList.get(i).setEaten();
	    				statusBar.incrementScore(GUM_GAIN);
	        			statusBar.updateScore();
	    			}
	    		}
	    		for(int i = 0; i < pacGumList.size(); i++) {
	    			if(pacGumList.get(i).getX() == nRaw && pacGumList.get(i).getY() == nColumn && !pacGumList.get(i).isEaten()) {
	    				//Tile contenant un PacGum
	    				pacGumList.get(i).setEaten();
	    				statusBar.incrementScore(PAC_GUM_GAIN);
	        			statusBar.updateScore();
	    			}
	    		}		
	    		for(int i = 0; i < fruitList.size(); i++) {
	    			if(fruitList.get(i).getX() == nRaw && fruitList.get(i).getY() == nColumn && !fruitList.get(i).isEaten()) {
	    				//Tile contenant un fruit
	    				fruitList.get(i).setEaten();
	    				if(statusBar.decrementLife() == false) {
	    					gameOver = true;
	    				}
	        			statusBar.updateScore();
	    			}
	    		}
	    		
	    		statusBar.updateCollision("NONE");
	    		pacMan.move(); 
	    		pacMan.updateDirection();
	    		pacMan.setInsideTile(nRaw, nColumn);
    			
	    	}else{
	    		//Mur 
	    		nRaw = 0;
	        	nColumn = 0;
	        	
	        	pacMan.nextX();
	        	pacMan.nextY();
	        	
	        	sz = maze.getDefaultSize();
	        	
	        	if(sz != 0) {
	        		//Pour savoir le tile suivant ou le pacman va se placer
	        		nRaw = (int) Math.floor((pacMan.getNextY() + pacMan.pacManFront.get(pacMan.getDirection()).x)/sz) % maze.getnRaw();
	        		nColumn = (int) Math.floor((pacMan.getNextX() + pacMan.pacManFront.get(pacMan.getDirection()).y)/sz) % maze.getnColumn();    		

	        		
	        		tile = maze.getMaze()[nRaw][nColumn];
	        		
	        		if(tile == 0 || tile >= 30) {
	        			//Tile vide
	    	    		for(int i = 0; i < gumList.size(); i++) {
	    	    			if(gumList.get(i).getX() == nRaw && gumList.get(i).getY() == nColumn && !gumList.get(i).isEaten()) {
	    	    				//Tile contenant une Gum
	    	    				gumList.get(i).setEaten();
	    	    				statusBar.incrementScore(GUM_GAIN);
	    	        			statusBar.updateScore();
	    	    			}
	    	    		}
	    	    		for(int i = 0; i < pacGumList.size(); i++) {
	    	    			if(pacGumList.get(i).getX() == nRaw && pacGumList.get(i).getY() == nColumn && !pacGumList.get(i).isEaten()) {
	    	    				//Tile contenant un PacGum
	    	    				pacGumList.get(i).setEaten();
	    	    				statusBar.incrementScore(PAC_GUM_GAIN);
	    	        			statusBar.updateScore();
	    	    			}
	    	    		}
	    	    		
	    	    		for(int i = 0; i < fruitList.size(); i++) {
	    	    			if(fruitList.get(i).getX() == nRaw && fruitList.get(i).getY() == nColumn && !fruitList.get(i).isEaten()) {
	    	    				//Tile contenant un fruit
	    	    				fruitList.get(i).setEaten();
	    	    				if(statusBar.decrementLife() == false) {
	    	    					gameOver = true;
	    	    				}
	    	        			statusBar.updateScore();
	    	    			}
	    	    		}
	    	    		
	    	    		statusBar.updateCollision("NONE");
	    	    		pacMan.move(); 
	    	    		pacMan.setInsideTile(nRaw, nColumn);
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

	private void pause() {
		
	}
	
	private void resume() {
		
	}

	
}