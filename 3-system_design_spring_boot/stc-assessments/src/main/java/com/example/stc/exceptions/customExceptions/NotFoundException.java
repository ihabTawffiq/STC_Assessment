package com.example.stc.exceptions.customExceptions;

public class NotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    private String errorMessage;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String errorMessage) {
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
