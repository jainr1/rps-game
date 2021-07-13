package com.resmed.rpsgame.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    RPS_1("Player not found exception", HttpStatus.NOT_FOUND),
    RPS_2("Invalid throw exception", HttpStatus.BAD_REQUEST);

    public final String shortMessage;
    public final HttpStatus httpStatus;

    ErrorCode(String shortMessage, HttpStatus httpStatus) {
        this.shortMessage = shortMessage;
        this.httpStatus = httpStatus;
    }
}
