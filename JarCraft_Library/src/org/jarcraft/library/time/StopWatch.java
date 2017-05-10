package org.jarcraft.library.time;

public class StopWatch {
	
	private long start, time;
	private boolean running;
	
	public StopWatch() {
		running = false;
		start = 0;
		time = 0;
	}
	
	public boolean start() {
		if (running) return false;
		running = true;
		start = System.currentTimeMillis();
		return true;
	}
	
	public boolean stop() {
		if (!running) return false;
		time = System.currentTimeMillis() - start;
		running = false;
		return true;
	}
	
	public long getTime() {
		if (running) {
			time = System.currentTimeMillis() - start;
		}
		return time;
	}

	public long getTimeDelta(long secondTime) {
		return secondTime - getTime();
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void reset() {
		if (isRunning()) {
			stop();
		}
		time = 0;
	}
	
}
