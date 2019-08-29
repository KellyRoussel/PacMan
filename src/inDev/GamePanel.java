package inDev;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;


import javax.swing.JFrame;
import javax.swing.JPanel;

import sun.java2d.loops.DrawRect;


public class GamePanel extends JPanel implements ActionListener, Runnable{
	
	
	
	
	private Timer timer;
    private PacMan pacMan;
    private final int DELAY = 10;
<<<<<<< HEAD
    private Image dbImage = null;
=======
    public boolean running;
>>>>>>> 9ca1d5a42171497554a1816281814476a04fc119

    public GamePanel() {
        initPanel();
    }
    
    public void run() {
    	running = true;
    	while(running) {
    		//gameUpdate();
    		//gameRender();
    		repaint();
    		
    		try {
    			Thread.sleep(20);
    		}catch(InterruptedException ex) {}
    	}
    }

    private void initPanel() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        pacMan = new PacMan();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
        
        Toolkit.getDefaultToolkit().sync();
    }
    
    private void doDrawing(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(pacMan.getImage(), pacMan.getX(), 
        		pacMan.getY(), this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        step();
    }
    
    private void step() {
        
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

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
        	pacMan.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
        	pacMan.keyPressed(e);
        }
    }
	
	public void startGame() {
		
	}
	public void stop() {
		
	}
	public void pause() {
		
	}
	public void resume() {
		
	}
	
}
