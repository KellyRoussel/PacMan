package Models.Characters.Strategies.Normal;

import java.awt.Point;

import Controllers.GameController;
import Models.Characters.Ghost;
import Models.Characters.Strategies.GhostStrategy;

public class PinkStrategy implements GhostStrategy{

	
	private Ghost ghost;
	
	
	public void setGhost(Ghost ghost){
		this.ghost = ghost;
	}
	
	@Override
	public void meet() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
		if (ghost.getUpdatedAvailableDirections() != ghost.getAvailableDirections()) {
			ghost.setAvailableDirections(ghost.getUpdatedAvailableDirections());
			ghost.setRandomDirection();
		}
		ghost.move();
	}
	
	

}
