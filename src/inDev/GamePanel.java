package inDev;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class GamePanel extends JPanel implements Runnable{
	
	
    private PacMan pacMan;
    public boolean running;
    private Graphics dbg;
    private Image dbImage = null;
    
    public GamePanel() {
        initPanel();
        startGame();
    }
    
    public void run() {
    	running = true;
<<<<<<< HEAD
=======
    	long beforeTime, afterTime, timeDiff, sleepTime;
    	long overSleepTime = 0;
    	int noDelays = 0;
    	long excess = 0;
    	
    	beforeTime =  java.lang.System.nanoTime(); 
    	long period = 2000;
    	
>>>>>>> 4265f65227683a081b33fdc875e05eb7c5aeb922
    	while(running) {
    		gameUpdate();
    		gameRender();
    		paintScreen();
    		
    		try {
    			Thread.sleep(1);
    		}catch(InterruptedException ex) {}
    	}
    }
 
    private void initPanel() {

        addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) {
        		pacMan.keyPressed(e);
        	}
        	public void keyReleased(KeyEvent e) {
        		pacMan.keyReleased(e);
        	}
        });
        
        setBackground(Color.black);
        setFocusable(true);

        pacMan = new PacMan();
    }

    private void paintScreen() {
        Graphics g;
        try {
			 g = this.getGraphics(); 
			 g.drawImage(dbImage, 0, 0, null);
			 Toolkit.getDefaultToolkit().sync(); 
		}
        catch (Exception e){ 
        	System.out.println("Graphics context error: " + e);  
        }
	}
    
    private void gameUpdate() {
    	pacMan.move();
    }	
    
    private void gameRender() {
    	if (dbImage == null){
            dbImage = createImage(PacManGame.DefaultWidth-50, PacManGame.DefaultHeight-50);
            if (dbImage == null) {
              System.out.println("dbImage is null");
			   return; }
			else {
				dbg = dbImage.getGraphics();
			}
    	}
          // clear the background
        dbg.setColor(Color.black);
        dbg.fillRect (0, 0, PacManGame.DefaultWidth, PacManGame.DefaultHeight);

        pacMan.draw(dbg);     

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