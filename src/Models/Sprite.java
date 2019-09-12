package Models;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

public class Sprite {
	protected final Point initialPosition;
	protected int height;
	protected int width;
	protected Image image;
	
	public Sprite(int width, int height, Image image, Point initialPosition) {
		super();
		this.width = width;
		this.height = height;
		this.image = image;
		this.initialPosition = initialPosition;
	}

	
	public void draw(Graphics g) {
        g.drawImage(image, initialPosition.x, initialPosition.y, width, height, null);	
	}
	
	
	
	public Point getInitialPosition() {
		return initialPosition;
	}
	

	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	
	
}
