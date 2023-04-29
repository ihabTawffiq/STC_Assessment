package com.example.stc.exceptions.customExceptions;

public class InsufficientPrivilege extends Exception {
    private static final long serialVersionUID = 1L;

    private String errorMessage;

    public InsufficientPrivilege() {
        super();
    }

    public InsufficientPrivilege(String errorMessage) {
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
