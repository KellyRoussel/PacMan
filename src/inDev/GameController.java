package inDev;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.net.URL;

public class GameController implements Runnable{
	
	private PacMan pacMan;
    private boolean running;
    private boolean pause;
    private boolean soundOn;
	private GamePanel gamePanel;
	private PacManGame frame = null; 
	private Maze maze = null;
	private Sound music;
	
    public GameController(GamePanel gamePanel, PacManGame frame) {
		// TODO Auto-generated constructor stub
    	this.gamePanel = gamePanel;
    	this.frame = frame;
    	
    	frame.setContentPane(gamePanel);
    	
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
	    		gameUpdate();
	    		gamePanel.gameRender(pacMan, maze);
	    		gamePanel.paintScreen();
    		}
    		try {
    			Thread.sleep(20);
    		}catch(InterruptedException ex) {}
    	}
    }

	private void gameUpdate() {
    	pacMan.move();
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
