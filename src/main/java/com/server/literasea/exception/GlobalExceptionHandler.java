package com.server.literasea.exception;

import com.server.literasea.exception.common.BaseException;
import com.server.literasea.exception.common.ExceptionResponse;
import com.server.literasea.exception.common.ExceptionStatus;
import com.server.literasea.exception.common.ExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(BaseException e) {
        log.warn(e.getMessage(), e);
        ExceptionType type = e.getExceptionType();
        ExceptionStatus exceptionStatus = ExceptionStatus.from(type.status());
        return ResponseEntity.status(exceptionStatus.getHttpStatus())
                .body(new ExceptionResponse(type.exceptionCode(), type.message()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(0000, e.getMessage()));
    }
}
