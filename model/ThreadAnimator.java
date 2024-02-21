package motion.model;



import util.Contract;

public class ThreadAnimator extends AbstractAnimator {
	
	private Thread thread;
	private boolean stopped;
	private boolean started;
	private boolean paused;
	private int getSpeed;
	
	
	public ThreadAnimator(int max) {
		super(test(max));
		getSpeed =max/2;
		thread = new Thread(new TickLoop());
	}

	@Override
	public int getSpeed() {
		return getSpeed;
	}

	@Override
	public boolean hasStarted() {

		return started;
	}

	@Override
	public boolean hasStopped() {

		return hasStarted() && stopped;
	}

	@Override
	public boolean isPaused() {

		return isRunning() && paused;
	}

	@Override
	public boolean isResumed() {

		return isRunning() && !isPaused();
	}

	@Override
	public boolean isRunning() {

		return  hasStarted() && !hasStopped();
	}

	@Override
	public void pause() {

		Contract.checkCondition(isRunning());
		paused=true;
		
		fireStateChanged();
	}

	@Override
	public void resume() {

		Contract.checkCondition(isRunning());
		
		synchronized (thread) {
			thread.notify();
		}
		paused=false;

		fireStateChanged();
	}

	@Override
	public void setSpeed(int d) {
		Contract.checkCondition(d >=0 && d <= getMaxSpeed());
		
		getSpeed=d;
		fireStateChanged();
	}

	@Override
	public void start() {
		thread.start();
		
		started=true;
		paused =false;
		fireStateChanged();
	}

	@Override
	public void stop() {
		Contract.checkCondition(isRunning());
		
		thread.interrupt();
		stopped = true;
		
		fireStateChanged();
	}
	
	private static int test(int m) {
		Contract.checkCondition(m >0);
		return m;
	}
	
	private class TickLoop implements Runnable{
		@Override
		public void run() {
			while (!hasStopped()) {
	            if (isPaused()) {
	                synchronized (thread) {
	                    try {
							thread.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	                    
	                }
	            } else {
	            	fireTickOccured();
	                try {
						Thread.sleep(sleepDuration());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						
					}
	            }
	        }
			
			
		}
	}
	
	


	
	
}
	
	
