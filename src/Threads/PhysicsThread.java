package Threads;

import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Controllers.GameController;
import Models.Characters.Ghost;
import Models.Characters.PacMan;
import Models.Foods.Food;

public class PhysicsThread extends Thread
{
	private volatile boolean isRunning;
	private final int SLEEP_TIMER = 50; //ms

	private final PacMan pacMan;
	private final List<Ghost> ghost;
	private final List<Food> food;

	public PhysicsThread(PacMan PACMAN, ArrayList<Ghost> GHOST, List<Food> list) {
		
		pacMan = PACMAN;
		ghost = GHOST;
		food = list;
	}

	/*public synchronized boolean catchCollisionRectangle(Rectangle R1, Rectangle R2) {
		if(R1.intersects(R2)) {
			return true;
		}
		return false;
	}*/
	
	public synchronized void catchCollisionPacManPacGum(PacMan var_pacMan, List<Food> var_food) {
		
		synchronized (var_food) {
			for (Iterator<Food> iterator = var_food.iterator(); iterator.hasNext();) {
				Food food_ite = iterator.next();
				if(var_pacMan.getRectangle().intersects(food_ite.getRectangle())) 
				{
					if(catchAdvancedCollisionPacManPacGum(var_pacMan.getEllipse(), food_ite.getEllipse() )) {
						food_ite.setEaten();
					}
				}
			}
		}
		
	}
	
	public synchronized boolean catchAdvancedCollisionPacManPacGum(Ellipse2D.Float var_pacman, Ellipse2D.Float var_food) 
	{
		if(((var_pacman.getWidth()/2)+(var_food.getWidth()/2)) >= ((var_pacman.x+(var_pacman.getWidth()/2) - var_food.x+(var_food.getWidth()/2))) || ((var_pacman.getHeight()/2)+(var_food.getHeight()/2)) >= ((var_pacman.y+(var_pacman.getHeight()/2) - var_food.y+(var_food.getHeight()/2))))
		{
			return true;
		}
		return false;
	}

	public synchronized boolean catchCollisionPacManGhost(PacMan var_pacMan, List<Ghost> ghost) {

		Rectangle pacmanRectangle = var_pacMan.getRectangle();

		Iterator iter = ghost.iterator();
	    while (iter.hasNext()){
	    	Ghost x = (Ghost) iter.next();
			if(pacmanRectangle.intersects(x.getRectangle())) 
			{
				//System.out.println("Basic Collision Detected");
				if(!x.isEaten() && advancedCatchCollisionTactics(pacMan.getEllipse(),x.getAdvancedLowerRectangle(),x.getAdvancedTopArc()))
				{
					x.setEaten(true);
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
					if(GameController.isInvincible()) {
						
						GameController.incEatenGhosts();
						GameController.setScore(GameController.getScore() + 200 * GameController.getEatenGhosts());
					}
					else
						pacMan.setIsDead(true);
				}
				catchCollisionPacManPacGum(pacMan,food);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("STOP - " + this.getName());
	}
}