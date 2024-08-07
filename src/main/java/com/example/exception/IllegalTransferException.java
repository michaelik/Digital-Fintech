package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class IllegalTransferException extends RuntimeException {
    public IllegalTransferException(String message) {
        super(message);
    }
}
