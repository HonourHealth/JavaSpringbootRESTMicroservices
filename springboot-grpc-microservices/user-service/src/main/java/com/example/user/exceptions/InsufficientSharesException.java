package com.example.user.exceptions;

public class InsufficientSharesException extends RuntimeException {
    private static final String MESSAGE = "User [id=%d] does not have enough shares to perform the requested operation. Insufficient shares available for the requested operation.";

    public InsufficientSharesException(Integer userId) {
        super(MESSAGE.formatted(userId));
    }
}
