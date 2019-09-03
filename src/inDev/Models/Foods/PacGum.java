package inDev.Models.Foods;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;

public class PacGum implements Food{

	
	
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

	public PacGum(int x, int y) {
	    loadImage();
	    this.x = x;
	    this.y = y;
	}
	
	public PacGum() {
	    x = 0;
	    y = 0;
	    isEaten = false;
	    image = null;
	}
	
	
    private void loadImage() {
        ImageIcon ii = new ImageIcon("ressources" + File.separator + "pacGum.png");
        image = ii.getImage(); 
    }
	
	@Override
	public boolean isEaten() {
		return isEaten;
	}

	@Override
	public void draw(Graphics g, int x, int y) {
        image = new ImageIcon("ressources" + File.separator + "pacGum.png").getImage(); 
		g.drawImage(image, x, y, null);
		
	}

	public void setEaten() {
		isEaten = true;
	}


}
