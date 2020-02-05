package com.bay.common.exceptions;

public class CustomException extends RuntimeException {
	
    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable e) {
        super(message, e);
    }
    
    public CustomException(Throwable e) {
        super(e);
    }
}
