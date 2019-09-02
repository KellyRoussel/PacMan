package inDev;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.sun.tools.javac.util.List;

public class GameController implements Runnable{
	
	private PacMan pacMan;
    private boolean running;
    private boolean pause;
    private boolean soundOn;
	private GamePanel gamePanel;
	private PacManGame frame = null; 
	private Maze maze = null;
	private Sound music;
	private StatusBar statusBar = null;
	private ArrayList<Gum> gumList = null;
	private ArrayList<PacGum> pacGumList = null;
	
	public static final int FPS = 20;
	public static final int GUM_GAIN = 4;
	public static final int PAC_GUM_GAIN = 8;
	
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
    	
    	//Définir les positions des pacGums
    	setPacGumsPositions();
    	
    	//Creer une instance de la labyrinthe
        try {
			maze = new Maze();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
        
    	//Creer le PacMan        
    	pacMan = new PacMan();
        
    	// Creer les Gums
    	gumList = new ArrayList<Gum>();
    	int [][] mazeMat = maze.getMaze();
    	
    	for(int i = 0; i < 33; i++)
    		for(int j = 0; j < 30; j++) {
    			if(mazeMat[i][j] == 120 && !isPacGum(i, j)) {
    				gumList.add(new Gum(i, j));
    			}
    		}
    	
    	//Creer les pacGums
    	pacGumList = new ArrayList<PacGum>();
    	
    	for(int i = 0; i < pacGumIndexes.length; i++)
    		pacGumList.add(new PacGum(pacGumIndexes[i][0], pacGumIndexes[i][1]));
    	
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
        			} else { 
        				music.play(); 
        			}
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
        
        // Lancer le jeu
        startGame();
	}

	private void setPacGumsPositions() {
		pacGumIndexes = new int[3][2];
    	
    	pacGumIndexes[0][0] = 6;
    	pacGumIndexes[0][1] = 2;
    	
    	pacGumIndexes[1][0] = 12;
    	pacGumIndexes[1][1] = 10;
    	
    	pacGumIndexes[2][0] = 30;
    	pacGumIndexes[2][1] = 13;
	}
    
	private boolean isPacGum(int x, int y) {
		for(int i = 0; i < pacGumIndexes.length; i++) {
			if(pacGumIndexes[i][0] == x && pacGumIndexes[i][1] == y)
				return true;
		}
		return false;
	}

	@Override
	public void run() {
    	running = true;
    	pause = false;
    	while(running) {
    		if(! pause) {		
    			gameUpdate();
	    		gamePanel.gameRender(pacMan, maze, gumList, pacGumList);
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
    	
    	int sz = Maze.getSize();
    	
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
    		
    		if(tile%60 != 0) {
    			//Mur 
    			statusBar.updateCollision(pacMan.getDirection());
	    	}else{
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
    		statusBar.updateCollision("NONE");
    		pacMan.move();    	
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