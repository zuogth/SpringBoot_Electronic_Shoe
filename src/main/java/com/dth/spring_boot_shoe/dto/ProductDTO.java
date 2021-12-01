package com.dth.spring_boot_shoe.dto;

import com.dth.spring_boot_shoe.entity.BrandEntity;
import com.dth.spring_boot_shoe.entity.ProductEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductDTO{

    private Long id;
    private String name;
    private BigDecimal price;
    private BrandEntity brand;

    public static ProductDTO converter(ProductEntity entity){
        return ProductDTO.builder().id(entity.getId())
                .name(entity.getName())
                .brand(entity.getBrand())
                .price(entity.getPrice()).build();
    }
}
