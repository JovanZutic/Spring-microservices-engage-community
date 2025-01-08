package org.example.eventservice.exceptions;

public class CommentsNotFoundException extends RuntimeException {
    public CommentsNotFoundException(String message) {
        super(message);
    }
}
