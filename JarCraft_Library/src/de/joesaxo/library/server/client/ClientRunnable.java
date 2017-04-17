package de.joesaxo.library.server.client;

import de.joesaxo.library.server.DevClient;
import de.joesaxo.library.annotation.AnnotationManager;
import org.jarcraft.library.time.Time;

import java.io.IOException;
import java.net.Socket;

class ClientRunnable extends DevClient implements Runnable {

	private int port;
	private String IP;
	private boolean stopped;

	ClientRunnable(String IP, int port, AnnotationManager annotationManager) {
		super(annotationManager);
		this.IP = IP;
		this.port = port;
		stopped = false;
	}
	
	private Socket createSocket() {
		Socket client = null;
		while (client == null && !stopped) {
			try {
				client = new Socket(IP, port);
			} catch (IOException e) {}
		}
		return client;
	}

	@Override
	public void run() {
		start(createSocket());
		while (isConnected()) {
			update();
			Time.WaitMillis(50);
		}
	}
	
	@Override
	public void stopClient() {
		stopped = true;
		super.stopClient();
	}

}
