package inDev.Models.Foods;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

import inDev.Models.Maze;
import inDev.Views.GamePanel;

public class Gum implements Food{

	
	
	private Image image = null;
	private int x;
	private int y;
	private boolean isEaten = false;
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Gum(int x, int y) {
	    loadImage();
	    this.x = x;
	    this.y = y;
	}
	
	public Gum() {
	    x = 0;
	    y = 0;
	    isEaten = false;
	}
	
    private void loadImage() {
        ImageIcon ii = new ImageIcon("ressources" + File.separator + "gum.png");
        image = ii.getImage(); 
    }
	
	@Override
	public boolean isEaten() {
		return isEaten;
	}

	public int shiftedX(int x, int size) {
    	int nColumn = (x / Maze.getDefaultSize());
    	int nLeft = (x % Maze.getDefaultSize());
    	nLeft = nLeft * size / Maze.getDefaultSize();
    	nLeft += nColumn * size;
    	return nLeft;
    }
	
	@Override
	public void draw(Graphics g, int x, int y) {
        image = new ImageIcon("ressources" + File.separator + "gum.png").getImage(); 
		g.drawImage(image, GamePanel.debutX + shiftedX(x, Maze.getSize()), GamePanel.debutY + shiftedX(y, Maze.getSize()), null);
		
	}

	public void setEaten() {
		isEaten = true;
	}


}
