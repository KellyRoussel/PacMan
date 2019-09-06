package Models.Foods;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;


public class Gum implements Food{

	
	
	private Image image;
	private int x;
	private int y;
	private boolean isEaten;
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Gum(int x, int y) {
	    loadImage();
	    isEaten = false;
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

	
	@Override
	public void draw(Graphics g, int x, int y) {
        image = new ImageIcon("ressources" + File.separator + "gum.png").getImage(); 
		g.drawImage(image, x, y, null);
		
	}

	public void setEaten() {
		isEaten = true;
	}


}
