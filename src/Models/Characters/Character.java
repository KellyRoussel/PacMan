package Models.Characters;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import Controllers.GameController;
import Models.Sprite;

public abstract class Character extends Sprite {
	
	protected Point position;

	
	public Character(int width, int height, Image image, Point initialPosition) {
		super(width, height, image, initialPosition);
		this.position = initialPosition;
	}

	

	public abstract void move();	

	public void returnInitialPosition() {
		this.position = initialPosition;
	}
}
