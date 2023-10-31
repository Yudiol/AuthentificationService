package com.yudiol.jobsearchplatform.exception.errors;

public class BadRequestError extends RuntimeException {
    public BadRequestError(String message) {
        super(message);
    }
}
