package com.server.literasea.exception;

import com.server.literasea.exception.common.BaseException;
import com.server.literasea.exception.common.ExceptionType;

public class WordException extends BaseException {
    public WordException(ExceptionType exceptionType){ super(exceptionType); }
}
