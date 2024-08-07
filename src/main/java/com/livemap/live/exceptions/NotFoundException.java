package com.livemap.live.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends LiveMapException {

    public NotFoundException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus(){ return HttpStatus.NOT_FOUND; }
}
