package Models.Characters.Strategies.Normal;

import java.awt.Point;
import java.awt.event.KeyEvent;

import Controllers.GameController;
import Models.Characters.Ghost;
import Models.Characters.Strategies.GhostStrategy;

public class PinkStrategy implements GhostStrategy{

	
	private Ghost ghost;
	private boolean onRoad;
	
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
		int pmDirection = GameController.getPacManDirection();
		
		if(isPacManCorridor() && ghost.canMove(pmDirection)) {
			ghost.setDirection(pmDirection);
			onRoad = true;
		}
		else
			if (onRoad || ghost.getUpdatedAvailableDirections() != ghost.getAvailableDirections()) {
				ghost.setAvailableDirections(ghost.getUpdatedAvailableDirections());
				ghost.setRandomDirection();
				onRoad = false;
			}
		
		ghost.move();
	}
	
	public boolean isPacManCorridor() {
		Point pacManPosition = GameController.getPacManPosition();
		
		int pmRaw = pacManPosition.y / GameController.getDefaultSize();
		int pmColumn = pacManPosition.x / GameController.getDefaultSize();
		
		int ghostRaw = ghost.getPosition().y / GameController.getDefaultSize();
		int ghostColumn = ghost.getPosition().x / GameController.getDefaultSize();
	
		if(pmRaw == ghostRaw) {
			if(pmColumn > ghostColumn) {
				for(int i = ghostColumn + 1; i < pmColumn; i++) {
					if(GameController.getGrille()[pmRaw][i] <= 25 && GameController.getGrille()[pmRaw][i] >= 1) {
						return false;
					}
				}
				return true;
			}
			else {
				for(int i = pmColumn + 1; i < ghostColumn; i++) {
					if(GameController.getGrille()[pmRaw][i] <= 25 && GameController.getGrille()[pmRaw][i] >= 1) {
						return false;
					}
				}
				return true;
			}
		}
		
		if(pmColumn == ghostColumn) {
			if(pmRaw > ghostRaw) {
				for(int i = ghostRaw + 1; i < pmRaw; i++) {
					if(GameController.getGrille()[i][pmColumn] <= 25 && GameController.getGrille()[i][pmColumn] >= 1) {
						return false;
					}
				}
				return true;
			}
			else {
				for(int i = pmRaw + 1; i < ghostRaw; i++) {
					if(GameController.getGrille()[i][pmColumn] <= 25 && GameController.getGrille()[i][pmColumn] >= 1) {
						return false;
					}
				}
				return true;
			}
		}
	
		return false;
		
	}
	
	

}
