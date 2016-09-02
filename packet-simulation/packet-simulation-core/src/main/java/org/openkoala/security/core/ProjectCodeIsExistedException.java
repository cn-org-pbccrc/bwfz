package org.openkoala.security.core;

public class ProjectCodeIsExistedException extends SecurityException {
	
	private static final long serialVersionUID = 1873801072065401961L;

	public ProjectCodeIsExistedException() {
		super();
	}

	public ProjectCodeIsExistedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProjectCodeIsExistedException(String message) {
		super(message);
	}

	public ProjectCodeIsExistedException(Throwable cause) {
		super(cause);
	}
}
