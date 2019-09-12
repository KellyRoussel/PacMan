package Models.Characters;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import Models.Characters.Strategies.GhostStrategy;

public class Ghost extends Character{
	

	public static boolean state = true;
	private String color;
	private GhostStrategy stategy;

	public Ghost(int width, int height, Image image, Point initialPosition) {
		super(width, height, image, initialPosition);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
}
