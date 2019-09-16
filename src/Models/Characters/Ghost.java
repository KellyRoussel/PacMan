package Models.Characters;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Arc2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import Controllers.GameController;
import Models.Characters.Strategies.GhostStrategy;

public class Ghost extends Character{
	

	public static boolean state = true;
	private String color;
	private GhostStrategy stategy;
	
	private int direction;	
	
	private boolean outside = false;

	private int style;
	private int counter;
    
    private int w;
    private int h;
    
    
    private int nextX;
    private int nextY;
    
    private final int PAS = 2;
	private final int MARGE = 10;
    
	private int dx;
    private int dy;
        	    
    private Map<Integer, Point> changes;
    private Map<Integer, Point> steps;
    public Map<Integer, Point> ghostFront;
    public Map<Integer, String> directionString;
    
    private Rectangle ghostRectangle;
    private Rectangle pacManAdvancedLowerShape;
    private Arc2D.Float pacManAdvancedTopShape;
    
    private int availableDirections;
    
	public Ghost(int width, int height, Image image, Point initialPosition, String color) {
		super(width, height, image, initialPosition);
		// TODO Auto-generated constructor stub
		counter = 0;
    	direction = KeyEvent.VK_LEFT;
    	dx = -PAS;
    	dy = 0;
    	
    	
    	this.color = color;
        
    	directionString = new HashMap<Integer, String>(); 
        directionString.put(KeyEvent.VK_LEFT, "Left");
        directionString.put(KeyEvent.VK_RIGHT, "Right");	        
        directionString.put(KeyEvent.VK_UP, "Up");	        
        directionString.put(KeyEvent.VK_DOWN, "Down");	
        
    	
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

        ghostFront = new HashMap<Integer, Point>();
        ghostFront.put(KeyEvent.VK_LEFT, new Point(h/2, 0));
        ghostFront.put(KeyEvent.VK_RIGHT, new Point(h/2, w));	        
        ghostFront.put(KeyEvent.VK_UP, new Point(0, w/2));	        
        ghostFront.put(KeyEvent.VK_DOWN, new Point(h, w/2));	
        
        ghostRectangle = new Rectangle(position.x,position.y,width,height);
        
        pacManAdvancedLowerShape = new Rectangle(position.x,position.y + height/2,width,height/2);
        pacManAdvancedTopShape = new Arc2D.Float(position.x,position.y,width,height/2, 0, 180, Arc2D.CHORD);
        
        availableDirections = getUpdatedAvailableDirections();
    	setRandomDirection();
	}
	
	public void returnInitialPosition() {
		position.x = initialPosition.x;
		position.y = initialPosition.y;
		
		ghostRectangle.setLocation(initialPosition.x,initialPosition.y);
	}

	public double getRectangleX(){
   		return ghostRectangle.getX();
    }
   
    public double getRectangleY(){
   		return ghostRectangle.getY();
    }
   
    public Rectangle getRectangle(){
   		return ghostRectangle;
    }
    
   public int getAvailableDirections() {
		return availableDirections;
	}

	public void setAvailableDirections(int availableDirections) {
		this.availableDirections = availableDirections;
	}

	public int getUpdatedAvailableDirections() {
		
			
		   int raw = position.y / GameController.getDefaultSize();
		   int column = position.x / GameController.getDefaultSize();
		   int counter = 0;
		   counter += (GameController.getGrille()[raw - 1][column] > 25 || GameController.getGrille()[raw - 1][column] < 1 ||  GameController.getGrille()[raw - 1][column] == 2 || GameController.getGrille()[raw - 1][column] == 15)? KeyEvent.VK_UP : 0;
		   counter += (GameController.getGrille()[raw + 1][column] <= 25 && GameController.getGrille()[raw + 1][column] >= 1)? 0 : KeyEvent.VK_DOWN;
		   counter += (GameController.listTunnelLeft.contains(new Point(raw, column)) || (GameController.getGrille()[raw][column - 1] <= 25 && GameController.getGrille()[raw][column - 1] >= 1))? 0 : KeyEvent.VK_LEFT;
		   counter += (column + 1 >= 30 || GameController.listTunnelRight.contains(new Point(raw, column)) || (GameController.getGrille()[raw][column + 1] <= 25 && GameController.getGrille()[raw][column + 1] >= 1))? 0 : KeyEvent.VK_RIGHT;
		   return counter;
		}
	
	
	
	public String getDirectionString() {
		return directionString.get(direction);
	}


    public int getPas() {
		return PAS;
	}

    public void nextX(){
    	setNextX((position.x + dx + GameController.getDefaultSize() * GameController.getnColumn() - MARGE) % (GameController.getDefaultSize() * GameController.getnColumn() - MARGE));
    }
    
