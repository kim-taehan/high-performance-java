package com.skcc.orderv1.core.exception;

import com.skcc.orderv1.core.response.ResEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(IllegalStateException.class)
    protected ResEntity<Void> IllegalStateException(IllegalStateException e) {
        return ResEntity.ok(null);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResEntity<Void> RuntimeException(RuntimeException e) {
        return ResEntity.ok(null);
    }
}

