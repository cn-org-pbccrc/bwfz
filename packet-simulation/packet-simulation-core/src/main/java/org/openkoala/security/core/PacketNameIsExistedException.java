package org.openkoala.security.core;

public class PacketNameIsExistedException extends SecurityException {
	
	private static final long serialVersionUID = -7113801072088801962L;

	public PacketNameIsExistedException() {
		super();
	}

	public PacketNameIsExistedException(String message, Throwable cause) {
		super(message, cause);
	}

	public PacketNameIsExistedException(String message) {
		super(message);
	}

	public PacketNameIsExistedException(Throwable cause) {
		super(cause);
	}
}
