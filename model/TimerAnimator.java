package motion.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import util.Contract;

public class TimerAnimator extends AbstractAnimator {

	private Timer timer ;
	private boolean stopped;
	private boolean started;
	private boolean paused;
	private int getSpeed;
	
	
	public TimerAnimator(int max) {
		super(test(max));
		timer = new Timer(sleepDuration(), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireTickOccured();				
			}
		});
		timer.setInitialDelay(0);
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
		
		timer.stop();
		paused=true;
		
		fireStateChanged();
	}

	@Override
	public void resume() {
		Contract.checkCondition(isRunning());
		
		timer.start();
		paused=false;

		fireStateChanged();
	}

	@Override
	public void setSpeed(int d) {
		Contract.checkCondition(d >=0 && d <= getMaxSpeed());
		
		getSpeed=d;
		timer.setDelay(sleepDuration());
		fireStateChanged();
	}

	@Override
	public void start() {
		
		timer.start();
		started=true;
		paused =false;
		fireStateChanged();
	}

	@Override
	public void stop() {
		Contract.checkCondition(isRunning());
		
		timer.stop();
		stopped = true;
		
		fireStateChanged();
	}
	
	private static int test(int m) {
		Contract.checkCondition(m >0);
		
		return m;
	}
}
