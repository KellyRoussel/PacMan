package Models.Characters;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import Controllers.GameController;
import Models.Sprite;

public abstract class Character extends Sprite {


	
	public Character(int width, int height, Image image, Point initialPosition) {
		super(width, height, image, initialPosition);
	}

	

	public abstract void move();	

	public void returnInitialPosition() {
		getPosition().x = initialPosition.x;
		getPosition().y = initialPosition.y;
	}
}
