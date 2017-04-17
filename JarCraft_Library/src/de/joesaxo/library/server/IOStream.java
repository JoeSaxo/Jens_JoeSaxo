package de.joesaxo.library.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class IOStream {

	Socket socket;

	private InputStream inputstream;
	private OutputStream outputstream;
	private BufferedReader bufferedreader;
	private PrintWriter printwriter;
	
	List<String> buffer;

	IOStream(Socket socket) throws IOException {
		this.socket = socket;
		buffer = new ArrayList<String>();
		buffer.add("");
		createInput();
		createOutput();
	}

	private void createInput() throws IOException {
		inputstream = socket.getInputStream();
		bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
	}

	private void createOutput() throws IOException {
		outputstream = socket.getOutputStream();
		printwriter = new PrintWriter(outputstream);
	}

	void close() throws IOException {
		inputstream.close();
		outputstream.close();
		bufferedreader.close();
		printwriter.close();
		socket.close();

	}
	
	boolean ready(EMessageType messageType) throws IOException {
		update();
		for (int i = 0; i < buffer.size()-1; i++) {
			if (buffer.get(i).startsWith(messageType.getCharacterAsString())) return true;
		}
		return false;
	}

	String read(EMessageType messageType) {
		update();
		for (int i = 0; i < buffer.size()-1; i++) {
			if (buffer.get(i).startsWith(messageType.getCharacterAsString())) {
				return buffer.remove(i).substring(1);
			}
		}
		return null;
	}

	private void update() {
		try {
			while (bufferedreader.ready()) {
				char input = (char)bufferedreader.read();
				if (input != '\n') {
					int element = buffer.size() - 1;
					buffer.set(element, buffer.get(element) + input);
				} else {
					buffer.add("");
				}
			}
		} catch (IOException e) {}
	}

	boolean write(String text, EMessageType type) {
		if (printwriter.checkError()) return false;
		String[] strings = text.split("\n");
		for (String string : strings) {
			printwriter.write(type.getCharacter() + string + "\n");
		}
		printwriter.flush();
		return !printwriter.checkError();
	}

}
