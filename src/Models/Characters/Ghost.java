package Models.Characters;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.ImageIcon;

import Controllers.GameController;
import Models.Characters.Strategies.GhostStrategy;
import Models.Characters.Strategies.StrBlue;

public class Ghost  extends Character{
	

	private boolean state = true;
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
    
    private final int PAS = 4;
	private final int MARGE = 10;
    
	private int dx;
    private int dy;
    
	private AtomicBoolean isEaten;

        	    
    private Map<Integer, Point> changes;
    private Map<Integer, Point> steps;
    private Map<Integer, Point> ghostFront;
    private Map<Integer, String> directionString;
    private Map<Integer, Integer> oppositeDirection;
    
    private Rectangle ghostRectangle;
    private Rectangle ghostAdvancedLowerShape;
    private Arc2D.Float ghostAdvancedTopShape;
    private int availableDirections;
	private int defaultSize;
	private int[][] grille;
	private ArrayList<Point> listTunnelLeft;
	private ArrayList<Point> listTunnelRight;
	private int nColumn;
	private int nRow;
	private GhostStrategy ghostStrategy;
	private GhostStrategy normalStrategy;
    
	public Ghost(int width, int height, Image image, Point initialPosition, String color, int defaultSize, int[][] grille, ArrayList<Point> listTunnelLeft, ArrayList<Point> listTunnelRight, int nColumn, int nRow, GhostStrategy ghostStrategy) {
		super(width, height, image, initialPosition);
		// TODO Auto-generated constructor stub
		
		this.ghostStrategy = ghostStrategy;
		ghostStrategy.setGhost(this);
		this.normalStrategy = ghostStrategy;
		this.defaultSize = defaultSize;
		this.grille = grille;
		this.listTunnelLeft = listTunnelLeft;
		this.listTunnelRight = listTunnelRight;
		this.nColumn = nColumn;
		this.nRow = nRow;
		
		counter = 0;
    	direction = KeyEvent.VK_LEFT;
    	dx = -PAS;
    	dy = 0;
    	
    	isEaten = new AtomicBoolean(false);
    	this.color = color;
        
    	directionString = new HashMap<Integer, String>(); 
        directionString.put(KeyEvent.VK_LEFT, "left");
        directionString.put(KeyEvent.VK_RIGHT, "right");	        
        directionString.put(KeyEvent.VK_UP, "up");	        
        directionString.put(KeyEvent.VK_DOWN, "down");	
        
        oppositeDirection = new HashMap<Integer, Integer>(); 
        oppositeDirection.put(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
        oppositeDirection.put(KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT);	        
        oppositeDirection.put(KeyEvent.VK_UP, KeyEvent.VK_DOWN);	        
        oppositeDirection.put(KeyEvent.VK_DOWN, KeyEvent.VK_UP);	
        
        
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
        
        ghostRectangle = new Rectangle(getPosition().x,getPosition().y,width,height);
        ghostAdvancedLowerShape = new Rectangle(getPosition().x,getPosition().y + (height/2),width,height/2);
        ghostAdvancedTopShape = new Arc2D.Float(getPosition().x,getPosition().y,width,height/2, 0, 180, Arc2D.CHORD);
        
        
        try {
        	availableDirections = getUpdatedAvailableDirections();
        	setRandomDirection();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void returnInitialPosition() {
		getPosition().x = initialPosition.x;
		getPosition().y = initialPosition.y;
		
		ghostRectangle.setLocation(initialPosition.x,initialPosition.y);
		ghostAdvancedLowerShape.setLocation(initialPosition.x,initialPosition.y + (h/2));
		ghostAdvancedTopShape.setFrame(initialPosition.x,initialPosition.y,w,h/2);
	}

	public double getRectangleX(){
   		return ghostRectangle.getX();
    }
   
    public double getRectangleY(){
   		return ghostRectangle.getY();
    }
    
    public void setRectangleX(int x){
    	ghostRectangle.x = x;
    }
   
    public void setRectangleY(int y){
   		ghostRectangle.y = y;
    }
   
    public Rectangle getRectangle(){
   		return ghostRectangle;
    }
    
    public void updatePosition() {
    	ghostStrategy.updatePosition();
	}
    
    public double getAdvancedLowerRectangleX(){
   		return ghostAdvancedLowerShape.getX();
    }
   
    public double getAdvancedLowerRectangleY(){
   		return ghostAdvancedLowerShape.getY();
    }
    
    public void setAdvancedLowerRectangleX(int x){
   		ghostAdvancedLowerShape.x = x;
    }
   
    public void setAdvancedLowerRectangleY(int y){
   		ghostAdvancedLowerShape.y = y;
    }
   
    public Rectangle getAdvancedLowerRectangle(){
   		return ghostAdvancedLowerShape;
    }
    
    public double getAdvancedTopArcX(){
    	return ghostAdvancedTopShape.getX();
    }
    
    public double getAdvancedTopArcY(){
    	return ghostAdvancedTopShape.getY();
    }
    
    public void setAdvancedTopArcX(int x){
    	ghostAdvancedTopShape.x = x;
    }
    
    public void setAdvancedTopArcY(int y){
    	ghostAdvancedTopShape.y = y;
    }
    
    public Arc2D.Float getAdvancedTopArc(){
    	return ghostAdvancedTopShape;
    }
    
   public int getAvailableDirections() {
		return availableDirections;
	}

	public void setAvailableDirections(int availableDirections) {
		this.availableDirections = availableDirections;
	}

	public int getUpdatedAvailableDirections() {
					
		   int raw = getPosition().y / defaultSize;
		   int column = getPosition().x / defaultSize;
		   
		   if(raw < 0 || raw >= nRow || column < 0 || column >= nColumn)
			   return 0;


		   int counter = 0;
		   //UP
		   if(raw - 1 >= 0 && 
				   (grille[raw - 1][column] > 25 || 
						   grille[raw - 1][column] < 1 ||  
						   grille[raw - 1][column] == 2 || 
						   grille[raw - 1][column] == 15))
			   counter += KeyEvent.VK_UP;
		   //DOWN
		   if(raw + 1 < nRow && 
				   (grille[raw + 1][column] > 25 || grille[raw + 1][column] < 1))
			   counter += KeyEvent.VK_DOWN;
		   
		   //LEFT
		   if(!listTunnelLeft.contains(new Point(raw, column)) &&
				   column - 1 >= 0 &&
				   (grille[raw][column - 1] > 25 ||
						   grille[raw][column - 1] < 1))
			   counter += KeyEvent.VK_LEFT;
		   //DOWN
		   if(column + 1 < nColumn &&
				   !listTunnelRight.contains(new Point(raw, column)) &&
				   (grille[raw][column + 1] > 25 || 
						   grille[raw][column + 1] < 1))
				   counter += KeyEvent.VK_RIGHT;
		   
		   return counter;
	}
	
	
	
	public String getDirectionString() {
		return directionString.get(direction);
	}


    public int getPas() {
		return PAS;
	}

    public void nextX(){
    	setNextX((getPosition().x + dx + defaultSize * nColumn - MARGE) % (defaultSize * nColumn - MARGE));
    }
    
    public void nextY() {
    	setNextY((getPosition().y + dy + defaultSize * nRow) % (defaultSize * nRow));
    }
    

 
   
    public void move() {
    	counter = (counter + 1) % 10;
    	if(counter == 0) {
    		style = (style + 1) % 2;
    		loadImage();
    	}
    	
    	getPosition().x += dx;
    	getPosition().y += dy;
    	setInsideTile(getPosition().y / defaultSize, getPosition().x / defaultSize);
    	
    	ghostRectangle.setLocation(getPosition().x,getPosition().y);
    	ghostAdvancedLowerShape.setLocation(getPosition().x,getPosition().y);
		ghostAdvancedTopShape.setFrame(getPosition().x,getPosition().y,w,h/2);
    }

    public void loadImage() {
    	ghostStrategy.loadImage();
	}

	public int getX() {
        
        return getPosition().x;
    }
    
    public void setX(int var_x) {
        
    	getPosition().x = var_x;
    }

    public int getY() {
        
        return getPosition().y;
    }
    
    public void setY(int var_y) {
        
    	getPosition().y = var_y;
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
		dx = steps.get(direction).x;
		dy = steps.get(direction).y;
	}
	
	public void setPosition(int x, int y) {
		// TODO Auto-generated method stub
		getPosition().x = x;
		getPosition().y = y;
		
		ghostRectangle.setLocation(x,y);
		ghostAdvancedLowerShape.setLocation(x,y+(h/2));
		ghostAdvancedTopShape.setFrame(x,y,w,h/2);
	}

	
	public void setInsideTile(int nRaw, int nColumn) {
		// POUR METTRE LE PACMAN AU MILIEU DE LA TILE DE LA LABYRINTHE
		int sz = defaultSize;
		if(changes.get(direction).x == 1)
			getPosition().x = nColumn * sz;
		if(changes.get(direction).y == 1)
			getPosition().y = nRaw * sz;
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
		int raw = getPosition().y / defaultSize;
		int column = getPosition().x / defaultSize;
		
		if(raw - 1 >= 0 && 
				(grille[raw - 1][column] > 25 || grille[raw - 1][column] < 1 ||  grille[raw - 1][column] == 2 || grille[raw - 1][column] == 15) && KeyEvent.VK_DOWN != direction)
			availables.add(KeyEvent.VK_UP);
		
		if(raw + 1 < nRow && (grille[raw + 1][column] > 25 || grille[raw + 1][column] < 1) && KeyEvent.VK_UP != direction)
			availables.add(KeyEvent.VK_DOWN);
		
		if(column - 1 >= 0 && (grille[raw][column - 1] > 25 || grille[raw][column - 1] < 1) && KeyEvent.VK_RIGHT != direction && !listTunnelLeft.contains(new Point(raw, column))) 
			availables.add(KeyEvent.VK_LEFT);
		
		if(column + 1 < nColumn && (grille[raw][column + 1] > 25 || grille[raw][column + 1] < 1) && KeyEvent.VK_LEFT != direction && !listTunnelRight.contains(new Point(raw, column))) {
			availables.add(KeyEvent.VK_RIGHT);
		}
			
		
		if(availables.size() == 0) {
			if(raw - 1 >= 0 && 
					(grille[raw - 1][column] > 25 || grille[raw - 1][column] < 1 ||  grille[raw - 1][column] == 2 || grille[raw - 1][column] == 15))
				availables.add(KeyEvent.VK_UP);
			
			if(raw + 1 < nRow && (grille[raw + 1][column] > 25 || grille[raw + 1][column] < 1))
				availables.add(KeyEvent.VK_DOWN);
			
			if(column - 1 >= 0 && (grille[raw][column - 1] > 25 || grille[raw][column - 1] < 1) && !listTunnelLeft.contains(new Point(raw, column))) 
				availables.add(KeyEvent.VK_LEFT);
			
			if(column + 1 < nColumn && (grille[raw][column + 1] > 25 || grille[raw][column + 1] < 1) && !listTunnelRight.contains(new Point(raw, column))) {
				availables.add(KeyEvent.VK_RIGHT);
			}
		}
		
		setDirection(availables.get((int)(Math.random() * availables.size())));
		
	}



	public boolean isOutside() {
		return outside;
	}



	public void setOutside(boolean outside) {
		this.outside = outside;
	}

	public void setOppositeDirection(int direction) {
		// TODO Auto-generated method stub
		int dir = oppositeDirection.get(direction);
		setDirection(dir);
	}
	public boolean canMove(int direction) {
		int x = position.x + steps.get(direction).x;
		int y = position.y + steps.get(direction).y;
		
	   int raw = y / defaultSize;
	   int column = x / defaultSize;
	   
	   if(raw >= 0 && raw < nRow && column >= 0 && column < nColumn && (grille[raw][column] <= 0 || grille[raw][column] > 25))
		   return true;

	   return false;
	}
	
	

	public ArrayList<Point> getListTunnelLeft() {
		return listTunnelLeft;
	}

	public void setListTunnelLeft(ArrayList<Point> listTunnelLeft) {
		this.listTunnelLeft = listTunnelLeft;
	}

	public ArrayList<Point> getListTunnelRight() {
		return listTunnelRight;
	}

	public void setListTunnelRight(ArrayList<Point> listTunnelRight) {
		this.listTunnelRight = listTunnelRight;
	}

	public int getOppositeDirection(int direction2) {
		// TODO Auto-generated method stub
		return oppositeDirection.get(direction2);
	}

	public void setStrategy(GhostStrategy normalStrategy2) {
		// TODO Auto-generated method stub
		this.ghostStrategy = normalStrategy2;
		this.ghostStrategy.setGhost(this);
		this.setEaten(false);
	}

	public void setNormalStrategy() {
		// TODO Auto-generated method stub
		setStrategy(normalStrategy);
	}
	
	public void setEaten(boolean eaten) {
		synchronized (isEaten) {
			isEaten = new AtomicBoolean(eaten);
			ghostStrategy.loadImage();
		}
		if(eaten) {
			returnInitialPosition();
		}
	}
		
	public boolean isEaten() {
		// TODO Auto-generated method stub
		synchronized (isEaten) {
			return isEaten.get();			
		}
	}
}