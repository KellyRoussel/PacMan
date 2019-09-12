package Models.Foods;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import javax.swing.ImageIcon;


public class Gum extends Food{

	private final int GUM_GAIN = 10;

    public Gum(int width, int height, Image image, Point position) {
		super(width, height, image, position);
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
}
