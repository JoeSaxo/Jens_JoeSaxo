package org.jarcraft.library.thread;

import java.util.ArrayList;
import java.util.List;

import java.lang.Thread;

public class ThreadPool<T extends Thread> {

	private int maxThreads = -1;
	private List<T> threads = new ArrayList<T>();

	public ThreadPool() {}

	public ThreadPool(int maxClients) {
		this.maxThreads = maxClients;
	}

	public boolean execute(T thread) {
		if (poolFull()) return false;
		threads.add(thread);
		thread.start();
		return true;
	}

	public T getThread(int threadID) {
		if (!poolContains(threadID)) return null;
		return threads.get(threadID);
	}

	public void interruptAll() {
		while (threads.size() != 0) {
			interruptThread(0);
		}
	}

	public boolean interruptThread(int threadID) {
		if (!poolContains(threadID)) return false;
		threads.get(threadID).interrupt();
		threads.remove(threadID);
		return true;
	}

	public List<T> getThreads() {
		return threads;
	}

	public int executedThreads() {
		return threads.size();
	}

	public int poolsize() {
		return maxThreads;
	}

	private void updatePoolSize() {
		for (int i = 0; i < threads.size(); i++) {
			if (!threads.get(i).isAlive()) {
				threads.remove(i--);
			}
		}
	}

	private boolean poolContains(int threadID) {
		updatePoolSize();
		return !(threadID >= threads.size() || threadID < 0);
	}

	private boolean poolFull() {
		updatePoolSize();
		return (threads.size() == maxThreads && maxThreads != 0);
	}
}
