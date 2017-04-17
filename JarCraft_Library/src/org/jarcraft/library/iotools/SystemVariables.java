package org.jarcraft.library.iotools;

import java.io.File;

public class SystemVariables {

	public static final String path = System.getenv("Path");
	public static final String appdata = System.getenv("AppData");
	public static final String pathseperator = File.pathSeparator;

	public static String getVariable(String variable) {
		return System.getenv(variable);
	}

}
