package Models.Foods;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
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

	public PacGum(int width, int height, Image image, Point position) {
			super(width, height, image, position);
			initialisePacGumImages();
			// TODO Auto-generated constructor stub
		}
	 
	@Override
	public void draw(Graphics g) {
    	// apres chaque 100 frames on change l image du pacGum
    	g.drawImage(pacGumImages.get(animationCounter/100), position.x, position.y, width, height, null);
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
