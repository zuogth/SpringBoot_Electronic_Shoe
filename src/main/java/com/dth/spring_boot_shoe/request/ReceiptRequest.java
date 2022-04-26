package com.dth.spring_boot_shoe.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ReceiptRequest {
    private Long id;
    private Long brandId;
    List<ReceiptPDRequest> productDetails;
    private BigDecimal totalMoney;
}
