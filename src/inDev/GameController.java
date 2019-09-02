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
    public static boolean fullScreen = false;
    public static boolean resize = false;
	private GamePanel gamePanel;
	private PacManGame frame = null; 
	private Maze maze = null;
	private Sound music;
	private StatusBar statusBar = null;
	private ArrayList<Gum> gumList = null;
	public static final int FPS = 20;
	private static final int GUM_GAIN = 4;

	private int counterLives = 0;

	
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

    	
        try {
			maze = new Maze();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        
    	pacMan = new PacMan();
        
    	gumList = new ArrayList<Gum>();
    	int [][] mazeMat = maze.getMaze();
    	
    	for(int i = 0; i < 33; i++)
    		for(int j = 0; j < 30; j++) {
    			if(mazeMat[i][j] == 120) {
    				gumList.add(new Gum(i, j));
    			}
    		}
    	
        gamePanel.addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) {
        		
        		int key = e.getKeyCode();
        		
        		if (key == KeyEvent.VK_P) {
    	        	pause = !pause;
        		}
        		if (key == KeyEvent.VK_M) {
        			if (soundOn) {
        				music.stop();
        			} else { music.play(); }
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
        	//public void keyReleased(KeyEvent e) {
        	//	pacMan.keyReleased(e);
        	//}
        });
        music = background;
        background.loop();
        
        startGame();
	}
    
	@Override
	public void run() {
    	running = true;
    	pause = false;
    	while(running) {
    		
    		if(! pause) {
    			
    			
    			// Tester le changement des vies
    			
    			/*counterLives++;
        		if(counterLives % 200 == 0)
        			statusBar.decrementLife();
        			*/
    			
    			gameUpdate();
	    		gamePanel.gameRender(pacMan, maze, gumList);
	    		gamePanel.paintScreen();
    		}
    		try {
    			Thread.sleep(FPS);
    		}catch(InterruptedException ex) {}
    	}
    }

	private void gameUpdate() {
		
    	float h = gamePanel.getSize().height;
    	float w = gamePanel.getSize().width;
    	int nRaw = 0;
    	int nColumn = 0;

    	
    	pacMan.nextX();
    	pacMan.nextY();
    	
    	int sz = Maze.size;
    	
    	if(sz != 0) {
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
    		//System.out.println(nRaw);
    		//System.out.println(nColumn);
    		int tile = maze.getMaze()[nRaw][nColumn];
    		System.out.println(tile);
    		if(tile%60 != 0) {
    			statusBar.updateCollision(pacMan.getDirection());
    	}else{
    		for(int i = 0; i < gumList.size(); i++) {
    			if(gumList.get(i).getX() == nRaw && gumList.get(i).getY() == nColumn && !gumList.get(i).isEaten()) {
    				gumList.get(i).setEaten();
    				statusBar.incrementScore(GUM_GAIN);
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

	
	public PacMan getPacMan() {
		return pacMan;
	}

	
	public void setPacMan(PacMan pacMan) {
		this.pacMan = pacMan;
	}
	
}