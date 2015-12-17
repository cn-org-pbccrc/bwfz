package org.openkoala.security.core;

public class PacketHeadFormatException extends SecurityException {
	
	private static final long serialVersionUID = -7197016322088801102L;

	public PacketHeadFormatException() {
		super();
	}

	public PacketHeadFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public PacketHeadFormatException(String message) {
		super(message);
	}

	public PacketHeadFormatException(Throwable cause) {
		super(cause);
	}
}
