package inDev;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

public class GameController implements Runnable{
	
	private PacMan pacMan;
    private boolean running;
    private boolean pause;
	private GamePanel gamePanel;
	private PacManGame frame = null; 
	
	
    public GameController(GamePanel gamePanel, PacManGame frame) {
		// TODO Auto-generated constructor stub
    	this.gamePanel = gamePanel;
    	this.frame = frame;
    	frame.setContentPane(gamePanel);
    	pacMan = new PacMan();
    	URL url = GameController.class.getResource("beginning.wav");
    	Sound beginning = new Sound(url);
        
        gamePanel.addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) {
        		
        		int key = e.getKeyCode();
        		
        		if (key == KeyEvent.VK_P) {
    	        	pause = !pause;
        		}
        		
        		if(!pause){
        			pacMan.keyPressed(e);
        		}
        	
        	}
        	//public void keyReleased(KeyEvent e) {
        	//	pacMan.keyReleased(e);
        	//}
        });
        beginning.play();
        startGame();
	}
    
	@Override
	public void run() {
    	running = true;
    	pause = false;
    	while(running) {
    		if(! pause) {
	    		gameUpdate();
	    		gamePanel.gameRender(pacMan);
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
