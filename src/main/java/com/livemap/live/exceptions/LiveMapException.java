package com.livemap.live.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class LiveMapException extends RuntimeException {

    public LiveMapException(String message) {
        super(message);
    }

    public HttpStatus getStatus(){ return HttpStatus.INTERNAL_SERVER_ERROR; }
}
