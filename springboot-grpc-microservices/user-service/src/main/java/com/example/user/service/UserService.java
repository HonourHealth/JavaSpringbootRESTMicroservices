package com.example.user.service;

import com.example.user.*;
import com.example.user.service.handler.StockTradeRequestHandler;
import com.example.user.service.handler.UserInformationRequestHandler;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
@AllArgsConstructor
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserInformationRequestHandler userInformationRequestHandler;
    private final StockTradeRequestHandler stockTradeRequestHandler;

    @Override
    public void getUserInformation(UserInformationRequest request, StreamObserver<UserInformation> responseObserver) {
        log.info("user information for id {}", request.getUserId());
        var userInformation = this.userInformationRequestHandler.getUserInformation(request);
        responseObserver.onNext(userInformation);
        responseObserver.onCompleted();
    }

    @Override
    public void tradeStock(StockTradeRequest request, StreamObserver<StockTradeResponse> responseObserver) {
        log.info("{}", request);
        var response = TradeAction.SELL.equals(request.getAction())
                ? this.stockTradeRequestHandler.sellStock(request)
                : this.stockTradeRequestHandler.buyStock(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
