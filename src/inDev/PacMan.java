package inDev;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;

public class PacMan implements Character{

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
	        
	        ImageIcon ii = new ImageIcon("ressources"+File.separator+"cr.png");
	        image = ii.getImage(); 
	        
	        w = image.getWidth(null);
	        h = image.getHeight(null);
	    }

	    public void move() {
	        
	        x += dx;
	        y += dy;
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
	            dx = -2;
	        }

	        if (key == KeyEvent.VK_RIGHT) {
	            dx = 2;
	        }

	        if (key == KeyEvent.VK_UP) {
	            dy = -2;
	        }

	        if (key == KeyEvent.VK_DOWN) {
	            dy = 2;
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
			g.drawImage(image, x, y, null);
			// TODO Auto-generated method stub
			
		}

		@Override
		public void treatcollision() {
			// TODO Auto-generated method stub
			
		}

	}
