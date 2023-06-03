package com.tuum.assignment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by Rana on 27 May, 2023
 */
@Getter
public class ApiException extends Exception {
    private HttpStatus status;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
