package org.jarcraft.library.time;

public class Time {

	public static void WaitMillis(long timemillis) {
		Wait(timemillis);
	}

	public static void WaitSeconds(long timeseconds) {
		WaitMillis(timeseconds * 1000);
	}

	public static void WaitMinutes(long timeminutes) {
		WaitSeconds(timeminutes * 60);
	}

	public static void WaitHours(long timehours) {
		WaitMinutes(timehours * 60);
	}

	public static void WaitDays(long timedays) {
		WaitHours(timedays * 24);
	}

	private static void Wait(long timemillis) {
		long starttime = System.currentTimeMillis();
		long lasttime = System.currentTimeMillis();
		while (lasttime - starttime < timemillis) {
			lasttime = System.currentTimeMillis();
		}
	}

}
