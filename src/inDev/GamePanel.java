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


public class GamePanel extends JPanel implements ActionListener{
	
	
	
	
	private Timer timer;
    private PacMan pacMan;
    private final int DELAY = 10;

    public GamePanel() {
        initPanel();
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
        
    	pacMan.move();
        
        repaint(pacMan.getX()-1, pacMan.getY()-1, 
        		pacMan.getWidth()+2, pacMan.getHeight()+2);     
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
