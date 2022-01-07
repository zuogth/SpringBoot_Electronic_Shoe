package com.dth.spring_boot_shoe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BillReceiptDTO {

    private Integer quantity;
    private ProductDetailDTO detail;
    private BigDecimal price;
}
