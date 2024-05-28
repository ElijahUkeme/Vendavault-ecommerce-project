package com.vendavaultecommerceproject.exception.exeception;

public class DataNotFoundException extends Exception {
    public DataNotFoundException(){
        super();
    }
    public DataNotFoundException(String message){
        super(message);
    }
    public DataNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
    public DataNotFoundException(Throwable cause){
        super(cause);
    }
    protected DataNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writeableStackTrace){
        super(message, cause,enableSuppression,writeableStackTrace);
    }
}
