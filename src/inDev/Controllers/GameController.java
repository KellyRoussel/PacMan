package inDev.Controllers;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.JPanel;



import inDev.Models.Maze;
import inDev.Models.Sound;
import inDev.Models.Characters.PacMan;
import inDev.Models.Foods.Fruit;
import inDev.Models.Foods.Gum;
import inDev.Models.Foods.PacGum;
import inDev.Views.GamePanel;
import inDev.Views.PacManGame;
import inDev.Views.StatusBar;

public class GameController implements Runnable{

	
	private PacManGame frame; 
	
	private GamePanel gamePanel;
	private StatusBar statusBar;
	
	private PacMan pacMan;
	private Maze maze;
	private ArrayList<Gum> gumList = null;
	private ArrayList<PacGum> pacGumList = null;
	private ArrayList<Fruit> fruitList = null;
	
	private Sound music;
	
	public static final int FPS = 10;
	public static final int GUM_GAIN = 4;
	public static final int PAC_GUM_GAIN = 8;
	public static final int FRUIT_GAIN = 16;
	
    private boolean running;
    private boolean pause;
    private boolean soundOn;
    
	public static boolean fullScreen = false;
    public static boolean resize = false;
	
    private int [][] pacGumIndexes; 

	
    public GameController(GamePanel gamePanel, PacManGame frame) {
		// TODO Auto-generated constructor stub
    	    	
    	this.gamePanel = gamePanel;
    	this.frame = frame;
    	
		BorderLayout bl = new BorderLayout();
		JPanel mainPane = new JPanel(bl);
    	statusBar = new StatusBar();
		
    	frame.setContentPane(mainPane);
    	
    	mainPane.add(gamePanel,BorderLayout.CENTER);
    	mainPane.add(statusBar, BorderLayout.SOUTH);
    	
    	
    	Sound background = new Sound(GameController.class.getResource("/Sounds/loop.wav"));
    	
    	
    	//Creer une instance de la labyrinthe
        try {
			maze = new Maze();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        
    	//Creer le PacMan        
    	pacMan = new PacMan();
        
    	// Creer les Gums et PacGums
    	
    	gumList = new ArrayList<Gum>();
    	pacGumList = new ArrayList<PacGum>();
    	fruitList = new ArrayList<Fruit>();
    	
    	int [][] mazeMat = maze.getMaze();
    	
    	for(int i = 0; i < 33; i++)
    		for(int j = 0; j < 30; j++) {
    			if(mazeMat[i][j] == 30)
    				gumList.add(new Gum(i, j));
		    	if(mazeMat[i][j] == 40)
					pacGumList.add(new PacGum(i, j));
		    	if(mazeMat[i][j] == 50)
		    		fruitList.add(new Fruit(i, j));
    		}

    			
    	
    	//Lancer un listener sur le clavier
        gamePanel.addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) {
        		
        		int key = e.getKeyCode();
        		
        		if (key == KeyEvent.VK_P) {
    	        	pause = !pause;
        		}
        		if (key == KeyEvent.VK_M) {
        			if (soundOn) {
        				music.stop();
        			} else { music.loop(); }
        			soundOn = !soundOn;
        		}
        		
        		if (key == KeyEvent.VK_F) {
        			resize = true;
        			fullScreen = !fullScreen;
        		}
        		
        		if(!pause){
        			pacMan.keyPressed(e);
        		}
        	
        	}
        });
        
        //Lancer la musique
        music = background;
        background.loop();
        soundOn = true;
        
        // Lancer le jeu
        startGame();
	}


	@Override
	public void run() {
    	running = true;
    	pause = false;
    	while(running) {
    		if(! pause) {		
    			gameUpdate();
	    		gamePanel.gameRender(pacMan, maze, gumList, pacGumList, fruitList);
	    		gamePanel.paintScreen();
    		}
    		try {
    			Thread.sleep(FPS);
    		}catch(InterruptedException ex) {}
    	}
    }

	private void gameUpdate() {
		
    	int nRaw = 0;
    	int nColumn = 0;
    	
    	pacMan.nextX();
    	pacMan.nextY();
    	
    	int sz = maze.getDefaultSize();
    	
    	if(sz != 0) {
    		//Pour savoir le tile suivant ou le pacman va se placer
    		switch(pacMan.getDirection()) {
	    		case "Left":
	    			nRaw = (int) Math.floor((pacMan.getNextY()+ pacMan.getH()/2)/sz) % 33;
	        		nColumn = (int) Math.floor(pacMan.getNextX()/sz) % 30;
	    			break;
	    		case "Right":
	    			nRaw = (int) Math.floor((pacMan.getNextY() + pacMan.getH()/2)/sz) % 33;
	        		nColumn = (int) Math.floor((pacMan.getNextX()+pacMan.getW())/sz) % 30;
	    			break;
	    		case "Up":
	    			nRaw = (int) Math.floor(pacMan.getNextY()/sz) % 33;
	        		nColumn = (int) Math.floor((pacMan.getNextX() + pacMan.getW()/2)/sz) % 30;
	    			break;
	    		case "Down":
	    			nRaw = (int) Math.floor((pacMan.getNextY()+pacMan.getH())/sz) % 33;
	        		nColumn = (int) Math.floor((pacMan.getNextX()+pacMan.getW()/2)/sz) % 30;
	    			break;
	    		default:
	    			break;
    		}
    		int tile = maze.getMaze()[nRaw][nColumn];
    		
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
	    				//Tile contenant un PacGum
	    				fruitList.get(i).setEaten();
	    				statusBar.incrementScore(FRUIT_GAIN);
	        			statusBar.updateScore();
	    			}
	    		}
	    		
	    		statusBar.updateCollision("NONE");
	    		pacMan.move(); 
	    		pacMan.setInsideTile(nRaw, nColumn);
    			
	    	}else{
	    		//Mur 
    			statusBar.updateCollision(pacMan.getDirection());
	    	}
	}
    }	
    
	public void startGame() {
		new Thread(this).start();
	}
	
	public void stop() {
		
	}

	public void pause() {
		
	}
	
	public void resume() {
		
	}

	
}