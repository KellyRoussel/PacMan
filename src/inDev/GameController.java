package inDev;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
<<<<<<< HEAD
import java.io.FileNotFoundException;
=======
import java.net.URL;
>>>>>>> 8b2791e3d2925bb7b0e9b018d4cb65af471e1afc

public class GameController implements Runnable{
	
	private PacMan pacMan;
    private boolean running;
    private boolean pause;
	private GamePanel gamePanel;
	private PacManGame frame = null; 
	private Maze maze = null;
	
    public GameController(GamePanel gamePanel, PacManGame frame) {
		// TODO Auto-generated constructor stub
    	this.gamePanel = gamePanel;
    	this.frame = frame;
    	
    	frame.setContentPane(gamePanel);
    	
    	pacMan = new PacMan();
<<<<<<< HEAD
    	Sound beginning = new Sound(GameController.class.getResource("/Sounds/beginning.wav"));
=======
<<<<<<< HEAD
    	
        try {
			maze = new Maze();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
=======
    	URL url = GameController.class.getResource("/Sounds/beginning.wav");
    	Sound beginning = new Sound(url);
>>>>>>> 7f0ac054b8f5a41fbae6fda111891fc22552f523
        
>>>>>>> 8b2791e3d2925bb7b0e9b018d4cb65af471e1afc
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
<<<<<<< HEAD
        beginning.play();
=======
<<<<<<< HEAD
        
=======
        beginning.loop();
>>>>>>> 8b2791e3d2925bb7b0e9b018d4cb65af471e1afc
>>>>>>> 7f0ac054b8f5a41fbae6fda111891fc22552f523
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
