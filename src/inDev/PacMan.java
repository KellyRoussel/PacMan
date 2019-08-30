package inDev;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;

public class PacMan implements Character{

		private String direction = "Left";
		private int style = 0;
		private int counter = 0;
	    private int dx;
	    private int dy;
	    private int x = 40;
	    private int y = 60;
	    private int w;
	    private int h;
	    private Image image;

	    public PacMan() {
	        loadImage();
	    }

	    private void loadImage() {
	        ImageIcon ii = new ImageIcon("ressources" + File.separator + direction + "_"+ style + ".png");
	        image = ii.getImage(); 
	        
	        w = image.getWidth(null);
	        h = image.getHeight(null);
	    }

	    public void move() {
	    	counter = (counter + 1) % 10;
	    	if(counter == 0)
	    		style = (style + 1) % 2;
	    	
	        x += dx;
	        x = (x + PacManGame.DefaultWidth) % PacManGame.DefaultWidth;
	        y += dy;
	        y = (y + PacManGame.DefaultHeight) % PacManGame.DefaultHeight;
	    }

	    public int getX() {
	        
	        return x;
	    }

	    public int getY() {
	        
	        return y;
	    }
	    
	    public int getWidth() {
	        
	        return w;
	    }
	    
	    public int getHeight() {
	        
	        return h;
	    }    

	    public Image getImage() {
	        
	        return image;
	    }

	    public void keyPressed(KeyEvent e) {

	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_LEFT) {
	        	direction = "Left";
	            dx = -4;
	        }

	        if (key == KeyEvent.VK_RIGHT) {
	        	direction = "Right";
	            dx = 4;
	        }

	        if (key == KeyEvent.VK_UP) {
	        	direction = "Up";
	        	dy = -4;
	        }

	        if (key == KeyEvent.VK_DOWN) {
	        	direction = "Down";
	            dy = 4;
	        }
	    }

	    public void keyReleased(KeyEvent e) {
	        
	        int key = e.getKeyCode();

	        if (key == KeyEvent.VK_LEFT) {
	            dx = 0;
	        }

	        if (key == KeyEvent.VK_RIGHT) {
	            dx = 0;
	        }

	        if (key == KeyEvent.VK_UP) {
	            dy = 0;
	        }

	        if (key == KeyEvent.VK_DOWN) {
	            dy = 0;
	        }
	    }

		@Override
		public void draw(Graphics g) {
	        image = new ImageIcon("ressources" + File.separator + direction + "_"+ style + ".png").getImage(); 
			g.drawImage(image, x, y, null);
			
		}

		@Override
		public void treatcollision() {
			// TODO Auto-generated method stub
			
		}

	}
