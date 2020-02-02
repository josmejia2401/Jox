package com.bay.common.exceptions;

public class NotSaveException extends RuntimeException {
	
    public NotSaveException(String id) {
        super("Couldn't save entity: " + id);
    }

    public NotSaveException(String id, Throwable e) {
        super("Couldn't save entity: " + id, e);
    }
    
    public NotSaveException(Throwable e) {
        super(e);
    }
}
