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
    	while(running) {
    		//gameUpdate();
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
        
        pacMan.move();
        this.repaint();
        this.validate();
        repaint(pacMan.getX(), pacMan.getY(), 
        		pacMan.getWidth(), pacMan.getHeight());     
    }    

	public void playClip(String clip) {
		try {
			File clipPath = new File(clip);
			if(clipPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(clipPath);
				Clip music = AudioSystem.getClip();
				music.open(audioInput);
				music.start();
			}
			else{System.out.println("Audio file couldn't be found");}
		}catch(Exception ex) {
			ex.printStackTrace();
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
