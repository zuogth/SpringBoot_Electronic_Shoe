package com.dth.spring_boot_shoe.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartRequest {
    private Long productDetailId;
    private BigDecimal price;
    private Integer quantity;
}
