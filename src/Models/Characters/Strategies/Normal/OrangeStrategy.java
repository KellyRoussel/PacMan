package Models.Characters.Strategies.Normal;

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
		// TODO Auto-generated method stub
		if (ghost.getUpdatedAvailableDirections() != ghost.getAvailableDirections()) {
			ghost.setAvailableDirections(ghost.getUpdatedAvailableDirections());
			ghost.setRandomDirection();
		}
		ghost.move();
	}

}
