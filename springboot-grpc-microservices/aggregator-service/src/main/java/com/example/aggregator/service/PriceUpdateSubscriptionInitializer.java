package com.example.aggregator.service;

import com.example.stock.StockServiceGrpc;
import com.google.protobuf.Empty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor
//@NoArgsConstructor
@Slf4j
public class PriceUpdateSubscriptionInitializer implements CommandLineRunner {

    @GrpcClient("stock-service")
    private StockServiceGrpc.StockServiceStub stockClient;

    private final PriceUpdateListener listener;

    public PriceUpdateSubscriptionInitializer(PriceUpdateListener listener) {
        this.listener = listener;
    }

    @Override
    public void run(String... args) {
        try {
            log.info("Attempting to connect to stock-service for price updates...");
            this.stockClient
                    .withWaitForReady()
                    .getPriceUpdates(
                            Empty.getDefaultInstance(),
                            listener
                    );
            log.info("Successfully connected to stock-service");
        } catch (Exception e) {
            log.warn("Failed to connect to stock-service: {}. The aggregator service will continue running without stock price updates.", e.getMessage());
        }
    }
}
