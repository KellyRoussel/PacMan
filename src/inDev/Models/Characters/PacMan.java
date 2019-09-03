package inDev.Models.Characters;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;

import inDev.Models.Maze;
import inDev.Views.PacManGame;

public class PacMan implements Character{

		private String direction = "Left";
		private int style = 0;
		private int counter = 0;
	    
	    private int x;
	    private int y;
	    
	    private int w;
	    private int h;
	    
	    private Image image;
	    private int nextX = 0;
	    private int nextY = 0;
	    private final int PAS = 2;
		
	    private int dx = -1 * PAS;
	    private int dy;
	    
	    private boolean undefinedPosition = true;

	    public PacMan() {
	    	ImageIcon ii = new ImageIcon("ressources" + File.separator + getDirection() + "_"+ style + ".png");
	        image = ii.getImage(); 
	 
	        setW(image.getWidth(null));
	        setH(image.getHeight(null));
	    }

	    public void nextX(){
	    	setNextX((x + dx + Maze.getSize() * 30 - 20) % (Maze.getSize() * 30 - 20));
	    }
	    
	    public void nextY() {
	    	setNextY((y + dy + Maze.getSize() * 33) % (Maze.getSize() * 33));
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
	        	dx = -1 * PAS;
	        }

	        if (key == KeyEvent.VK_RIGHT) {
	        	setDirection("Right");
	        	dy = 0;
	        	dx = PAS;
	        }

	        if (key == KeyEvent.VK_UP) {
	        	setDirection("Up");
	        	dx = 0;
	        	dy = -1 * PAS;
	        }

	        if (key == KeyEvent.VK_DOWN) {
	        	setDirection("Down");
	        	dx = 0;
	         	dy = PAS;
	        }
	    }
	    
		@Override
		public void draw(Graphics g) {
	        image = new ImageIcon("ressources" + File.separator + getDirection() + "_"+ style + ".png").getImage(); 
			g.drawImage(image, x, y, Maze.getSize() - 2, Maze.getSize() - 2, null);
			
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

		public boolean undefinedPosition() {
			if(undefinedPosition ) {
				undefinedPosition = false;
				return true;
			}
			return false;
		}

		public void setPosition(int x, int y) {
			// TODO Auto-generated method stub
			this.x = x;
			this.y = y;
		}

		public void setInsideTile(int nRaw, int nColumn) {
			// TODO Auto-generated method stub
			switch(getDirection()) {
	    		case "Left":
	    			y = nRaw * Maze.getSize() + 1;
	    			break;
	    		case "Right":
	    			y = nRaw * Maze.getSize() + 1;
	    			break;
	    		case "Up":
	    			x = nColumn * Maze.getSize() + 1;
	    			break;
	    		case "Down":
	    			x = nColumn * Maze.getSize() + 1;
	    			break;
	    		default:
	    			break;
			}
		}

	}
