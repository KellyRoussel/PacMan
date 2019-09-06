package Models.Characters;

import java.awt.Graphics;

public interface Character {
	public void move();
	public void treatcollision();
	public void draw(Graphics g, int x, int y, int mazeSize);
}
