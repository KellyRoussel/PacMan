package Threads;

import java.awt.Rectangle;
import java.util.List;

public class PhysicsThread extends Thread
{
	private volatile boolean isRunning;
	private final int SLEEP_TIMER = 100; //ms
	
	private int x1;
	private int x2;
	private int y1;
	private int y2;

	private int width;
	private int height;
	
	private Rectangle Pacman;
	private static List<Rectangle> fantome;
	private static List<Rectangle> pacgum;
	
	public PhysicsThread()
	{
		x1 = 1;
		x2 = 10;
		y1 = 1;
		y2 = 10;
		width = 5;
		height = 5;
	}
	
	public synchronized boolean catchCollisionRectangle(Rectangle R1, Rectangle R2) {
		if(R1.intersects(R2)) {
			return true;
		}
		return false;
	}
	
	public synchronized boolean catchCollisionRectangleList(Rectangle R1, Rectangle R2) {
		if(R1.intersects(R2)) {
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
		R1 = new Rectangle(x1, y1, width, height);
		R2 = new Rectangle(x2, y2, width, height);

		System.out.println("START - " + this.getName());

		while(isRunning)
		{
			try 
			{
				if(catchCollisionRectangle(R1,R2)) {
					System.out.println(this.getName() + " Collision detected");
				}
				else
				{
					System.out.println(this.getName() + " No Collision detected");
				}
				Thread.sleep(SLEEP_TIMER);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("STOP - " + this.getName());
	}
}
