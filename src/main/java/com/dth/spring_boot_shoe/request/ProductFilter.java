package com.dth.spring_boot_shoe.request;

import lombok.Data;

@Data
public class ProductFilter {

    private Long brandId;
    private Long colorId;
    private Long sizeId;
    private String sort;
    private String price;
}
