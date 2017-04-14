package de.joesaxo.library.server.client;

import java.lang.Thread.State;

import de.joesaxo.library.server.interfaces.IConnection;
import de.joesaxo.library.server.interfaces.IReceiver;
import de.joesaxo.library.server.interfaces.ITimeOut;

public class Client implements IConnection {

	private ClientRunnable runnableClient;
	private Thread thread;

	public Client(String IP, int port, IReceiver iReceiver) {
		runnableClient = new ClientRunnable(IP, port, iReceiver, this);
	}

	public Client(String IP, int port, IReceiver iReceiver, IConnection iConnection) {
		runnableClient = new ClientRunnable(IP, port, iReceiver, iConnection);
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
	
	public void setTimeOutInterface(ITimeOut iTimeOut) {
		runnableClient.setTimeOutInterface(iTimeOut);
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

	@Override // IConnection
	public void establishedConnection(String clientIP) {}

}
