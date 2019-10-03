package Threads;

import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;
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
	private final List<Food> food;

	public PhysicsThread(PacMan PACMAN, ArrayList<Ghost> GHOST, ArrayList<Food> FOOD) {
		
		pacMan = PACMAN;
		ghost = GHOST;
		food = FOOD;
	}

	/*public synchronized boolean catchCollisionRectangle(Rectangle R1, Rectangle R2) {
		if(R1.intersects(R2)) {
			return true;
		}
		return false;
	}*/
	
	public synchronized boolean catchCollisionPacManPacGum(PacMan var_pacMan, List<Food> var_food) {
		
		//int cpt = 0;
		Iterator iter = var_food.iterator();
	    while (iter.hasNext()){
	    	Food f = (Food) iter.next();
	    	if(pacMan.getRectangle().intersects(f.getRectangle())) 
			{
				System.out.println("pacman is eating food");
				//if(catchAdvancedCollisionPacManPacGum()) {
					//return true;
				//}
			}
	    }
	    
	
		
		return false;
	}
	
	public synchronized boolean catchAdvancedCollisionPacManPacGum() {
		//var_pacMan.getRectangle().getWidth() == 0 && var_pacMan.getRectangle().getCenterY() == 0
		return true;
	}

	public synchronized boolean catchCollisionPacManGhost(PacMan var_pacMan, List<Ghost> ghost) {

		Rectangle pacmanRectangle = var_pacMan.getRectangle();

		Iterator iter = ghost.iterator();
	    while (iter.hasNext()){
	    	Ghost x = (Ghost) iter.next();
			if(pacmanRectangle.intersects(x.getRectangle())) 
			{
				//System.out.println("Basic Collision Detected");
				if(advancedCatchCollisionTactics(pacMan.getEllipse(),x.getAdvancedLowerRectangle(),x.getAdvancedTopArc()))
				{
					return true;
				}
				//System.out.println("Basic Not Valid Collision Detected");
			}
		}
		return false;
	}
	
	private synchronized boolean advancedCatchCollisionTactics(Ellipse2D.Float var_pacman, Rectangle var_ghostlower, Arc2D.Float var_ghostUpper)
	{
		if(var_pacman.intersects(var_ghostlower))
		{
			//System.out.println("Advanced Rectangle Collision");
			return true;
		}
		else if(((var_pacman.getWidth()/2)+(var_ghostUpper.getWidth()/2)) >= ((var_pacman.x+(var_pacman.getWidth()/2) - var_ghostUpper.getCenterX())) && ((var_pacman.getHeight()/2)+(var_ghostUpper.getHeight()/2)) >= ((var_pacman.y+(var_pacman.getHeight()/2) - var_ghostUpper.getCenterY())))
		{
			//System.out.println("Advanced Arc Collision");
			return true;
		}
		return false;
	}
	

	public void stopThread() {
		isRunning = false;
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
				if(catchCollisionPacManPacGum(pacMan,food)) {
					System.out.println("collision pacgum detected");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("STOP - " + this.getName());
	}
}