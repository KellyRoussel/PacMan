package Models.Characters;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import Models.Maze;

public class PacMan implements Character{

		private int direction;
		private int nextDirection;

		

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
	    
	    private Map<Integer, Point> changes;
	    private Map<Integer, Point> steps;
	    public Map<Integer, Point> pacManFront;
	    public Map<Integer, String> directionString;

	    public PacMan() {
	    	nextX = 0; 
	    	nextY = 0;
	    	counter = 0;
	    	style = 0;
	    	undefinedPosition = true;
	    	direction = KeyEvent.VK_LEFT;
	    	nextDirection = direction;
	    	dx = -PAS;
	    	dy = 0;
	    	
	        directionString = new HashMap<Integer, String>(); 
	        directionString.put(KeyEvent.VK_LEFT, "Left");
	        directionString.put(KeyEvent.VK_RIGHT, "Right");	        
	        directionString.put(KeyEvent.VK_UP, "Up");	        
	        directionString.put(KeyEvent.VK_DOWN, "Down");	
	        
	    	ImageIcon ii = new ImageIcon("ressources" + File.separator + getDirectionString() + "_"+ style + ".png");
	        image = ii.getImage(); 
	    	w = image.getWidth(null);
	        h = image.getHeight(null);
	    		        
	        changes = new HashMap<Integer, Point>();
	        changes.put(KeyEvent.VK_LEFT, new Point(0, 1));
	        changes.put(KeyEvent.VK_RIGHT, new Point(0, 1));
	        changes.put(KeyEvent.VK_UP, new Point(1, 0));
	        changes.put(KeyEvent.VK_DOWN, new Point(1, 0));
	        
	        steps = new HashMap<Integer, Point>();
	        steps.put(KeyEvent.VK_LEFT, new Point(-PAS, 0));
	        steps.put(KeyEvent.VK_RIGHT, new Point(PAS, 0));
	        steps.put(KeyEvent.VK_UP, new Point(0, -PAS));
	        steps.put(KeyEvent.VK_DOWN, new Point(0, PAS));

	        pacManFront = new HashMap<Integer, Point>();
	        pacManFront.put(KeyEvent.VK_LEFT, new Point(h/2, 0));
	        pacManFront.put(KeyEvent.VK_RIGHT, new Point(h/2, w));	        
	        pacManFront.put(KeyEvent.VK_UP, new Point(0, w/2));	        
	        pacManFront.put(KeyEvent.VK_DOWN, new Point(h, w/2));	   
	        
	    }
	    
	   public String getDirectionString() {
			return directionString.get(direction);
		}

		public int getNextDx() {
			return nextDx;
		}

	    public int getNextDy() {
			return nextDy;
		}

	    public int getPas() {
			return PAS;
		}

	    public void nextX(){
	    	setNextX((x + dx + Maze.getDefaultSize() * Maze.getnColumn() - 10) % (Maze.getDefaultSize() * Maze.getnColumn() - 10));
	    }
	    
	    public void nextY() {
	    	setNextY((y + dy + Maze.getDefaultSize() * Maze.getnRaw()) % (Maze.getDefaultSize() * Maze.getnRaw()));
	    }
	    
	    public void nextNextX(){
	    	setNextX((x + nextDx + Maze.getDefaultSize() * Maze.getnColumn() - 10) % (Maze.getDefaultSize() * Maze.getnColumn() - 10));
	    }
	    
	    public void nextNextY() {
	    	setNextY((y + nextDy + Maze.getDefaultSize() * Maze.getnRaw()) % (Maze.getDefaultSize() * Maze.getnRaw()));
	    }
	    
	    public int getNextDirection() {
			return nextDirection;
		}

		public void setNextDirection(int nextDirection) {
			
			this.nextDirection = nextDirection;
			nextDx = steps.get(nextDirection).x;
			nextDy = steps.get(nextDirection).y;
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
	    
	    public void setX(int var_x) {
	        
	        x = var_x;
	    }

	    public int getY() {
	        
	        return y;
	    }
	    
	    public void setY(int var_y) {
	        
	        y = var_y;
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
	    
		@Override
		public void draw(Graphics g, int x, int y, int mazeSize) {
	        image = new ImageIcon("ressources" + File.separator + getDirectionString() + "_"+ style + ".png").getImage(); 
			
	        //g.drawImage(image, GamePanel.debutX + shiftedX(x, Maze.getSize()), GamePanel.debutY + shiftedX(y, Maze.getSize()), Maze.getSize(), Maze.getSize(), null);
	        g.drawImage(image, x, y, mazeSize, mazeSize, null);
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

		public void setW(int w) {
			this.w = w;
		}

		public int getH() {
			return h;
		}

		public void setH(int h) {
			this.h = h;
		}

		public int getDirection() {
			return direction;
		}

		public void setDirection(int direction) {
			this.direction = direction;
		}
		
		public void setUndefinedPosition(boolean Position) {
			undefinedPosition = Position;
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
			if(changes.get(direction).x == 1)
				x = nColumn * sz;
			if(changes.get(direction).y == 1)
				y = nRaw * sz;

		}
		
		public int getDX() {
			return dx;
		}
		
		public int getDY() {
			return dy;
		}
		
		public void setDX(int var_dx) {
			dx = var_dx;
		}
		
		public void setDY(int var_dy) {
			dy = var_dy;
		}

		public void updateDirection() {
			direction = nextDirection;
			dx = nextDx;
			dy = nextDy;
		}
		
		public void setNextDX(int var_nextDX) {
			nextDx = var_nextDX;
		}
		
		public void setNextDY(int var_nextDY) {
			nextDy = var_nextDY;
		}
	}