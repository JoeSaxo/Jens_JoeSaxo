package org.jarcraft.library.iotools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SystemExecuter {

	public static Process execute(String command) throws IOException {
		return Runtime.getRuntime().exec(command);
	}

	public static Process execute(String command, String[] args) throws IOException {
		command = String.join(" "+'"', command, String.join('"'+" "+'"', args)) + '"';
		return Runtime.getRuntime().exec(command);
	}

	public static List<Process> execute(String[] commands) {
		return execute(Arrays.asList(commands));
	}

	public static List<Process> execute(List<String> commands) {
		List<Process> processes = new ArrayList<Process>();
		for (String command : commands) {
			try {
				processes.add(execute(command));
			} catch (IOException e) {
				processes.add(null);
			}
		}
		return processes;
	}

}
