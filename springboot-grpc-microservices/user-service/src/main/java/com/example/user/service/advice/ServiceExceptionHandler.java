package com.example.user.service.advice;

import com.example.user.exceptions.InsufficientBalanceException;
import com.example.user.exceptions.InsufficientSharesException;
import com.example.user.exceptions.UnknownTickerException;
import com.example.user.exceptions.UnknownUserException;
import io.grpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class ServiceExceptionHandler {
    @GrpcExceptionHandler(UnknownTickerException.class)
    public Status handleInvalidArguments(UnknownTickerException exception) {
        return Status.INVALID_ARGUMENT.withDescription(exception.getMessage());
    }

    @GrpcExceptionHandler(UnknownUserException.class)
    public Status handleUnknownEntities(UnknownUserException exception) {
        return Status.NOT_FOUND.withDescription(exception.getMessage());
    }

    @GrpcExceptionHandler({InsufficientBalanceException.class, InsufficientSharesException.class})
    public Status handleUnknownEntities(Exception exception) {
        return Status.FAILED_PRECONDITION.withDescription(exception.getMessage());
    }
}
