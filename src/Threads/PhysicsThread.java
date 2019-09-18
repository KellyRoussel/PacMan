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
	private final int SLEEP_TIMER = 5; //ms

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

		//Rectangle = Basic Pacman Hitbox
		//Ellipse = Advanced Pacman Hitbox
		Rectangle pacmanRectangle = var_pacMan.getRectangle();
		Ellipse2D.Float pacmanEllipse = var_pacMan.getEllipse();

		for(Ghost x : var_ghost) 
		{
			//ghostRectangle = Basic Ghost Hitbox
			//ghostRectangleLow = Advanced Lower Ghost Hitbox
			//ghostArcTop = Advanced Upper Ghost Hitbox
			Rectangle ghostRectangle = x.getRectangle();
			Rectangle ghostRectangleLow = x.getAdvancedLowerRectangle();
			Arc2D ghostArcTop = x.getAdvancedTopArc();
			
			if(pacmanRectangle.intersects(x.getRectangle())) 
			{
				System.out.println("Basic Collision Detected");
				if(advancedCatchCollisionTactics(pacMan.getEllipse(),x.getAdvancedLowerRectangle(),x.getAdvancedTopArc()))
				{
					return true;
				}
				System.out.println("Basic Not Valid Collision Detected");
			}
		}
		return false;
	}
	
	private synchronized boolean advancedCatchCollisionTactics(Ellipse2D.Float var_pacman, Rectangle var_ghostlower, Arc2D.Float var_ghostUpper)
	{
		if(var_pacman.intersects(var_ghostlower))
		{
			System.out.println("Advanced Rectangle Collision");
			return true;
		}
		else if(((var_pacman.getWidth()/2)+(var_ghostUpper.getWidth()/2)) >= ((var_pacman.x+(var_pacman.getWidth()/2) - var_ghostUpper.getCenterX())) && ((var_pacman.getHeight()/2)+(var_ghostUpper.getHeight()/2)) >= ((var_pacman.y+(var_pacman.getHeight()/2) - var_ghostUpper.getCenterY())))
		{
			System.out.println("Advanced Arc Collision");
			return true;
		}
		return false;
	}
	

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