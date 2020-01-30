package com.bay.common.exceptions;

public class NotSaveException extends RuntimeException {
    public NotSaveException(String id) {
        super("Task save : " + id);
    }

    public NotSaveException(String message, Throwable e) {
        super(message, e);
    }
    
    public NotSaveException(Throwable e) {
        super(e);
    }
}
