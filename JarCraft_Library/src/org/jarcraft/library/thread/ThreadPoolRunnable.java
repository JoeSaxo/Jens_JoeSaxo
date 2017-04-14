package org.jarcraft.library.thread;

import java.util.ArrayList;
import java.util.List;

import java.lang.Thread;

public class ThreadPoolRunnable<R extends Runnable> {

	private int maxThreads = -1;
	private List<R> runnables = new ArrayList<R>();
	private List<Thread> threads = new ArrayList<Thread>();

	public ThreadPoolRunnable() {}

	public ThreadPoolRunnable(int maxClients) {
		this.maxThreads = maxClients;
	}

	public boolean execute(R runnable) {
		if (poolFull()) return false;
		runnables.add(runnable);
		Thread thread = new Thread(runnable);
		threads.add(thread);
		thread.start();
		return true;
	}

	public R getRunnable(int runnableID) {
		if (!poolContains(runnableID)) return null;
		return runnables.get(runnableID);
	}

	public void interruptAll() {
		while (runnables.size() != 0) {
			interruptRunnable(0);
		}
	}

	public boolean interruptRunnable(int runnableID) {
		if (!poolContains(runnableID)) return false;
		threads.get(runnableID).interrupt();
		runnables.remove(runnableID);
		threads.remove(runnableID);
		return true;
	}

	public List<R> getRunnables() {
		return runnables;
	}

	public int executedRunnables() {
		return runnables.size();
	}

	public int poolsize() {
		return maxThreads;
	}

	private void updatePoolSize() {
		for (int i = 0; i < runnables.size(); i++) {
			if (!threads.get(i).isAlive()) {
				threads.remove(i);
				runnables.remove(i);
				i--;
			}
		}
	}

	private boolean poolContains(int runnableID) {
		updatePoolSize();
		return !(runnableID >= runnables.size() || runnableID < 0);
	}

	private boolean poolFull() {
		updatePoolSize();
		return (runnables.size() == maxThreads && maxThreads != 0);
	}
}
