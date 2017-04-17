package de.joesaxo.library.server;

enum EMessageType {
	
	MESSAGE('m'), PINGREQUEST('r'), PINGANSWER('a');
	
	private static EMessageType[] messageTypes = { MESSAGE, PINGREQUEST, PINGANSWER };
	
	private char type;
	
	EMessageType(char type) {
		this.type = type;
	}
	
	char getCharacter() {
		return type;
	}
	
	String getCharacterAsString() {
		return String.valueOf(getCharacter());
	}
	
	EMessageType getMessageType(char character) {
		for (EMessageType type : messageTypes) {
			if (type.getCharacter() == character) return type;
		}
		return null;
	}
	
	boolean addMessageType(EMessageType messageType) {
		for (EMessageType type : messageTypes) {
			if (type.getCharacter() == messageType.getCharacter()) return false;
		}
		EMessageType[] types = messageTypes;
		messageTypes = new EMessageType[types.length + 1];
		for (int i = 0; i < types.length; i++) {
			messageTypes[i] = types[i];
		}
		messageTypes[types.length] = messageType;
		return true;
	}
	
}
