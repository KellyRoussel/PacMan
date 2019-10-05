package Models.Characters.Strategies;

import java.io.File;

import javax.swing.ImageIcon;

import Models.Characters.Ghost;

public class StrBlue implements GhostStrategy{

	private Ghost ghost;
	@Override
	public void meet() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setGhost(Ghost ghost) {
		// TODO Auto-generated method stub
		this.ghost = ghost;
	}


	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
		
	}


	
	@Override
	public void loadImage() {
		// TODO Auto-generated method stub
    	ImageIcon ii = new ImageIcon("ressources" + File.separator + "ghostdead.png");
    	ghost.setImage(ii.getImage());
	}
	
}
