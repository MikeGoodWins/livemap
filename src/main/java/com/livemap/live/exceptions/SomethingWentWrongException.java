package com.livemap.live.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SomethingWentWrongException extends LiveMapException {

    public SomethingWentWrongException(final String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus(){ return HttpStatus.INTERNAL_SERVER_ERROR; }
}
