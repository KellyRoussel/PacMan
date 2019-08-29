package inDev;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import sun.java2d.loops.DrawRect;

public class GamePanel extends JPanel implements KeyListener{
	
	private static final int RECT_X = 20;
	   private static final int RECT_Y = RECT_X;
	   private static final int RECT_WIDTH = 100;
	   private static final int RECT_HEIGHT = RECT_WIDTH;

	   @Override
	   protected void paintComponent(Graphics g) {
	      super.paintComponent(g);
	      // draw the rectangle here
	      g.drawRect(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT);
	   }

	   @Override
	   public Dimension getPreferredSize() {
	      // so that our GUI is big enough
	      return new Dimension(RECT_WIDTH + 2 * RECT_X, RECT_HEIGHT + 2 * RECT_Y);
	   }

	   // create the GUI explicitly on the Swing event thread
	   private static void createAndShowGui() {
	      GamePanel mainPanel = new GamePanel();

	      JFrame frame = new JFrame("DrawRect");
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.getContentPane().add(mainPanel);
	      frame.pack();
	      frame.setLocationByPlatform(true);
	      frame.setVisible(true);
	   }


	public GamePanel() {
		SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
	            createAndShowGui();
	         }
	      });
	}
	
	public void startGame() {
		
	}
	public void stop() {
		
	}
	public void pause() {
		
	}
	public void resume() {
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("Right key pressed");
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("Left key pressed");
        }
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("Right key realeased");
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("Left key realeased");
        }
		
	}
}
