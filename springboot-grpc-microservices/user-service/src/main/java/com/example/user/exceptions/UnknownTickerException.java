package com.example.user.exceptions;

public class UnknownTickerException extends RuntimeException {
    private static final String MESSAGE = "Ticker does not exist";
    public UnknownTickerException() {
        super(MESSAGE);
    }
}
