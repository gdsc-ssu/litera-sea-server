package com.server.literasea.exception;

import com.server.literasea.exception.common.ExceptionType;
import com.server.literasea.exception.common.Status;

public enum WordExceptionType implements ExceptionType {
    NOT_FOUND_ID(Status.NOT_FOUND, 1001, "요청하신 id의 단어를 찾을 수 없습니다.");

    private final Status status;
    private final int exceptionCode;
    private final String message;

    WordExceptionType(Status status, int exceptionCode, String message){
        this.status=status;
        this.exceptionCode=exceptionCode;
        this.message=message;
    }

    @Override
    public Status status(){ return status;}

    @Override
    public int exceptionCode(){return exceptionCode;}

    @Override
    public String message() {
        return message;
    }
}
