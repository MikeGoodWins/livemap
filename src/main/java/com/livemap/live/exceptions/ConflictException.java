package com.livemap.live.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends LiveMapException {

    public ConflictException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus(){ return HttpStatus.CONFLICT; }
}
