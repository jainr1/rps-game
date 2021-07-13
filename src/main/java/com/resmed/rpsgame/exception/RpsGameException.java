package com.resmed.rpsgame.exception;

public class RpsGameException extends RuntimeException {

    private final ErrorCode errorCode;

    public RpsGameException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