    public void nextY() {
    	setNextY((position.y + dy + GameController.getDefaultSize() * GameController.getnRow()) % (GameController.getDefaultSize() * GameController.getnRow()));
    }
    
 
   
    public void move() {
    	counter = (counter + 1) % 10;
    	if(counter == 0) {
    		style = (style + 1) % 2;
    		loadImage();
    	}
    	position.x += dx;
    	position.y += dy;
    	setInsideTile(position.y / GameController.getDefaultSize(), position.x / GameController.getDefaultSize());
    	
    	ghostRectangle.setLocation(position.x,position.y);
    }

    private void loadImage() {
		// TODO Auto-generated method stub
    	ImageIcon ii = new ImageIcon("ressources" + File.separator + "ghost" + color+ ".png");
    	image = ii.getImage();
	}

	public int getX() {
        
        return position.x;
    }
    
    public void setX(int var_x) {
        
    	position.x = var_x;
    }

    public int getY() {
        
        return position.y;
    }
    
    public void setY(int var_y) {
        
    	position.y = var_y;
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
	
	public void setPosition(int x, int y) {
		// TODO Auto-generated method stub
		position.x = x;
		position.y = y;
		
		ghostRectangle.setLocation(x,y);
	}

	
	public void setInsideTile(int nRaw, int nColumn) {
		// POUR METTRE LE PACMAN AU MILIEU DE LA TILE DE LA LABYRINTHE
		int sz = GameController.getDefaultSize();
		if(changes.get(direction).x == 1)
			position.x = nColumn * sz;
		if(changes.get(direction).y == 1)
			position.y = nRaw * sz;
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



	public void setRandomDirection() {
		// TODO Auto-generated method stub
		List<Integer> availables = new ArrayList<>();
		int raw = position.y / GameController.getDefaultSize();
		int column = position.x / GameController.getDefaultSize();
		
		if((GameController.getGrille()[raw - 1][column] > 25 || GameController.getGrille()[raw - 1][column] < 1 ||  GameController.getGrille()[raw - 1][column] == 2 || GameController.getGrille()[raw - 1][column] == 15) && KeyEvent.VK_DOWN != direction)
			availables.add(KeyEvent.VK_UP);
		
		if((GameController.getGrille()[raw + 1][column] > 25 || GameController.getGrille()[raw + 1][column] < 1) && KeyEvent.VK_UP != direction)
			availables.add(KeyEvent.VK_DOWN);
		
		if(column - 1 >= 0 && (GameController.getGrille()[raw][column - 1] > 25 || GameController.getGrille()[raw][column - 1] < 1) && KeyEvent.VK_RIGHT != direction && !GameController.listTunnelLeft.contains(new Point(raw, column))) 
			availables.add(KeyEvent.VK_LEFT);
		
		if(column + 1 < GameController.getnColumn() && (GameController.getGrille()[raw][column + 1] > 25 || GameController.getGrille()[raw][column + 1] < 1) && KeyEvent.VK_LEFT != direction && !GameController.listTunnelRight.contains(new Point(raw, column))) {
			availables.add(KeyEvent.VK_RIGHT);
		}
			
		
		if(availables.size() == 0) {
			if((GameController.getGrille()[raw - 1][column] > 25 || GameController.getGrille()[raw - 1][column] < 1 ||  GameController.getGrille()[raw - 1][column] == 2 || GameController.getGrille()[raw - 1][column] == 15))
				availables.add(KeyEvent.VK_UP);
			
			if((GameController.getGrille()[raw + 1][column] > 25 || GameController.getGrille()[raw + 1][column] < 1))
				availables.add(KeyEvent.VK_DOWN);
			
			if(column - 1 >= 0 && (GameController.getGrille()[raw][column - 1] > 25 || GameController.getGrille()[raw][column - 1] < 1) && !GameController.listTunnelLeft.contains(new Point(raw, column)))
				availables.add(KeyEvent.VK_LEFT);
			
			if(column + 1 < GameController.getnColumn() && (GameController.getGrille()[raw][column + 1] > 25 || GameController.getGrille()[raw][column + 1] < 1) && !GameController.listTunnelRight.contains(new Point(raw, column)))
				availables.add(KeyEvent.VK_RIGHT);
		}
		
		direction = availables.get((int)(Math.random() * availables.size()));
		dx = steps.get(direction).x;
		dy = steps.get(direction).y;
	}



	public boolean isOutside() {
		return outside;
	}



	public void setOutside(boolean outside) {
		this.outside = outside;
	}
}