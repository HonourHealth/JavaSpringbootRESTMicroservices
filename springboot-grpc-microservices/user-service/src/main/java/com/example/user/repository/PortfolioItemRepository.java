package com.example.user.repository;

import com.example.common.Ticker;
import com.example.user.entity.PortfolioItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioItemRepository extends CrudRepository<PortfolioItem, Integer> {
    List<PortfolioItem> findAllByUserId(Integer userId);

    Optional<PortfolioItem> findByUserIdAndTicker(Integer userId, Ticker ticker);
}
