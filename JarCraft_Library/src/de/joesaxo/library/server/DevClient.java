package de.joesaxo.library.server;

import de.joesaxo.library.server.notificator.EServerNotification;
import de.joesaxo.library.annotation.AnnotationManager;

import java.io.IOException;
import java.net.Socket;

import static de.joesaxo.library.server.EMessageType.*;

public class DevClient {

	public static final long defaultMaxTimeOut = 500;
	public static final long defaultDelayTime = 1000;
	
	
	private boolean connected;
	
	private boolean disconnectOnTimeOut;
	
	private boolean pinging;
	private long maxTimeOut;
	private long delayTime;
	
	private long lastPing;

	private String clientId;

	private IOStream stream;

	private AnnotationManager annotationManager;

	// ------------------------------ constructors
	// ----------------------------------

	public DevClient(AnnotationManager annotationManager) {
		this.annotationManager = annotationManager;
		connected = false;
		disconnectOnTimeOut = true;
		setMaxTimeOut(defaultMaxTimeOut);
		setDelayTime(defaultDelayTime);
	}
	
	public void setDisconnectOnTimeOut(boolean disconnectOnTimeOut) {
		this.disconnectOnTimeOut = disconnectOnTimeOut;
	}

	public void setMaxTimeOut(long maxTimeOut) {
		this.maxTimeOut = maxTimeOut;
		if (delayTime <= maxTimeOut) delayTime = maxTimeOut * 2;
	}
	
	public void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
		if (delayTime <= maxTimeOut) maxTimeOut = delayTime / 2;
	}
	
	public boolean start(Socket client) {
		if (connected) return false;
		lastPing = 0;
		pinging = false;
		// establishing connection to Server
		clientId = client.getRemoteSocketAddress().toString();
		// creating streams for communication
		openStreams(client);
		// connection established
		connected = true;
		annotationManager.invokeMethods(EServerNotification.ESTABLISHEDCONNECTION.getModule(), clientId);
		return true;
	}
	
	public void update() {
		
		checkForNewMessages();
		
		checkForNewPingRequests();
		
		ping();
		
	}

	// checking for new message's
	private void checkForNewMessages() {
		String message = stream.read(MESSAGE);
		if (message != null) {
			annotationManager.invokeMethods(EServerNotification.NEWMESSAGE.getModule(), new Object[]{clientId, message});
			annotationManager.invokeMethods(EServerNotification.NEWMESSAGE.getModule().addParameter("type", message), clientId);
			//iReceiver.IncommingMessage(clientId, message);
		}
	}

	// checks for ping requests and answers them
	private void checkForNewPingRequests() {
		String message = stream.read(PINGREQUEST);
		if (message != null) {
			send(message, PINGANSWER);
		}
	}
	
	// starts ping requests and checks for Timeout
	private void ping() {
		if (!pinging && deltaTime(lastPing) > delayTime) {
			lastPing = deltaTime(0);
			send(lastPing, PINGREQUEST);
			pinging = true;
		}
		
		if (pinging) {
			String message = stream.read(PINGANSWER);
			if (message != null && message.equals(String.valueOf(lastPing))) {
				pinging = false;
			} else if (deltaTime(lastPing) > maxTimeOut) {
				annotationManager.invokeMethods(EServerNotification.TIMEDOUT.getModule(), new Object[]{clientId, deltaTime(lastPing)});
				if (disconnectOnTimeOut) stopClient();
				//if (iTimeOut != null) iTimeOut.timedOut(clientId, deltaTime(lastPing));
			}
		}
	}

	// ------------------------ Help methodes ------------------------
	
	private long deltaTime(long time) {
		return System.currentTimeMillis() - time;
	}

	private void openStreams(Socket client) {
		try {
			stream = new IOStream(client);
		} catch (IOException e) {}
	}

	private void closeStreams() {
		try {
			stream.close();
		} catch (IOException e) {}
	}

	private boolean send(String message, EMessageType messageType) {
		return stream.write(message, messageType);
	}

	private boolean send(long message, EMessageType messageType) {
		return send(String.valueOf(message), messageType);
	}

	// ----------------------- external Access -------------------------------

	public boolean send(String message) {
		return send(message, MESSAGE);
	}

	public String getClientID() {
		return clientId;
	}

	public boolean isConnected() {
		return connected;
	}

	public void stopClient() {
		connected = false;
		if (stream != null) {
			closeStreams();
		}
		annotationManager.invokeMethods(EServerNotification.STOPPED.getModule(), clientId);
	}
	
}
