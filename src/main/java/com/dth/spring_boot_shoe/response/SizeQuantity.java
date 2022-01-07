package com.dth.spring_boot_shoe.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SizeQuantity {
    private Long productDetailId;
    private String size;
    private Integer quantity;

    public static SizeQuantity converter(Object[] objects){
        return SizeQuantity.builder().productDetailId(Long.valueOf(objects[0].toString()))
                .size(objects[1].toString())
                .quantity(Integer.valueOf(objects[2].toString())).build();
    }
}
