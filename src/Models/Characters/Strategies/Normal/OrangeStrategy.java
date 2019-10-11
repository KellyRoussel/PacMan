package Models.Characters.Strategies.Normal;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Models.Characters.Ghost;
import Models.Characters.Strategies.GhostStrategy;

public class OrangeStrategy implements GhostStrategy{
	
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
		if(!ghost.isOutside() && ghost.canMove(KeyEvent.VK_UP)) {
			ghost.setDirection(KeyEvent.VK_UP);
			ghost.move();
			return;
		}
		if (ghost.getUpdatedAvailableDirections(new ArrayList<Integer>()) != ghost.getAvailableDirections()) {
			ghost.setAvailableDirections(ghost.getUpdatedAvailableDirections(new ArrayList<Integer>()));
			if(!ghost.setRandomDirection(new ArrayList<Integer>()))
				return;
		}
		ghost.move();
	}
	
	@Override
	public void loadImage() {
		// TODO Auto-generated method stub
    	ImageIcon ii = new ImageIcon("ressources" + File.separator + "orange" + ghost.getDirectionString() + ".png");
    	ghost.setImage(ii.getImage());
	}


	@Override
	public void setOnRoad() {
		// TODO Auto-generated method stub
		
	}
}
