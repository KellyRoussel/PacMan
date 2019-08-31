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
	    private int dx = -4;
	    private int dy;
	    private int x = 250;
	    private int y = 425;
	    private int w;
	    private int h;
	    private Image image;
	    private int nextX = 0;
	    private int nextY = 0;

	    public PacMan() {
	        loadImage();
	    }

	    private void loadImage() {
	        ImageIcon ii = new ImageIcon("ressources" + File.separator + getDirection() + "_"+ style + ".png");
	        image = ii.getImage(); 
	 
	        setW(image.getWidth(null));
	        setH(image.getHeight(null));
	    }

	    public void nextX(){
	    	setNextX((x+dx + PacManGame.DefaultWidth) % PacManGame.DefaultWidth);
	    }
	    
	    public void nextY() {
	    	setNextY((y+dy + PacManGame.DefaultHeight) % PacManGame.DefaultHeight);
	    }
	    
	    public void move() {
	    	counter = (counter + 1) % 10;
	    	if(counter == 0)
	    		style = (style + 1) % 2;
	    	
	        x = nextX;
	        y = nextY;
	    }

	    public int getX() {
	        
	        return x;
	    }

	    public int getY() {
	        
	        return y;
	    }
	    
	    public int getWidth() {
	        
	        return getW();
	    }
	    
	    public int getHeight() {
	        
	        return getH();
	    }    

	    public Image getImage() {
	        
	        return image;
	    }

	    public void keyPressed(KeyEvent e) {

	        int key = e.getKeyCode();
	        
	        if (key == KeyEvent.VK_LEFT) {
	        	setDirection("Left");
			dy = 0;
	        	dx = -4;
	        }

	        if (key == KeyEvent.VK_RIGHT) {
	        	setDirection("Right");
			dy = 0;
	        	dx = 4;
	        }

	        if (key == KeyEvent.VK_UP) {
	        	setDirection("Up");
			dx = 0;
	        	dy = -4;
	        }

	        if (key == KeyEvent.VK_DOWN) {
	        	setDirection("Down");
			dx = 0;
	         	dy = 4;
	        }
	    }
	    
/*
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
*/
		@Override
		public void draw(Graphics g) {
	        image = new ImageIcon("ressources" + File.separator + getDirection() + "_"+ style + ".png").getImage(); 
			g.drawImage(image, x, y, null);
			
		}

		@Override
		public void treatcollision() {
			// TODO Auto-generated method stub
			
		}

		public int getNextX() {
			return nextX;
		}

		public void setNextX(int nextX) {
			this.nextX = nextX;
		}

		public int getNextY() {
			return nextY;
		}

		public void setNextY(int nextY) {
			this.nextY = nextY;
		}

		public int getW() {
			return w;
		}

		private void setW(int w) {
			this.w = w;
		}

		public int getH() {
			return h;
		}

		private void setH(int h) {
			this.h = h;
		}

		public String getDirection() {
			return direction;
		}

		private void setDirection(String direction) {
			this.direction = direction;
		}

	}
