package com.example.user.entity;

import com.example.common.Ticker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PortfolioItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "customer_id")
    private Integer userId;
    private Ticker ticker;
    private Integer quantity;
}
