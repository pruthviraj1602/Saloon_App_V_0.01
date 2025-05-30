package com.maven.exception;

public class TimeSlotUnavailableException extends RuntimeException {
    public TimeSlotUnavailableException(String message) {
        super(message);
    }
}