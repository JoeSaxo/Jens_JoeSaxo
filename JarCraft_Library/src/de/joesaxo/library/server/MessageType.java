package de.joesaxo.library.server;

enum MessageType {
	
	MESSAGE('m'), PINGREQUEST('r'), PINGANSWER('a');
	
	private static MessageType[] messageTypes = { MESSAGE, PINGREQUEST, PINGANSWER };
	
	private char type;
	
	MessageType(char type) {
		this.type = type;
	}
	
	char getCharacter() {
		return type;
	}
	
	String getCharacterAsString() {
		return String.valueOf(getCharacter());
	}
	
	MessageType getMessageType(char character) {
		for (MessageType type : messageTypes) {
			if (type.getCharacter() == character) return type;
		}
		return null;
	}
	
	boolean addMessageType(MessageType messageType) {
		for (MessageType type : messageTypes) {
			if (type.getCharacter() == messageType.getCharacter()) return false;
		}
		MessageType[] types = messageTypes;
		messageTypes = new MessageType[types.length + 1];
		for (int i = 0; i < types.length; i++) {
			messageTypes[i] = types[i];
		}
		messageTypes[types.length] = messageType;
		return true;
	}
	
}
