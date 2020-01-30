package com.bay.common.exceptions;

public class BayException extends RuntimeException {
    public BayException(String id) {
        super(id);
    }

    public BayException(String message, Throwable e) {
        super(message, e);
    }
    
    public BayException(Throwable e) {
        super(e);
    }
}
