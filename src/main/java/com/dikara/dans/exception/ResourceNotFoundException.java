package com.dikara.dans.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(
            String message
    ) {
        super(message);
    }
}
