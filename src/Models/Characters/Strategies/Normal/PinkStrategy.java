package Models.Characters.Strategies.Normal;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Controllers.GameController;
import Models.Characters.Ghost;
import Models.Characters.Strategies.GhostStrategy;

public class PinkStrategy implements GhostStrategy{

	
	private Ghost ghost;
	private boolean onRoad;
	private Point lastPosition;

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
		int direction = isPacManCorridor();
		if(direction != -1) {
			lastPosition = new Point();
			lastPosition.x = GameController.getPacManPosition().x;
			lastPosition.y = GameController.getPacManPosition().y;
			onRoad = true;
			ghost.setDirection(direction);
			ghost.move();
			return;
		}
		else {
			if(onRoad){
				int ghostRaw = ghost.getPosition().x / GameController.getDefaultSize();
				int pmRaw = lastPosition.x / GameController.getDefaultSize();
				
				int ghostColumn = ghost.getPosition().y / GameController.getDefaultSize();
				int pmColumn = lastPosition.y / GameController.getDefaultSize();
				

				if(ghostRaw == pmRaw && ghostColumn == pmColumn) {
					onRoad = false;
					ghost.setAvailableDirections(ghost.getUpdatedAvailableDirections(new ArrayList<Integer>()));
					if(!ghost.setRandomDirection(new ArrayList<Integer>()))
						return;
				}
			}
			else {
				Point pacManPosition = GameController.getPacManPosition();
				
				int pmRaw = pacManPosition.y / GameController.getDefaultSize();
				int pmColumn = pacManPosition.x / GameController.getDefaultSize();
				
				int ghostRaw = ghost.getPosition().y / GameController.getDefaultSize();
				int ghostColumn = ghost.getPosition().x / GameController.getDefaultSize();
				
				int pmDirection = GameController.getPacManDirection();
				
				if(ghostRaw == pmRaw) {
					if((pmDirection == KeyEvent.VK_UP ||pmDirection == KeyEvent.VK_DOWN) && ghost.canMove(pmDirection)) {
						ghost.setDirection(pmDirection);
						ghost.move();
						return;
					}
				}
				
				if(ghostColumn == pmColumn) {
					if((pmDirection == KeyEvent.VK_RIGHT ||pmDirection == KeyEvent.VK_LEFT) && ghost.canMove(pmDirection)) {
						ghost.setDirection(pmDirection);
						ghost.move();
						return;
					}
				}
				if (!ghost.canMove(ghost.getDirection()) || ghost.getUpdatedAvailableDirections(new ArrayList<Integer>()) != ghost.getAvailableDirections()) {
					ghost.setAvailableDirections(ghost.getUpdatedAvailableDirections(new ArrayList<Integer>()));
					if(!ghost.setRandomDirection(new ArrayList<Integer>()))
						return;
				}
			}
			ghost.move();
		}
		
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

	@Override
	public void loadImage() {
		// TODO Auto-generated method stub
    	ImageIcon ii = new ImageIcon("ressources" + File.separator + "pink" + ghost.getDirectionString() + ".png");
    	ghost.setImage(ii.getImage());
	}

	@Override
	public void setOnRoad() {
		// TODO Auto-generated method stub
		onRoad = false;
	}
	

}
