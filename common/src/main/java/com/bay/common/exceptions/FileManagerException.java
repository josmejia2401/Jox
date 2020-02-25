package com.bay.common.exceptions;

public class FileManagerException extends RuntimeException {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileManagerException(String message) {
        super(message);
    }

    public FileManagerException(String message, Throwable e) {
        super(message, e);
    }
    
    public FileManagerException(Throwable e) {
        super(e);
    }
}
