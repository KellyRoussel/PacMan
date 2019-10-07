package Models.Characters.Strategies;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ImageIcon;

import Controllers.GameController;
import Models.Characters.Ghost;

public class StrBlue implements GhostStrategy{

	private Ghost ghost;
	private int style = 1;
	private int counter = 0;

	@Override
	public void meet() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setGhost(Ghost ghost) {
		// TODO Auto-generated method stub
		this.ghost = ghost;
	}


	public void updatePosition() {
		// TODO Auto-generated method stub
		if(!ghost.isEaten()){

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
		else
			loadImage();
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
			if(Math.abs(pmColumn - ghostColumn) <= 2)
				return -1;
			if(pmColumn > ghostColumn) {
				for(int i = ghostColumn; i <= pmColumn; i++) {
					if((GameController.getGrille()[pmRaw][i] <= 25 && GameController.getGrille()[pmRaw][i] >= 1) || ghost.getListTunnelRight().contains(new Point(pmRaw, i))) {
						return -1;
					}
				}
				return ghost.canMove(KeyEvent.VK_LEFT)? KeyEvent.VK_LEFT : -1;
			}
			else {
				
				for(int i = pmColumn; i <= ghostColumn; i++) {
					if((GameController.getGrille()[pmRaw][i] <= 25 && GameController.getGrille()[pmRaw][i] >= 1) || ghost.getListTunnelLeft().contains(new Point(pmRaw, i))) {
						return -1;
					}
				}
				return ghost.canMove(KeyEvent.VK_RIGHT)? KeyEvent.VK_RIGHT : -1;
			}
		}
		
		if(pmColumn == ghostColumn) {
			if(Math.abs(ghostRaw - pmRaw) <= 2)
				return -1;
			if(pmRaw > ghostRaw) {
				for(int i = ghostRaw + 1; i < pmRaw; i++) {
					if(GameController.getGrille()[i][pmColumn] <= 25 && GameController.getGrille()[i][pmColumn] >= 1) {
						return -1;
					}
				}
				return ghost.canMove(KeyEvent.VK_UP)? KeyEvent.VK_UP : -1;

			}
			else {
				for(int i = pmRaw + 1; i < ghostRaw; i++) {
					if(GameController.getGrille()[i][pmColumn] <= 25 && GameController.getGrille()[i][pmColumn] >= 1) {
						return -1;
					}
				}
				return ghost.canMove(KeyEvent.VK_DOWN)? KeyEvent.VK_DOWN : -1;
			}
		}
	
		return -1;
		
	}
	


	
	@Override
	public void loadImage() {
		// TODO Auto-generated method stub
		counter++;
		if(counter == 3) {
			counter = 0;
			if(GameController.getInvincibleCounter() <= 5) {
				style = (style + 1) % 2;
				style *= 2;
				style += 1;
			}
			else
				style = (style + 1) % 4;
		}
		String str = "";
		if(ghost.isEaten()) {
			str = "dead";
		}
		else
			str = "alive";
		
    	ImageIcon ii = new ImageIcon("ressources" + File.separator + "ghost" + str + style + ".png");
    	ghost.setImage(ii.getImage());
	}
	
}
