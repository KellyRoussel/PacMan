package Models.Foods;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import javax.swing.ImageIcon;
import Models.Maze;

public class Fruit extends Food{

	private final int FRUIT_GAIN = 100;
	public Fruit(int width, int height, Image image, Point position) {
		super(width, height, image, position);
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

}
