package com.bay.common.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String id) {
        super("Task not found : " + id);
    }

    public NotFoundException(String message, Throwable e) {
        super(message, e);
    }
    
    public NotFoundException(Throwable e) {
        super(e);
    }
}
