package de.joesaxo.library.server.client;

import java.io.IOException;
import java.net.Socket;

import org.jarcraft.library.time.Time;

import de.joesaxo.library.server.DevClient;
import de.joesaxo.library.server.interfaces.IConnection;
import de.joesaxo.library.server.interfaces.IReceiver;

class ClientRunnable extends DevClient implements Runnable {

	private int port;
	private String IP;
	private boolean stopped;

	ClientRunnable(String IP, int port, IReceiver iReceiver, IConnection iConnection) {
		super(iReceiver, iConnection);
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
