package de.joesaxo.test.server;

import java.util.Scanner;

import de.joesaxo.library.server.client.Client;
import de.joesaxo.library.server.notificator.AServer;
import de.joesaxo.library.server.notificator.EServerNotification;

public class MyClient {

	private Client client;

	public static void main(String[] args) {
		MyClient test2 = new MyClient();
		test2.start();
	}

	public void start() {
		client = new Client("0.0.0.0", 56711);
		client.setAnnotationClass(this);
		client.start();
		while (!client.isConnected()) {
			System.out.print("");
		}
		Scanner scanner = new Scanner(System.in);
		String input;
		System.out.println("Started");
		do {
			input = scanner.next();
			if (test(input)) client.send(input);
		} while (!input.equals("stopclient"));
		System.out.println("stopped");
		client.stop();
		scanner.close();
		// while(true);
	}

	private boolean test(String input) {
		switch (input) {
		case "HW":
			client.send("Hallo Welt");
			break;
		default:
			return true;
		}
		return false;
	}

	@AServer(EServerNotification.TIMEDOUT)
	public void timedOut(String clientIP, Long timeout) {
		System.out.println("[Client]: lost connection");
	}

    @AServer(EServerNotification.NEWMESSAGE)
    public void IncommingMessage(String clientIP, String message) {
        System.out.println("[" + clientIP + "]: " + message);
    }

    @AServer(value = EServerNotification.NEWMESSAGE, message = "testMessage") // only called if the text of the message is "testMessage"
    public void IncommingTestMessage(String clientIP) {
        System.out.println("[" + clientIP + "]: " + "(preSet) testMessage");
    }

	@AServer(EServerNotification.ESTABLISHEDCONNECTION)
	public void establishedConnection(String clientIP) {
		System.out.println("[Client]: established connection");
	}


	@AServer(EServerNotification.STOPPED)
	public void stoppedServer(String clientIP) {
		System.out.println("[Client]: stopped");
	}

}
