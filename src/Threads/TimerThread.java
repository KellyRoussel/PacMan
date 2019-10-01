package Threads;

public class TimerThread extends Thread {

	private int SLEEP_TIMER = 5;
	private int iTimeDelayMS;
	private long timerCount;
	private volatile boolean isRunning = false;

	public TimerThread(int iTimeInSec) {
		iTimeDelayMS = iTimeInSec * 1000;

	}

	public void stopThread() {
		isRunning = false;
		try {
			this.join(500);
			if (this.isAlive()) {
				this.interrupt();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		isRunning = true;
		int currentTime = 0;
		int previousTime = 0;

		System.out.println("THREAD" + this.getName() + " STARTED");
		while (verifStop()) {
			try {
				long counterStart = System.currentTimeMillis();
				Thread.sleep(SLEEP_TIMER);
				setTimerCount(getTimerCount() + System.currentTimeMillis() - counterStart);

				currentTime = (int) getTimerCount() / 1000;
				if (currentTime > previousTime) {

					previousTime = currentTime;
					System.out.println(currentTime + "secound passed");

				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("THREAD" + this.getName() + " STOPPED");
	}

	private boolean verifStop() {
		return isRunning & getTimerCount() <= iTimeDelayMS;
	}

	public long getTimerCount() {
		return timerCount;
	}

	public void setTimerCount(long timerCount) {
		this.timerCount = timerCount;
	}

}
