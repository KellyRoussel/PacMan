package Models.Foods;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;

import javax.swing.ImageIcon;


public class PacGum extends Food{

	
	
	private final int PACGUM_GAIN = 50;

    public PacGum(int width, int height, Image image, Point position) {
		super(width, height, image, position);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setEaten() {
		
		//// TODO
	}

	@Override
	public int getGain() {
		// TODO Auto-generated method stub
		return PACGUM_GAIN;
	}
}
