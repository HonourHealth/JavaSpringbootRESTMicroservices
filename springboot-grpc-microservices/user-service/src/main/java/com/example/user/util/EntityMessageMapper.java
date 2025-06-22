package com.example.user.util;

import com.example.user.Holding;
import com.example.user.StockTradeRequest;
import com.example.user.StockTradeResponse;
import com.example.user.UserInformation;
import com.example.user.entity.PortfolioItem;
import com.example.user.entity.User;

import java.util.List;

public class EntityMessageMapper {
    public static UserInformation toUserInformation(User user, List<PortfolioItem> portfolioItems) {
        List<Holding> holdings = portfolioItems
                .stream()
                .map(i -> Holding.newBuilder().setTicker(i.getTicker()).setQuantity(i.getQuantity()).build())
                .toList();

        return UserInformation.newBuilder()
                .setUserId(user.getId())
                .setName(user.getName())
                .setBalance(user.getBalance())
                .addAllHoldings(holdings)
                .build();
    }

    public static PortfolioItem toPortfolioItem(StockTradeRequest stockTradeRequest) {
        PortfolioItem portfolioItem = new PortfolioItem();
        portfolioItem.setUserId(stockTradeRequest.getUserId());
        portfolioItem.setTicker(stockTradeRequest.getTicker());
        portfolioItem.setQuantity(stockTradeRequest.getQuantity());
        return portfolioItem;
    }

    public static StockTradeResponse toStockTradeResponse(StockTradeRequest stockTradeRequest, int balance) {
        return StockTradeResponse.newBuilder()
                .setUserId(stockTradeRequest.getUserId())
                .setTicker(stockTradeRequest.getTicker())
                .setQuantity(stockTradeRequest.getQuantity())
                .setPrice(stockTradeRequest.getPrice())
                .setAction(stockTradeRequest.getAction())
                .setTotalPrice(stockTradeRequest.getPrice() * stockTradeRequest.getQuantity())
                .setBalance(balance)
                .build();
    }
}
