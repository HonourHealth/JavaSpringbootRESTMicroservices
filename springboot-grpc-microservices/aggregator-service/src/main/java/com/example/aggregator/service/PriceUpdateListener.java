package com.example.aggregator.service;

import com.example.aggregator.dto.PriceUpdateDto;
import com.example.stock.PriceUpdate;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class PriceUpdateListener implements StreamObserver<PriceUpdate> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceUpdateListener.class);

    private final Set<SseEmitter> emitters = Collections.synchronizedSet(new HashSet<>());

    private final long sseTimeout;

    public PriceUpdateListener(@Value("${sse.timeout:300000}") long sseTimeout) {
        this.sseTimeout = sseTimeout;
    }

    public SseEmitter createEmitter() {
        SseEmitter emitter = new SseEmitter(this.sseTimeout);
        this.emitters.add(emitter);
        emitter.onError(ex -> this.emitters.remove(emitter));
        emitter.onTimeout(() -> this.emitters.remove(emitter));
        return emitter;
    }

    @Override
    public void onNext(PriceUpdate priceUpdate) {
        var dto = new PriceUpdateDto(
                priceUpdate.getTicker().toString(),
                priceUpdate.getPrice());
        this.emitters.removeIf(ex -> !this.send(ex, dto));
    }

    @Override
    public void onError(Throwable throwable) {
        LOGGER.error("stream error", throwable);
        this.emitters.forEach(e -> e.completeWithError(throwable));
        this.emitters.clear();
    }

    @Override
    public void onCompleted() {
        this.emitters.forEach(ResponseBodyEmitter::complete);
        this.emitters.clear();
    }

    private boolean send(SseEmitter emitter, Object o) {
        try {
            emitter.send(o);
            return true;
        } catch (IOException e) {
            LOGGER.warn("Failed to send SSE event {}", e.getMessage());
            return false;
        }
    }
}
