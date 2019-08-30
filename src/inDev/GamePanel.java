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
    public boolean pause;
    private Graphics dbg;
    private Image dbImage = null;
    
    public GamePanel() {
        initPanel();
        startGame();
    }
    
    public void run() {
    	running = true;
    	pause = false;
    	while(running) {
    		if(! pause) {
	    		gameUpdate();
	    		gameRender();
	    		paintScreen();
    		}
    		try {
    			Thread.sleep(20);
    		}catch(InterruptedException ex) {}
    	}
    }
 
    private void initPanel() {
    	
        pacMan = new PacMan();
        
        addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) {
        		
        		int key = e.getKeyCode();
        		
        		if (key == KeyEvent.VK_P) {
    	        	pause = !pause;
    	        	System.out.println(pause);
        		}
        		
        		if(!pause){
        			pacMan.keyPressed(e);
        		}
        	
        	}
        	public void keyReleased(KeyEvent e) {
        		pacMan.keyReleased(e);
        	}
        });
        
        setBackground(Color.black);
        setFocusable(true);

    }

    
    
    private void gameUpdate() {
    	pacMan.move();
    }	
    
    private void gameRender() {
    	if (dbImage == null){
            dbImage = createImage(PacManGame.DefaultWidth, PacManGame.DefaultHeight);
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