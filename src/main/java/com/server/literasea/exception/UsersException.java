package com.server.literasea.exception;

import com.server.literasea.exception.common.BaseException;
import com.server.literasea.exception.common.ExceptionType;

public class UsersException extends BaseException {
    public UsersException(ExceptionType exceptionType) { super(exceptionType); }
}
