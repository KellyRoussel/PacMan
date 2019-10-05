package Models.Characters.Strategies;

import Models.Characters.Ghost;

public interface GhostStrategy {
	public void setGhost(Ghost ghost);
	public void updatePosition();
	public void meet();
	public void loadImage();
}

