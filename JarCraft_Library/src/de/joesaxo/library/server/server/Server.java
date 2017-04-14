package de.joesaxo.library.server.server;

import de.joesaxo.library.server.interfaces.IConnection;
import de.joesaxo.library.server.interfaces.IReceiver;
import de.joesaxo.library.server.interfaces.ITimeOut;

public class Server implements IConnection {

	private ServerRunnable runnableServer;

	// ----------------------- Constructors -----------------------------------

	public Server(int port, int maxclients, IReceiver iReceiver) {
		runnableServer = new ServerRunnable(port, maxclients, iReceiver, this);
	}

	public Server(int port, int maxclients, IReceiver iReceiver, IConnection iConnection) {
		runnableServer = new ServerRunnable(port, maxclients, iReceiver, iConnection);
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
	
	public void setTimeOutInterface(ITimeOut iTimeOut) {
		runnableServer.setTimeOutInterface(iTimeOut);
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

	@Override // IConnection
	public void establishedConnection(String clientIP) {}

}
