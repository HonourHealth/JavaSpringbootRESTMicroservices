package com.example.user.tests;

import com.example.common.Ticker;
import com.example.user.StockTradeRequest;
import com.example.user.TradeAction;
import com.example.user.UserInformationRequest;
import com.example.user.UserServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "grpc.server.port=-1",
        "grpc.server.in-process-name=integration-test",
        "grpc.client.user-service.address=in-process:integration-test"
})
class UserServiceTest {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @Test
    void userInformationTest() {
        var request = UserInformationRequest.newBuilder()
                .setUserId(1)
                .build();
        var response = this.userServiceBlockingStub.getUserInformation(request);
        Assertions.assertEquals(1, response.getUserId());
        Assertions.assertEquals("Sam", response.getName());
        Assertions.assertEquals(10_000, response.getBalance());
        Assertions.assertTrue(response.getHoldingsList().isEmpty());
    }

    @Test
    void unknownUserTest() {
        StatusRuntimeException statusRuntimeException = Assertions.assertThrows(StatusRuntimeException.class, () -> {
            var request = UserInformationRequest.newBuilder()
                    .setUserId(10)
                    .build();
            var response = this.userServiceBlockingStub.getUserInformation(request);
        });

        Assertions.assertEquals(Status.Code.NOT_FOUND, statusRuntimeException.getStatus().getCode());
    }

    @Test
    void unknownTickerBuyTest() {
        StatusRuntimeException statusRuntimeException = Assertions.assertThrows(StatusRuntimeException.class, () -> {
            var request = StockTradeRequest.newBuilder()
                    .setUserId(1)
                    .setPrice(1)
                    .setQuantity(1)
                    .setAction(TradeAction.BUY)
                    .build();
            this.userServiceBlockingStub.tradeStock(request);

        });

        Assertions.assertEquals(Status.Code.INVALID_ARGUMENT, statusRuntimeException.getStatus().getCode());
    }

    @Test
    void insufficientSharesTest() {
        StatusRuntimeException statusRuntimeException = Assertions.assertThrows(StatusRuntimeException.class, () -> {
            var request = StockTradeRequest.newBuilder()
                    .setUserId(1)
                    .setTicker(Ticker.AMAZON)
                    .setPrice(1)
                    .setQuantity(1000)
                    .setAction(TradeAction.SELL)
                    .build();
            this.userServiceBlockingStub.tradeStock(request);
        });

        Assertions.assertEquals(Status.Code.FAILED_PRECONDITION, statusRuntimeException.getStatus().getCode());
    }

    @Test
    void insufficientBalanceTest() {
        StatusRuntimeException statusRuntimeException = Assertions.assertThrows(StatusRuntimeException.class, () -> {
            var request = StockTradeRequest.newBuilder()
                    .setUserId(1)
                    .setTicker(Ticker.AMAZON)
                    .setPrice(1)
                    .setQuantity(10001)
                    .setAction(TradeAction.BUY)
                    .build();
            this.userServiceBlockingStub.tradeStock(request);
        });

        Assertions.assertEquals(Status.Code.FAILED_PRECONDITION, statusRuntimeException.getStatus().getCode());
    }

    @Test
    void buySellTest() {
        var buyRequest = StockTradeRequest.newBuilder()
                .setUserId(2)
                .setTicker(Ticker.AMAZON)
                .setPrice(100)
                .setQuantity(5)
                .setAction(TradeAction.BUY)
                .build();
        var buyResponse = this.userServiceBlockingStub.tradeStock(buyRequest);
        Assertions.assertEquals(9500, buyResponse.getBalance());
        Assertions.assertEquals(Ticker.AMAZON, buyResponse.getTicker());
        Assertions.assertEquals(5, buyResponse.getQuantity());

        var userRequest = UserInformationRequest.newBuilder()
                .setUserId(2)
                .build();
        var userResponse = this.userServiceBlockingStub.getUserInformation(userRequest);
        Assertions.assertEquals(1, userResponse.getHoldingsCount());
        Assertions.assertEquals(Ticker.AMAZON, userResponse.getHoldingsList().getFirst().getTicker());

        var sellRequest = buyRequest.toBuilder().setAction(TradeAction.SELL).setPrice(102).build();
        var sellResponse = this.userServiceBlockingStub.tradeStock(sellRequest);

        Assertions.assertEquals(10010, sellResponse.getBalance());
        Assertions.assertEquals(Ticker.AMAZON, sellResponse.getTicker());
        Assertions.assertEquals(5, sellResponse.getQuantity());
    }
}
