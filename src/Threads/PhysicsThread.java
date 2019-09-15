package Threads;

import java.awt.Rectangle;
import java.util.ArrayList;

public class PhysicsThread extends Thread
{
	private volatile boolean isRunning;
	private final int SLEEP_TIMER = 100; //ms
	
	public synchronized boolean catchCollisionRectangle(Rectangle R1, Rectangle R2) {
		if(R1.intersects(R2)) {
			return true;
		}
		return false;
	}
	
	public synchronized boolean catchCollisionRectangleVSList(Rectangle R1, ArrayList<Rectangle> R2) {
		
		for(Rectangle x : R2) 
		{
			if(R1.intersects(x)) {
				return true;
			}
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
				Thread.sleep(5000);
				System.out.println("Running");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*try 
			{
				if(catchCollisionRectangle(Pacman,Pacman)) {
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
			}*/
		}
		System.out.println("STOP - " + this.getName());
	}
}
