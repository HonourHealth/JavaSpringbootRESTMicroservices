package com.example.aggregator.tests;

import com.example.aggregator.tests.mockservice.StockMockService;
import com.example.aggregator.tests.mockservice.UserMockService;
import com.example.common.Ticker;
import com.example.user.StockTradeRequest;
import com.example.user.StockTradeResponse;
import com.example.user.TradeAction;
import com.example.user.UserInformation;
import net.devh.boot.grpc.server.service.GrpcService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext
@SpringBootTest(properties = {
        "grpc.server.port=-1",
        "grpc.server.in-process-name=integration-test",
        "grpc.client.user-service.address=in-process:integration-test",
        "grpc.client.stock-service.address=in-process:integration-test"
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserTradeTest {

    private static final String USER_INFORMATION_ENDPOINT = "http://localhost:%d/user/%d";
    private static final String TRADE_ENDPOINT = "http://localhost:%d/trade";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void userInformationTest() {
        var url = USER_INFORMATION_ENDPOINT.formatted(port, 1);
        var response = this.restTemplate.getForEntity(url, UserInformation.class);
        assertEquals(200, response.getStatusCode().value());
        var user = response.getBody();
        assertNotNull(user);
        assertEquals(1, user.getUserId());
        assertEquals("integration-test", user.getName());
        assertEquals(100, user.getBalance());
    }

    @Test
    void unknownUserTest() {
        var url = USER_INFORMATION_ENDPOINT.formatted(port, 2);
        var response = this.restTemplate.getForEntity(url, UserInformation.class);
        assertEquals(404, response.getStatusCode().value());
        var user = response.getBody();
        assertNull(user);
    }

    @Test
    void tradeTest() {
        var tradeRequest = StockTradeRequest.newBuilder()
                .setUserId(1)
                .setPrice(10)
                .setTicker(Ticker.AMAZON)
                .setAction(TradeAction.BUY)
                .setQuantity(2)
                .build();
        var url = TRADE_ENDPOINT.formatted(port);
        var response = this.restTemplate.postForEntity(url, tradeRequest, StockTradeResponse.class);
        assertEquals(200, response.getStatusCode().value());
        var tradeResponse = response.getBody();
        assertNotNull(tradeResponse);
        assertEquals(Ticker.AMAZON, tradeResponse.getTicker());
        assertEquals(1, tradeResponse.getUserId());
        assertEquals(15, tradeResponse.getPrice());
        assertEquals(1000, tradeResponse.getTotalPrice());
        assertEquals(0, tradeResponse.getBalance());
    }

    @TestConfiguration
    static class TestConfig {

        @GrpcService
        public StockMockService stockMockService() {
            return new StockMockService();
        }

        @GrpcService
        public UserMockService userMockService() {
            return new UserMockService();
        }

    }

}
