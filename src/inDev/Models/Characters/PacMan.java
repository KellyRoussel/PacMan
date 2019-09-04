package inDev.Models.Characters;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;

import inDev.Models.Maze;
import inDev.Views.GamePanel;
import inDev.Views.PacManGame;

public class PacMan implements Character{

		private String direction;
		private String nextDirection;

		

		private int style;
		private int counter;
	    
	    private int x;
	    private int y;
	    
	    private int w;
	    private int h;
	    
	    private Image image;
	    
	    private int nextX;
	    private int nextY;
	    
	    private final int PAS = 2;
		
	    private int dx;
	    private int dy;
	    
	    private int nextDx;
	    private int nextDy;
	    
	    private boolean undefinedPosition;

	    public PacMan() {
	    	nextX = 0; 
	    	nextY = 0;
	    	counter = 0;
	    	style = 0;
	    	undefinedPosition = true;
	    	direction = "Left";
	    	nextDirection = "Left";
	    	dx = -PAS;
	    	dy = 0;
	    	ImageIcon ii = new ImageIcon("ressources" + File.separator + getDirection() + "_"+ style + ".png");
	        image = ii.getImage(); 
	 
	        setW(image.getWidth(null));
	        setH(image.getHeight(null));
	    }

	    public void nextX(){
	    	setNextX((x + dx + Maze.getDefaultSize() * 30 - 20) % (Maze.getDefaultSize() * 30 - 20));
	    }
	    
	    public void nextY() {
	    	setNextY((y + dy + Maze.getDefaultSize() * 33) % (Maze.getDefaultSize() * 33));
	    }
	    
	    public void nextNextX(){
	    	setNextX((x + nextDx + Maze.getDefaultSize() * 30 - 20) % (Maze.getDefaultSize() * 30 - 20));
	    }
	    
	    public void nextNextY() {
	    	setNextY((y + nextDy + Maze.getDefaultSize() * 33) % (Maze.getDefaultSize() * 33));
	    }
	    
	    public String getNextDirection() {
			return nextDirection;
		}

		public void setNextDirection(String nextDirection) {
			
			this.nextDirection = nextDirection;
			if (nextDirection == "Left") {
	        	nextDy = 0;
	        	nextDx = -1 * PAS;
	        }

			if (nextDirection == "Right") {
				nextDy = 0;
				nextDx = PAS;
	        }

			if (nextDirection == "Up") {
				nextDx = 0;
	        	nextDy = -1 * PAS;
	        }

			if (nextDirection == "Down") {
				nextDx = 0;
	        	nextDy = PAS;
	        }
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
	        System.out.println("DONE");
	        if (key == KeyEvent.VK_LEFT) {
	        	setNextDirection("Left");
	        }

	        if (key == KeyEvent.VK_RIGHT) {
	        	setNextDirection("Right");
	        }

	        if (key == KeyEvent.VK_UP) {
	        	setNextDirection("Up");
	        }

	        if (key == KeyEvent.VK_DOWN) {
	        	setNextDirection("Down");
	        }
	    }
	    public int shiftedX(int x, int size) {
	    	int nColumn = (x / Maze.getDefaultSize());
	    	int nLeft = (x % Maze.getDefaultSize());
	    	nLeft = nLeft * size / Maze.getDefaultSize();
	    	nLeft += nColumn * size;
	    	return nLeft;
	    }
		@Override
		public void draw(Graphics g) {
	        image = new ImageIcon("ressources" + File.separator + getDirection() + "_"+ style + ".png").getImage(); 
			g.drawImage(image, GamePanel.debutX + shiftedX(x, Maze.getSize()), GamePanel.debutY + shiftedX(y, Maze.getSize()), Maze.getSize(), Maze.getSize(), null);
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
			int sz = Maze.getDefaultSize();
			switch(getDirection()) {
	    		case "Left":
	    			y = nRaw * sz;
	    			break;
	    		case "Right":
	    			y = nRaw * sz;
	    			break;
	    		case "Up":
	    			x = nColumn * sz;
	    			break;
	    		case "Down":
	    			x = nColumn * sz;
	    			break;
	    		default:
	    			break;
			}
		}

		public void updateDirection() {
			direction = nextDirection;
			dx = nextDx;
			dy = nextDy;
		}

	}
