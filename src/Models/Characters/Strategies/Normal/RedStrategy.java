package Models.Characters.Strategies.Normal;

import java.awt.Point;
import java.awt.event.KeyEvent;

import Controllers.GameController;
import Models.Characters.Ghost;
import Models.Characters.Strategies.GhostStrategy;

public class RedStrategy implements GhostStrategy{

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
		int direction = isPacManCorridor();
		if(direction != -1) {
			ghost.setDirection(direction);
		}
		else {
			if (ghost.getUpdatedAvailableDirections() != ghost.getAvailableDirections()) {
				ghost.setAvailableDirections(ghost.getUpdatedAvailableDirections());
				ghost.setRandomDirection();
			}
		}
		ghost.move();
	}
	public int isPacManCorridor() {
		Point pacManPosition = GameController.getPacManPosition();
		
		int pmRaw = pacManPosition.y / GameController.getDefaultSize();
		int pmColumn = pacManPosition.x / GameController.getDefaultSize();
		
		int ghostRaw = ghost.getPosition().y / GameController.getDefaultSize();
		int ghostColumn = ghost.getPosition().x / GameController.getDefaultSize();
		
		if(ghostRaw < 0 || ghostRaw >= GameController.getGrille().length)
			return -1;
		if(ghostColumn < 0 || ghostColumn >= GameController.getGrille()[0].length)
			return -1;
		
		if(pmRaw == ghostRaw) {
			if(pmColumn > ghostColumn) {
				for(int i = ghostColumn; i <= pmColumn; i++) {
					if((GameController.getGrille()[pmRaw][i] <= 25 && GameController.getGrille()[pmRaw][i] >= 1) || ghost.getListTunnelRight().contains(new Point(pmRaw, i))) {
						return -1;
					}
				}
				return KeyEvent.VK_RIGHT;
			}
			else {
				for(int i = pmColumn; i <= ghostColumn; i++) {
					if((GameController.getGrille()[pmRaw][i] <= 25 && GameController.getGrille()[pmRaw][i] >= 1) || ghost.getListTunnelLeft().contains(new Point(pmRaw, i))) {
						return -1;
					}
				}
				return KeyEvent.VK_LEFT;
			}
		}
		
		if(pmColumn == ghostColumn) {
			if(pmRaw > ghostRaw) {
				for(int i = ghostRaw + 1; i < pmRaw; i++) {
					if(GameController.getGrille()[i][pmColumn] <= 25 && GameController.getGrille()[i][pmColumn] >= 1) {
						return -1;
					}
				}
				return KeyEvent.VK_DOWN;
			}
			else {
				for(int i = pmRaw + 1; i < ghostRaw; i++) {
					if(GameController.getGrille()[i][pmColumn] <= 25 && GameController.getGrille()[i][pmColumn] >= 1) {
						return -1;
					}
				}
				return KeyEvent.VK_UP;
			}
		}
	
		return -1;
		
	}
}
