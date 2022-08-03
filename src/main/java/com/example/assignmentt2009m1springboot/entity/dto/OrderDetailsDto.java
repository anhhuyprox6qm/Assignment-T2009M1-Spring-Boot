package com.example.assignmentt2009m1springboot.entity.dto;

import com.example.assignmentt2009m1springboot.entity.Product;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {
    private Product product;
    private int quantity;
    private BigDecimal unitPrice;
}
