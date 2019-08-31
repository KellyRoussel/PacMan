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
	
    public GameController(GamePanel gamePanel, PacManGame frame) {
		// TODO Auto-generated constructor stub
    	this.gamePanel = gamePanel;
    	this.frame = frame;
    	
		BorderLayout bl = new BorderLayout();
		JPanel mainPane = new JPanel(bl);
    	
    	frame.setContentPane(mainPane);
    	mainPane.add(gamePanel,BorderLayout.CENTER);
    	mainPane.add(new StatusBar(), BorderLayout.SOUTH);
    	
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
		
    	float h = gamePanel.getSize().height;
    	float w = gamePanel.getSize().width;
    	
    	// Juste des essais pour le détections des murs
     	int nRaw = (int) Math.floor(pacMan.nextX()/(h/33));
     	int nColumn = (int) Math.floor(pacMan.nextY()/(h/30));
     	//System.out.println(maze.maze[0][0]);
     	//System.out.println(nRaw);
     	//System.out.println(nColumn);
//    	System.out.println(h);
//    	System.out.println(w);
    	//System.out.println(maze.maze[nRaw][nColumn]);
//    	if(tile != 0) {
//    		System.out.println("Mur !");
     		//Afficher collision dans la StatusBar
//    	}else{
     	pacMan.move();
	//}
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
