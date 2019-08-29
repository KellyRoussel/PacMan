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
    private Image dbImage = null;
    public boolean running;

    public GamePanel() {
        initPanel();
        startGame();
    }
    
    public void run() {
    	running = true;
    	long beforeTime, afterTime, timeDiff, sleepTime;
    	long overSleepTime = 0;
    	int noDelays = 0;
    	long excess = 0;
    	
    	beforeTime =  java.lang.System.nanoTime(); 
    	long period = 1;
    	
    	while(running) {
    		gameUpdate();
    		gameRender();
    		paintScreen();
    		
    		afterTime =  java.lang.System.nanoTime();
    		timeDiff = afterTime - beforeTime;
    		sleepTime = (period - timeDiff) - overSleepTime;
    		 if (sleepTime > 0) {     
    			 try {        
    				 Thread.sleep(sleepTime);      
    				 }      
    			 catch(InterruptedException ex){}      
    			 overSleepTime = (java.lang.System.nanoTime() - afterTime) - sleepTime;   
    			 }   
    		 else {    
    			 excess -= sleepTime;
    			 overSleepTime = 0;
    		     
    			 if (++noDelays >= 2) {
    				 Thread.yield();  
    				 noDelays = 0;
    			 }
    	}
    		 beforeTime = java.lang.System.nanoTime();
    	} 
    	System.exit(0);

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
    		if((g!=null) && (dbImage != null)) {
    			g.drawImage(pacMan.getImage(), pacMan.getX(), 
        		pacMan.getY(), this);
    		}
    		 Toolkit.getDefaultToolkit().sync();
    		 g.dispose();
    	}catch(Exception e) {
    		System.out.println("Graphics context error: "+e);
    	}
    }
    
    private void gameUpdate() {
    	pacMan.move();
    }
    
    private void gameRender() {
        
    	if (dbImage == null){
            dbImage = createImage(600, 800);
            if (dbImage == null) {
              System.out.println("dbImage is null");
              return; 
            }
            else
            	dbImage.getGraphics().setColor(Color.white);
    	}
    	
        dbImage.getGraphics().fillRect (0, 0, 600, 800);
        dbImage.getGraphics().setColor (Color.BLACK);
        
        this.repaint();
        this.validate();
        repaint(pacMan.getX(), pacMan.getY(), 
        		pacMan.getWidth(), pacMan.getHeight());     
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
