package motion.model;

import java.util.List;

import javax.swing.SwingWorker;

import util.Contract;

public class WorkerAnimator extends AbstractAnimator {
	
	private SwingWorker<Void, Runnable> swingWorker;
	private boolean stopped;
	private boolean started;
	private boolean paused;
	private int getSpeed;
	
	
	public WorkerAnimator(int max) {
		super(test(max));
		getSpeed =max/2;
		 swingWorker = new TickWorker();
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
		
		synchronized (swingWorker) {
			swingWorker.notify();
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
		
		swingWorker.execute();
		started=true;
		paused =false;
		fireStateChanged();
	}

	@Override
	public void stop() {

		Contract.checkCondition(isRunning());
		stopped = true;
		
		fireStateChanged();
	}
	
	private static int test(int m) {
		Contract.checkCondition(m >0);
		return m;
	}
	
	
	
	
	private class TickWorker extends SwingWorker<Void, Runnable> {
	    @Override
	    protected Void doInBackground() throws Exception {
	        while (!hasStopped()) {
	            if (isPaused()) {
	                synchronized (swingWorker) {
	                    swingWorker.wait();
	                }
	            } else {
	                Thread.sleep(sleepDuration());
	                publish(new Runnable() {
						@Override
						public void run() {
							fireTickOccured();
						}
					});
	            }
	        }
	        return null;
	    }

	    @Override
	    protected void process(List<Runnable> chunks) {
	        for (Runnable r : chunks) {
	            r.run();
	        }
	    }
	}

	
	
}
	
	
