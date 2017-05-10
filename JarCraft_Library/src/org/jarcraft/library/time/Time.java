package org.jarcraft.library.time;

public class Time {

	public static void WaitMillis(long timeMillis) {
		Wait(timeMillis);
	}

	public static void WaitSeconds(long timeSeconds) {
		WaitMillis(timeSeconds * 1000);
	}

	public static void WaitMinutes(long timeMinutes) {
		WaitSeconds(timeMinutes * 60);
	}

	public static void WaitHours(long timeHours) {
		WaitMinutes(timeHours * 60);
	}

	public static void WaitDays(long timeDays) {
		WaitHours(timeDays * 24);
	}

	private static void Wait(long timeMillis) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		long timeDelta;
		while ((timeDelta = stopWatch.getTimeDelta(timeMillis)) > 0) {
			InterruptThread(timeDelta);
		}
		stopWatch.stop();
	}

	private static void InterruptThread(long timeMillis) {
		try {
		    Thread.currentThread().sleep(timeMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
