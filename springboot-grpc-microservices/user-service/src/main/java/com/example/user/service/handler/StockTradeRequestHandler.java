package com.example.user.service.handler;

import com.example.common.Ticker;
import com.example.user.StockTradeRequest;
import com.example.user.StockTradeResponse;
import com.example.user.entity.PortfolioItem;
import com.example.user.entity.User;
import com.example.user.exceptions.InsufficientBalanceException;
import com.example.user.exceptions.UnknownTickerException;
import com.example.user.exceptions.UnknownUserException;
import com.example.user.repository.PortfolioItemRepository;
import com.example.user.repository.UserRepository;
import com.example.user.util.EntityMessageMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StockTradeRequestHandler {
    private final UserRepository userRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    @Transactional
    public StockTradeResponse buyStock(StockTradeRequest stockTradeRequest) {
        this.validateTicker(stockTradeRequest.getTicker());
        User user = this.userRepository.findById(stockTradeRequest.getUserId())
                .orElseThrow(() -> new UnknownUserException(stockTradeRequest.getUserId()));

        var totalPrice = stockTradeRequest.getPrice() * stockTradeRequest.getQuantity();
        this.validateUserBalance(user.getId(), user.getBalance(), totalPrice);
        user.setBalance(user.getBalance() - totalPrice);
        this.portfolioItemRepository.findByUserIdAndTicker(user.getId(), stockTradeRequest.getTicker())
                .ifPresentOrElse(
                        portfolioItem -> portfolioItem.setQuantity(portfolioItem.getQuantity() + stockTradeRequest.getQuantity()),
                        () -> this.portfolioItemRepository.save(EntityMessageMapper.toPortfolioItem(stockTradeRequest))
                );
        return EntityMessageMapper.toStockTradeResponse(stockTradeRequest, user.getBalance());
    }

    @Transactional
    public StockTradeResponse sellStock(StockTradeRequest stockTradeRequest) {
        this.validateTicker(stockTradeRequest.getTicker());
        User user = this.userRepository.findById(stockTradeRequest.getUserId())
                .orElseThrow(() -> new UnknownUserException(stockTradeRequest.getUserId()));

        PortfolioItem portfolioItem = this.portfolioItemRepository.findByUserIdAndTicker(user.getId(), stockTradeRequest.getTicker())
                .filter(pi -> pi.getQuantity() >= stockTradeRequest.getQuantity())
                .orElseThrow(() -> new InsufficientBalanceException(user.getId()));


        var totalPrice = stockTradeRequest.getPrice() * stockTradeRequest.getQuantity();
        user.setBalance(user.getBalance() + totalPrice);
        portfolioItem.setQuantity(portfolioItem.getQuantity() - stockTradeRequest.getQuantity());
        return EntityMessageMapper.toStockTradeResponse(stockTradeRequest, user.getBalance());
    }

    private void validateTicker(Ticker ticker) {
        if (Ticker.UNKNOWN.equals(ticker)) {
            throw new UnknownTickerException();
        }
    }

    private void validateUserBalance(Integer userId, Integer userBalance, Integer totalPrice) {
        if (totalPrice > userBalance) {
            throw new InsufficientBalanceException(userId);
        }
    }
}
