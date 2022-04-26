package com.dth.spring_boot_shoe.response;

import com.dth.spring_boot_shoe.entity.ProductBillEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BillDetailResponse {

    private String name;
    private String image;
    private String color;
    private BigDecimal price;
    private Integer quantity;

    public static BillDetailResponse converter(ProductBillEntity entity,String image){
        return BillDetailResponse.builder().name(entity.getProductDetail().getProduct().getName())
                .image(image).color(entity.getProductDetail().getColor().getName())
                .price(entity.getPrice()).quantity(entity.getQuantity()).build();
    }

}
