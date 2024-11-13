package com.facens.booklist.infra.exception;

public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException() {
        super("Email already in use!\n");
    }

    public EmailAlreadyUsedException(Throwable cause) {
        super("Email already in use!\n", cause);
    }
}
