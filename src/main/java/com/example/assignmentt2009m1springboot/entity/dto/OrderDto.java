package com.example.assignmentt2009m1springboot.entity.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private BigDecimal totalPrice;
    private Set<OrderDetailsDto> orderDetails;
}
