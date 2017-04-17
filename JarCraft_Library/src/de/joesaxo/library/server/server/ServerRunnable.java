package de.joesaxo.library.server.server;

import de.joesaxo.library.server.DevClient;
import de.joesaxo.library.annotation.AnnotationManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

class ServerRunnable extends Thread {

	private int port;
	
	private long maxTimeOut;
	private long delayTime;
	
	private boolean disconnectOnTimeOut;

	private DevClient[] clients;
	private ServerSocket server;

	private AnnotationManager annotationManager;

	ServerRunnable(int port, int maxClients, AnnotationManager annotationManager) {
		this.annotationManager = annotationManager;
		disconnectOnTimeOut = true;
		maxTimeOut = DevClient.defaultMaxTimeOut;
		delayTime = DevClient.defaultDelayTime;
		this.port = port;
		clients = new DevClient[maxClients];
	}

	// ------------------------------- RUN
	// --------------------------------------------

	@Override
	public void run() {

		// -------------- Server ---
		while (server == null && !isInterrupted()) {
			try {
				server = new ServerSocket(port);
			} catch (IOException e) {}
		}

		// ------------------------
		while (!isInterrupted()) {
			Socket socketclient;
			try {
				server.setSoTimeout(50);
				socketclient = server.accept();
				if (isInterrupted()) break;
				
				
				{ //creating DevClient
					DevClient client = new DevClient(annotationManager);
					addClient(client);
					client.setDisconnectOnTimeOut(disconnectOnTimeOut);
					client.setMaxTimeOut(maxTimeOut);
					client.setDelayTime(delayTime);
					client.start(socketclient);
				}

			} catch (SocketTimeoutException ste) {} catch (SocketException se) {} catch (IOException e) {
				stopServer();
				e.printStackTrace();
			}
			
			updateClients();
			
		}
	}
	
	private void updateClients() {
		for (int i = 0; i < connectedClients(); i++) {
			clients[i].update();
			if (!clients[i].isConnected()) remove(i);
		}
	}

	void stopServer() {
		disconnectAll();
		closeServer();
		interrupt();
	}
	
	void setDisconnectOnTimeOut(boolean disconnectOnTimeOut) {
		this.disconnectOnTimeOut = disconnectOnTimeOut;
		for (int i = 0;  i < connectedClients(); i++) {
			clients[i].setDisconnectOnTimeOut(disconnectOnTimeOut);
		}
	}
	
	void setMaxTimeOut(long maxTimeOut) {
		this.maxTimeOut = maxTimeOut;
		for (int i = 0;  i < connectedClients(); i++) {
			clients[i].setMaxTimeOut(maxTimeOut);
		}
	}
	
	void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
		for (int i = 0;  i < connectedClients(); i++) {
			clients[i].setDelayTime(delayTime);
		}
	}

	// ------------------------------ help methods --------------------------

	private int getClientIDN(String clientid) {
		for (int i = 0; i < connectedClients(); i++) {
			if (clients[i].getClientID().equals(clientid)) {
				return i;
			}
		}
		return -1;
	}

	private void closeServer() {
		try {
			server.close();
		} catch (IOException e) {}
	}
	
	private boolean addClient(DevClient client) {
		if (clients[clients.length-1] != null) return false;
		for (int i = 0; i < clients.length; i++) {
			if (clients[i] == null) {
				clients[i] = client;
				break;
			}
		}
		return true;
	}
	
	private boolean remove(int index) {
		if (index < 0 || index >= clients.length) return false;
		
		if (clients[index] == null) return false;
		
		for (int i = index; i < clients.length - 1; i++) {
			clients[i] = clients[i+1];
			if (clients[i] == null) return true;
		}
		clients[clients.length-1] = null;
		return true;
	}

	// ------------------------------ external Access -------------------------

	boolean disconnect(String clientid) {
		if (!isConnected(clientid)) return false;
		clients[getClientIDN(clientid)].stopClient();
		return true;
	}

	void disconnectAll() {
		for (int i = 0; i < connectedClients(); i++) {
			clients[i].stopClient();
		}
	}

	int connectedClients() {
		for (int i = 0; i < clients.length; i++) {
			if (clients[i] == null) return i;
		}
		return clients.length;
	}

	boolean isConnected(String clientid) {
		for (DevClient client : clients) {
			if (client.getClientID() == clientid) return client.isConnected();
		}
		return false;
	}

	String getClientIP(int clientidn) {
		if (clients[clientidn] == null) return null;
		return clients[clientidn].getClientID();
	}

	String[] getClients() {
		String[] clientIPs = new String[connectedClients()];

		for (int i = 0; i < clientIPs.length; i++) {
			clientIPs[i] = clients[i].getClientID();
		}

		return clientIPs;
	}

	// --------------------- Send -----------------------------

	void SendAll(String message) {
		for (int i = 0; i < connectedClients(); i++) {
			clients[i].send(message);
		}
	}

	boolean Send(String clientid, String message) {
		return clients[getClientIDN(clientid)].send(message);
	}

}
