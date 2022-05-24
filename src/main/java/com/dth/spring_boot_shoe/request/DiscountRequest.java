package com.dth.spring_boot_shoe.request;

import lombok.Data;

import java.util.List;

@Data
public class DiscountRequest {
    private Long discountId;
    private List<Long> productDetailIds;
}
