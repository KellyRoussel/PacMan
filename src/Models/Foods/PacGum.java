package Models.Foods;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Models.ToSprite;


public class PacGum extends Food{
	
	// liste contenant les images de l'animation du PacGum
	private static ArrayList<Image> pacGumImages = new ArrayList<Image>();
	
	// champ contenant un compteur pour determiner l image du pacGum a afficher
	private static int animationCounter ;
	
	private final int PACGUM_GAIN = 50;
	private Rectangle foodRectangle;
	private Ellipse2D.Float foodEllipse;
	private boolean isEaten;

	public PacGum(int width, int height, Image image, Point position) {
			super(width, height, image, position);
			initialisePacGumImages();

			foodRectangle = new Rectangle(getPosition().x,getPosition().y,width,height);
			foodEllipse = new Ellipse2D.Float(getPosition().x,getPosition().y,width,height);
			isEaten = false;
		}
	 
	@Override
	public void draw(Graphics g) {
    	// apres chaque 100 frames on change l image du pacGum
    	g.drawImage(pacGumImages.get(animationCounter/100), getPosition().x, getPosition().y, width, height, null);
    	// les valeurs du animationCounter seront 0..199
    	animationCounter = (animationCounter+1) % 200;
    }
	
	// remplir la liste des images des pacGums
	private void initialisePacGumImages() {
		ToSprite pacGum_to_tile = new ToSprite(16,"pacmanTiles");
		for (int i = 0; i < 2 ; i++) {
			pacGumImages.add(pacGum_to_tile.extractImage(13 + i, 0, "pacGum", i));
		}
	}
	
	public double getRectangleX(){
   		return foodRectangle.getX();
    }
   
    public double getRectangleY(){
   		return foodRectangle.getY();
    }
   
    public Rectangle getRectangle(){
   		return foodRectangle;
    }
    
    public double getEllipseX(){
   		return foodEllipse.getX();
    }
   
    public double getEllipseY(){
   		return foodEllipse.getY();
    }
   
    public Ellipse2D.Float getEllipse(){
   		return foodEllipse;
    }
    
	@Override
	public synchronized void setEaten() {
		isEaten = true;
	}
	@Override
	public synchronized boolean getEaten() {
		return isEaten;
	}
	@Override
	public int getGain() {
		// TODO Auto-generated method stub
		return PACGUM_GAIN;
	}
	
	
}
