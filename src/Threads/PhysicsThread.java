package Threads;

import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

import Models.Characters.Ghost;
import Models.Characters.PacMan;
import Models.Foods.Food;

public class PhysicsThread extends Thread
{
	private volatile boolean isRunning;
	private final int SLEEP_TIMER = 10; //ms

	private final PacMan pacMan;
	private final List<Ghost> ghost;

	public PhysicsThread(PacMan PACMAN, List<Ghost> GHOST) 
	{
		pacMan = PACMAN;
		ghost = GHOST;
	}

	public synchronized boolean catchCollisionRectangle(Rectangle R1, Rectangle R2) {
		if(R1.intersects(R2)) {
			return true;
		}
		return false;
	}

	public synchronized boolean catchCollisionPacManGhost(PacMan var_pacMan, List<Ghost> var_ghost) {

		Rectangle pacmanRectangle = var_pacMan.getRectangle();
		Ellipse2D.Float pacmanEllipse = var_pacMan.getEllipse();

		for(Ghost x : var_ghost) 
		{
			Rectangle ghostRectangle = x.getRectangle();
			Rectangle ghostRectangleLow = x.getAdvancedLowerRectangle();
			Arc2D ghostArcTop = x.getAdvancedTopArc();
			
			if(pacmanRectangle.intersects(x.getRectangle())) 
			{
				return true;
				/*if(advancedCatchCollisionTactics(pacmanEllipse, ghostRectangleLow, ghostArcTop))
				{
					return true;
				}*/
			}
		}
		return false;
	}
	
	private synchronized boolean advancedCatchCollisionTactics(Ellipse2D.Float var_pacman, Rectangle var_ghostbottom,Arc2D ghostArcTop)
	{
		if(var_pacman.intersects(var_ghostbottom))
		{
			return true;
		}
		return false;
	}
	
	/*public synchronized boolean catchCollisionPacManPacGum(PacMan var_pacMan, List<Food> var_pacgum) {

		Rectangle pacmanRectangle = var_pacMan.getRectangle();

		for(PacGum x : var_pacgum) 
		{
			if(pacmanRectangle.intersects(x.getRectangle())) {
				return true;
			}
		}
		return false;
	}*/

	public void stopThread() {
		isRunning = false;
		/*synchronized(oBuffer)
		{
			//Reveiller l'ensemble des threads en attentes (wait)
			oBuffer.notifyAll();
		}*/
	}

	public void run()
	{
		isRunning = true;
		System.out.println("START - " + this.getName());

		while(isRunning)
		{
			try {
				Thread.sleep(SLEEP_TIMER);
				if(catchCollisionPacManGhost(pacMan,ghost)) {
					pacMan.setIsDead(true);
				}
				/*if(catchCollisionPacManPacGum(pacMan,pacgum)) {
					System.out.println("collision detected");
					pacMan.setIsDead(true);
				}*/
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("STOP - " + this.getName());
	}
}