package com.server.literasea.exception.common;

public interface ExceptionType {
    Status status();
    int exceptionCode();
    String message();
}
