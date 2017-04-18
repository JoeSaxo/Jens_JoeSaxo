package de.joesaxo.test.server;

import de.joesaxo.library.server.notificator.AServer;
import de.joesaxo.library.server.notificator.EServerNotification;
import de.joesaxo.library.server.server.Server;

public class MyServer {

	private Server server;

	public static void main(String[] args) {
		MyServer test = new MyServer();
		test.start();
	}

	public void start() {
		server = new Server(56711, 20);
		server.setAnnotationClass(this);
		server.start();
	}

	@AServer(EServerNotification.TIMEDOUT)
	public void timedOut(String clientIP, Long timeout) {
		System.out.println("[Server]: Client " + clientIP + " lost connection after " + timeout + " milliseconds");
	}

	@AServer(EServerNotification.NEWMESSAGE)
	public void IncommingMessage(String clientIP, String message) {
		System.out.println("[" + clientIP + "]: " + message);
		if (message.equals("stop")) {
			System.out.println("[Server]: stopped");
			server.stop();
		} else if (message.equals("dis")) {
			server.disconnectAll();
		} else if (message.equals("dism")) {
			server.disconnect(clientIP);
		} else {
			server.Send(clientIP, message);
		}
	}

	@AServer(EServerNotification.ESTABLISHEDCONNECTION)
	public void establishedConnection(String clientIP) {
		System.out.println("[Server]: Client " + clientIP + " established connection");

	}

}
