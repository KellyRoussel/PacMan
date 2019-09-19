package Models.Characters;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import Controllers.GameController;
import Models.ToSprite;

public class PacMan extends Character {

	private int direction;
	private int nextDirection;

	private boolean insideTunnel;
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

	private int nextDx;
	private int nextDy;

	private Map<Integer, Point> changes;
	private Map<Integer, Point> steps;
	private Map<Integer, Point> pacManFront;
	private Map<Integer, String> directionString;
	
	private Rectangle pacManRectangle;
	private Ellipse2D.Float pacManAdvancedFullShape;

	private boolean isDead = false;
	private boolean resurrection = false;
	private int deadAnimationCounter = 0;
	
	private int defaultSize;
	private int[][] grille;
	private ArrayList<Point> listTunnelLeft;
	private ArrayList<Point> listTunnelRight;
	private int nColumn;
	private int nRow;

	
	
	
	public PacMan(int width, int height, Image image, Point initialPosition, int defaultSize, int[][] grille, ArrayList<Point> listTunnelLeft, ArrayList<Point> listTunnelRight, int nColumn, int nRow) {
		super(width, height, image, initialPosition);

		initPM();
		
		this.defaultSize = defaultSize;
		this.grille = grille;
		this.listTunnelLeft = listTunnelLeft;
		this.listTunnelRight = listTunnelRight;
		this.nColumn = nColumn;
		this.nRow = nRow;
		
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

		setPacManFront(new HashMap<Integer, Point>());
		getPacManFront().put(KeyEvent.VK_LEFT, new Point(h / 2, 0));
		getPacManFront().put(KeyEvent.VK_RIGHT, new Point(h / 2, w));
		getPacManFront().put(KeyEvent.VK_UP, new Point(0, w / 2));
		getPacManFront().put(KeyEvent.VK_DOWN, new Point(h, w / 2));
		
		pacManRectangle = new Rectangle(position.x,position.y,width,height);
		pacManAdvancedFullShape = new Ellipse2D.Float(position.x,position.y,w,h);

	}
	
	public PacMan(int width, int height, Image image, Point initialPosition) {
		super(width, height, image, initialPosition);
	}
	
	public void returnInitialPosition() {
		position.x = initialPosition.x;
		position.y = initialPosition.y;
		
		pacManRectangle.setLocation(initialPosition.x,initialPosition.y);
		pacManAdvancedFullShape.setFrame(initialPosition.x,initialPosition.y,w,h);
	}

	public void initPM() {
		insideTunnel = false;
		counter = 0;
		direction = KeyEvent.VK_LEFT;
		nextDirection = direction;
		dx = -PAS;
		dy = 0;
		nextDx = -PAS;
		nextDy = 0;
	}
	
	public double getRectangleX(){
    	return pacManRectangle.getX();
    }
    
    public double getRectangleY(){
    	return pacManRectangle.getY();
    }
    
    public Rectangle getRectangle(){
    	return pacManRectangle;
    }
    
    public double getEllipseX(){
    	return pacManAdvancedFullShape.getX();
    }
    
    public double getEllipseY(){
    	return pacManAdvancedFullShape.getY();
    }
    
    public Ellipse2D.Float getEllipse(){
    	return pacManAdvancedFullShape;
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

	public void nextX() {
		setNextX((position.x + dx + defaultSize * nColumn - MARGE)
				% (defaultSize * nColumn - MARGE));
	}

	public void nextY() {
		setNextY((position.y + dy + defaultSize * nRow)
				% (defaultSize * nRow));
	}

	public void nextNextX() {
		setNextX((position.x + nextDx + defaultSize * nColumn - MARGE)
				% (defaultSize * nColumn - MARGE));
	}

	public void nextNextY() {
		setNextY((position.y + nextDy + defaultSize * nRow)
				% (defaultSize * nRow));
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
		if (!isDead) {
			counter = (counter + 1) % 10;
			if (counter == 0) {
				style = (style + 1) % 2;
			}
			loadImage();
			position.x = nextX;
			position.y = nextY;
			
			pacManRectangle.setLocation(nextX,nextY);
			pacManAdvancedFullShape.setFrame(nextX,nextY,w,h);
			
			if (direction == KeyEvent.VK_LEFT) {
				if (listTunnelLeft.contains(new Point(position.y / defaultSize,
						position.x / defaultSize + 1)))
					insideTunnel = true;
				else if (listTunnelRight.contains(new Point(position.y / defaultSize,
						position.x / defaultSize)))
					insideTunnel = false;
			}
			if (direction == KeyEvent.VK_RIGHT) {
				if (listTunnelRight.contains(new Point(position.y / defaultSize,
						position.x / defaultSize - 1)))
					insideTunnel = true;
				else if (listTunnelLeft.contains(new Point(position.y / defaultSize,
						position.x / defaultSize)))
					insideTunnel = false;
			}
		}
	}

	public void loadImage() {
		// TODO Auto-generated method stub
		ImageIcon ii = new ImageIcon("ressources" + File.separator + getDirectionString() + "_" + style + ".png");
		image = ii.getImage();
	}

	public void deadAnimate() {
		System.out.println("hohoo");
		// l image de l animation change apr�s 5 Frames 
		// il existe 12 images de l animation
		System.out.println("dead = " + deadAnimationCounter);
		if (getDeadAnimationCounter() < 12 * 2) {
			ToSprite pacMan_tiles_to_sprite = new ToSprite(8, "pacmanTilesSheet");
			setImage(pacMan_tiles_to_sprite.extractImage((getDeadAnimationCounter() / 2) % 8, 4 + getDeadAnimationCounter() / (8 * 2),
					"PacManDead", getDeadAnimationCounter()));
			deadAnimationCounter = getDeadAnimationCounter() + 1;
		}else {
			setIsDead(false);
			setResurrection(true);
			deadAnimationCounter = 0;
		}
		
		
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
		
		pacManRectangle.setLocation(x,y);
		pacManAdvancedFullShape.setFrame(x,y,w,h);
	}

	public void setInsideTile(int nRaw, int nColumn) {
		// POUR METTRE LE PACMAN AU MILIEU DE LA TILE DE LA LABYRINTHE
		int sz = defaultSize;
		if (changes.get(direction).x == 1)
			position.x = nColumn * sz;
		if (changes.get(direction).y == 1)
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

	public void updateDirection() {
		if (!insideTunnel) {
			direction = nextDirection;
			dx = nextDx;
			dy = nextDy;
		}
	}

	public void setNextDX(int var_nextDX) {
		nextDx = var_nextDX;
	}

	public void setNextDY(int var_nextDY) {
		nextDy = var_nextDY;
	}

	public boolean isInsideTunnel() {
		return insideTunnel;
	}

	public void setInsideTunnel(boolean insideTunnel) {
		this.insideTunnel = insideTunnel;
	}

	public boolean isDead() {
		return isDead;
	}
	
	public synchronized void setIsDead(boolean dead) {
		isDead = dead;
	}

	public boolean isResurrection() {
		return resurrection;
	}

	public void setResurrection(boolean resurrection) {
		this.resurrection = resurrection;
	}

	public Map<Integer, Point> getPacManFront() {
		return pacManFront;
	}

	public void setPacManFront(Map<Integer, Point> pacManFront) {
		this.pacManFront = pacManFront;
	}

	/**
	 * @return the deadAnimationCounter
	 */
	public int getDeadAnimationCounter() {
		return deadAnimationCounter;
	}
}