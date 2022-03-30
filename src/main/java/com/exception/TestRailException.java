package com.exception;

/**
 * @author amuthansakthivel
 * @version 1.0
 */
public class TestRailException extends RuntimeException{

    public TestRailException(String message){
        super(message);
    }
    public TestRailException(String message, Throwable cause){
        super(message,cause);
    }
}
