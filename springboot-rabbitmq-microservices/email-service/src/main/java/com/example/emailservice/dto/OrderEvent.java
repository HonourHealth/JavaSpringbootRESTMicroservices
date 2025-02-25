package com.example.emailservice.dto;

import com.example.emailservice.dto.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private String status;
    private String message;
    private Order order;
}
