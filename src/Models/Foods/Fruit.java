package Models.Foods;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.ImageIcon;
import Models.Maze;

public class Fruit extends Food{

	private final int FRUIT_GAIN = 100;
	private Rectangle fruitRectangle;
	
	public Fruit(int width, int height, Image image, Point position) {
		super(width, height, image, position);
		
		fruitRectangle = new Rectangle(getPosition().x,getPosition().y,width,height);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void setEaten() {
		// TODO Auto-generated method stub		
		//// TODO
	}
	@Override
	public int getGain() {
		// TODO Auto-generated method stub
		return FRUIT_GAIN;
	}
	
	public double getRectangleX(){
   		return fruitRectangle.getX();
    }
   
    public double getRectangleY(){
   		return fruitRectangle.getY();
    }
   
    public Rectangle getRectangle(){
   		return fruitRectangle;
    }

}
