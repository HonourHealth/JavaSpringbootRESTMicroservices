package com.example.user.util;

import com.example.user.Holding;
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
}
