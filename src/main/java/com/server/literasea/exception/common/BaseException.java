package com.server.literasea.exception.common;

public class BaseException extends RuntimeException{
    private final ExceptionType exceptionType;


    public BaseException(ExceptionType exceptionType) {
        super(exceptionType.message());
        this.exceptionType = exceptionType;
    }

    public ExceptionType getExceptionType(){return exceptionType;}
}
