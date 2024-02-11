package com.server.literasea.exception.common;

public record ExceptionResponse(
        int exceptionCode,
        String message
) {
}
