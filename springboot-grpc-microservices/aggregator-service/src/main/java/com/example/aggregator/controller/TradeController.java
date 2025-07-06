package com.example.aggregator.controller;

import com.example.aggregator.service.TradeService;
import com.example.user.StockTradeRequest;
import com.example.user.StockTradeResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("trade")
@AllArgsConstructor
public class TradeController {

    private TradeService tradeService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public StockTradeResponse trade(StockTradeRequest stockTradeRequest) {
        return this.tradeService.trade(stockTradeRequest);
    }

}
