package com.yudiol.jobsearchplatform.exception.errors;

public class EmailExistError extends RuntimeException {
    public EmailExistError(String message) {
        super(message);
    }
}
