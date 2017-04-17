package de.joesaxo.library.server.server;

import de.joesaxo.library.server.notificator.AServer;
import de.joesaxo.library.server.notificator.EServerNotification;
import de.joesaxo.library.annotation.AnnotationManager;

public class Server {

	private ServerRunnable runnableServer;

	private AnnotationManager annotationManager;

	// ----------------------- Constructors -----------------------------------

	public Server(int port, int maxclients) {
		annotationManager = new AnnotationManager(this);
		runnableServer = new ServerRunnable(port, maxclients, annotationManager);
	}

	public Server(int port, int maxclients, Object annotatedClass) {
		annotationManager = new AnnotationManager(annotatedClass);
		runnableServer = new ServerRunnable(port, maxclients, annotationManager);
	}

	public Server(int port, int maxclients, Object[] annotatedClasses) {
		annotationManager = new AnnotationManager(annotatedClasses);
		runnableServer = new ServerRunnable(port, maxclients, annotationManager);
	}

	public void setAnnotationClass(Object cls) {
		annotationManager.setClass(cls);
	}

	public void setAnnotationClasses(Object[] classes) {
		annotationManager.setClasses(classes);
	}

	// ------------------------------- start / stop
	// --------------------------------

	public boolean start() {
		if (runnableServer.isAlive()) return false;
		runnableServer.start();
		return true;
	}

	public boolean stop() {
		if (!runnableServer.isAlive()) return false;
		runnableServer.stopServer();
		return true;
	}

	public boolean isRunning() {
		return runnableServer.isAlive();
	}
	
	public void setDisconnectOnTimeOut(boolean disconnectOnTimeOut) {
		runnableServer.setDisconnectOnTimeOut(disconnectOnTimeOut);
	}
	
	public void setMaxTimeOut(long maxTimeOut) {
		runnableServer.setMaxTimeOut(maxTimeOut);
	}
	
	public void setDelayTime(long delayTime) {
		runnableServer.setDelayTime(delayTime);
	}

	// ----------------------- external Access ----------------------------

	public boolean disconnect(String clientid) {
		if (!runnableServer.isAlive()) return false;
		return runnableServer.disconnect(clientid);
	}

	public boolean disconnectAll() {
		if (!runnableServer.isAlive()) return false;
		runnableServer.disconnectAll();
		return true;
	}

	public boolean isConnected(String clientid) {
		if (!runnableServer.isAlive()) return false;
		return runnableServer.isConnected(clientid);
	}

	public int connectedClients() {
		return runnableServer.connectedClients();
	}

	public String getClientIP(int clientidn) {
		return runnableServer.getClientIP(clientidn);
	}

	public String[] getClients() {
		return runnableServer.getClients();
	}

	// ------------------------ Send ---------------------------------------

	public boolean SendAll(String message) {
		if (!runnableServer.isAlive()) return false;
		runnableServer.SendAll(message);
		return true;
	}

	public boolean Send(String clientid, String message) {
		if (!runnableServer.isAlive()) return false;
		return runnableServer.Send(clientid, message);
	}

	// ---------------------------- Implementation ---------------------------

	@AServer(EServerNotification.ESTABLISHEDCONNECTION) // IConnection
	public void establishedConnection(String clientIP) {}

}
