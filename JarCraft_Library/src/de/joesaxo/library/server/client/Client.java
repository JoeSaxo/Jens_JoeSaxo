package de.joesaxo.library.server.client;

import de.joesaxo.library.server.notificator.AServer;
import de.joesaxo.library.server.notificator.EServerNotification;
import de.joesaxo.library.annotation.AnnotationManager;

import java.lang.Thread.State;

public class Client {

	private ClientRunnable runnableClient;
	private Thread thread;

	private AnnotationManager annotationManager;

	public Client(String IP, int port) {
		annotationManager = new AnnotationManager(this);
		runnableClient = new ClientRunnable(IP, port, annotationManager);
	}

	public Client(String IP, int port, Object annotatedClass) {
		annotationManager = new AnnotationManager(annotatedClass);
		runnableClient = new ClientRunnable(IP, port, annotationManager);
	}

	public Client(String IP, int port, Object[] annotatedClasses) {
		annotationManager = new AnnotationManager(annotatedClasses);
		runnableClient = new ClientRunnable(IP, port, annotationManager);
	}

	public void setAnnotationClass(Object cls) {
		annotationManager.setClass(cls);
	}

	public void setAnnotationClasses(Object[] classes) {
		annotationManager.setClasses(classes);
	}

	// ------------------------------Start / Stop -----------------------------

	public boolean start() {
		if (isRunning()) return false;
		thread = new Thread(runnableClient);
		thread.start();
		return true;
	}

	public boolean stop() {
		if (!isRunning()) return false;
		runnableClient.stopClient();
		return true;
	}

	public boolean isRunning() {
		return !(thread == null || thread.getState().equals(State.TERMINATED));
	}
	
	public void setDisconnectOnTimeOut(boolean disconnectOnTimeOut) {
		runnableClient.setDisconnectOnTimeOut(disconnectOnTimeOut);
	}

	public void setMaxTimeOut(long maxTimeOut) {
		runnableClient.setMaxTimeOut(maxTimeOut);
	}
	
	public void setDelayTime(long delayTime) {
		runnableClient.setDelayTime(delayTime);
	}

	// ---------------------- external Access --------------------------------

	public boolean send(String message) {
		if (!isRunning()) return false;
		return runnableClient.send(message);
	}

	public String getClientID() {
		return runnableClient.getClientID();
	}

	public boolean isConnected() {
		return runnableClient.isConnected();
	}

	// ---------------------------- Implementation ---------------------------

	// IConnection
	@AServer(EServerNotification.ESTABLISHEDCONNECTION)
	public void establishedConnection(String clientIP) {}

}
