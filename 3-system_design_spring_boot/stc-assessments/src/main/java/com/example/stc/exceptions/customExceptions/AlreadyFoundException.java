package com.example.stc.exceptions.customExceptions;

public class AlreadyFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    private String errorMessage;

    public AlreadyFoundException() {
        super();
    }

    public AlreadyFoundException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
