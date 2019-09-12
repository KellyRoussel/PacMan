package Models.Foods;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import Models.Sprite;

public abstract class Food extends Sprite{


	public Food(int width, int height, Image image, Point position) {
		super(width, height, image, position);
	}
	

	public abstract void setEaten();
	public abstract int getGain();
}
