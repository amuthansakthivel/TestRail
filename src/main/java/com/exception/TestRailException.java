package com.exception;

public class TestRailException extends RuntimeException{

    public TestRailException(String message){
        super(message);
    }
    public TestRailException(String message, Throwable cause){
        super(message,cause);
    }
}
