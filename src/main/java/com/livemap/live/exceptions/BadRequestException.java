package com.livemap.live.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends LiveMapException {

    public BadRequestException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus(){ return HttpStatus.BAD_REQUEST; }
}
