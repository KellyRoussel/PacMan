package inDev;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.JPanel;

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
	
	public static final int FPS = 20;

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
    	
    	pacMan = new PacMan();

    	Sound background = new Sound(GameController.class.getResource("/Sounds/loop.wav"));

    	
        try {
			maze = new Maze();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
    			
    			// Tester le changement du score
    			statusBar.incrementScore(1);
    			statusBar.updateScore();
	    		
    			
    			// Tester le changement des vies
    			
    			counterLives++;
        		if(counterLives % 200 == 0)
        			statusBar.decrementLife();
    			
    			gameUpdate();
	    		gamePanel.gameRender(pacMan, maze);
	    		gamePanel.paintScreen();
    		}
    		try {
    			Thread.sleep(500);
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
    	
    	if(h != 0) {
    		switch(pacMan.getDirection()) {
    		case "Left":
    			nRaw = (int) Math.floor(pacMan.getNextX()/(w/33));
        		nColumn = (int) Math.floor((pacMan.getNextY() + pacMan.getH()/2)/(h/30));
    			break;
    		case "Right":
    			nRaw = (int) Math.floor((pacMan.getNextX() + pacMan.getW())/(w/33));
        		nColumn = (int) Math.floor((pacMan.getNextY()+pacMan.getH()/2)/(h/30));
    			break;
    		case "Up":
    			nRaw = (int) Math.floor(pacMan.getNextX()/(w/33));
        		nColumn = (int) Math.floor(pacMan.getNextY()/(h/30));
    			break;
    		case "Down":
    			nRaw = (int) Math.floor(pacMan.getNextX()/(w/33));
        		nColumn = (int) Math.floor(pacMan.getNextY()/(h/30));
    			break;
    		default:
    			break;
    		}
    		
    		int tile = maze.getMaze()[nRaw][nColumn];
    		if(tile != 0) {
    			//Afficher collision dans la StatusBar
    	}else{
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
