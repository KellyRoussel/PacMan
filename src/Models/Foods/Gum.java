package Models.Foods;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.ImageIcon;


public class Gum extends Food{

	private final int GUM_GAIN = 10;
	private Rectangle gumRectangle;

    public Gum(int width, int height, Image image, Point position) {
		super(width, height, image, position);
		
		gumRectangle = new Rectangle(getPosition().x,getPosition().y,width,height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setEaten() {
		// TODO Auto-generated method stub
	}

	@Override
	public int getGain() {
		// TODO Auto-generated method stub
		return GUM_GAIN;
	}
	
	public double getRectangleX(){
   		return gumRectangle.getX();
    }
   
    public double getRectangleY(){
   		return gumRectangle.getY();
    }
   
    public Rectangle getRectangle(){
   		return gumRectangle;
    }
}
