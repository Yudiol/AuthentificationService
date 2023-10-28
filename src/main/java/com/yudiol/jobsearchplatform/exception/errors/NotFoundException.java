package com.yudiol.jobsearchplatform.exception.errors;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String objectName, String objectId) {
        super(objectName + " c id / name " + objectId + " не найден.");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
