package Models.Foods;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import Models.Sprite;

public abstract class Food extends Sprite{


	public Food(int width, int height, Image image, Point position) {
		super(width, height, image, position);
	}
	
	
	public abstract boolean getEaten();
	public abstract void setEaten();
	public abstract int getGain();
	
	public abstract double getRectangleX();
    public abstract double getRectangleY();
    public abstract Rectangle getRectangle();
    public abstract double getEllipseX();
    public abstract double getEllipseY();
    public abstract Ellipse2D.Float getEllipse();
}
