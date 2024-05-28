package com.vendavaultecommerceproject.exception.exeception;

public class DataNotAcceptableException extends Exception {

    public DataNotAcceptableException(){
        super();
    }
    public DataNotAcceptableException(String message){
        super(message);
    }
    public DataNotAcceptableException(String message, Throwable cause){
        super(message, cause);
    }
    public DataNotAcceptableException(Throwable cause){
        super(cause);
    }
    protected DataNotAcceptableException(String message, Throwable cause, boolean enableSuppression, boolean writeableStackTrace){
        super(message, cause,enableSuppression,writeableStackTrace);
    }
}
