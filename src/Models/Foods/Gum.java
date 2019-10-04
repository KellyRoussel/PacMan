package Models.Foods;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.io.File;
import javax.swing.ImageIcon;


public class Gum extends Food{

	private final int GUM_GAIN = 10;
	private Rectangle foodRectangle;
	private Ellipse2D.Float foodEllipse;
	private boolean isEaten;

    public Gum(int width, int height, Image image, Point position) {
		super(width, height, image, position);
		
		foodRectangle = new Rectangle(getPosition().x,getPosition().y,width,height);
		foodEllipse = new Ellipse2D.Float(getPosition().x,getPosition().y,width,height);
		isEaten = false;
		// TODO Auto-generated constructor stub
	}

	@Override
	public synchronized void setEaten() {
		isEaten = true;
	}
	@Override
	public synchronized boolean getEaten() {
		return isEaten;
	}
	@Override
	public int getGain() {
		// TODO Auto-generated method stub
		return GUM_GAIN;
	}
	
	public double getRectangleX(){
   		return foodRectangle.getX();
    }
   
    public double getRectangleY(){
   		return foodRectangle.getY();
    }
   
    public Rectangle getRectangle(){
   		return foodRectangle;
    }
    
    public double getEllipseX(){
   		return foodEllipse.getX();
    }
   
    public double getEllipseY(){
   		return foodEllipse.getY();
    }
   
    public Ellipse2D.Float getEllipse(){
   		return foodEllipse;
    }
}
